package com.chakwak.tarotscoreboard.activities;

import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.chakwak.tarotscoreboard.R;
import com.chakwak.tarotscoreboard.ScoreBoardContract.Game;
import com.chakwak.tarotscoreboard.ScoreBoardContract.Player;
import com.chakwak.tarotscoreboard.ScoreBoardContract.PlayerInGame;
import com.chakwak.tarotscoreboard.ScoreBoardDbHelper;
import com.chakwak.tarotscoreboard.dao.PlayerDao;
import com.chakwak.tarotscoreboard.enums.Appel;
import com.chakwak.tarotscoreboard.enums.Prise;

public class CreateGameActivity extends ActionBarActivity {
	
	private Cursor cursorPlayers = null;
	
	private Button selectPlayersButton;
	protected CharSequence[] players = null;
	private ArrayList<CharSequence> selectedPlayers = new ArrayList<CharSequence>();
	private Long eventId = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		
		if(eventId == null) {
			eventId = Long.valueOf(getIntent().getStringExtra(EventBoardActivity.EVENT_BOARD_ID));
		}
		
		cursorPlayers = PlayerDao.getPlayersInEvent(this, eventId);
		
		String[] from = new String[] {Player.COLUMN_NAME_PLAYER_NAME};
		int[] to = new int[] {android.R.id.text1};
		
		Spinner takerSelect = (Spinner)findViewById(R.id.spinner_game_create_taker);
		takerSelect.setAdapter(new SimpleCursorAdapter(
				this, android.R.layout.simple_spinner_item, cursorPlayers, from, to));
		
		Spinner priseSelect = (Spinner)findViewById(R.id.spinner_game_create_prise);
		priseSelect.setAdapter(new ArrayAdapter<Prise>(this, android.R.layout.simple_spinner_item, Prise.values()));
		
		Spinner appelSelect = (Spinner)findViewById(R.id.spinner_game_create_appel);
		appelSelect.setAdapter(new ArrayAdapter<Appel>(this, android.R.layout.simple_spinner_item, Appel.values()));
		
		Spinner associateSelect = (Spinner)findViewById(R.id.spinner_game_create_associate);
		associateSelect.setAdapter(new SimpleCursorAdapter(
				this, android.R.layout.simple_spinner_item, cursorPlayers, from, to));
		
		Spinner resultSelect = (Spinner)findViewById(R.id.spinner_game_create_result);
		resultSelect.setAdapter(ArrayAdapter.createFromResource(this, R.array.string_array_result, android.R.layout.simple_spinner_item));
		
		selectPlayersButton = (Button)findViewById(R.id.button_select_players);
		
		selectAllPlayers();
	}
	
	private void selectAllPlayers() {
		if(players == null) {
			players = new CharSequence[cursorPlayers.getCount()];
			for(int i=0; i<players.length; i++) {
				cursorPlayers.moveToPosition(i);
				players[i] = cursorPlayers.getString(1);
				selectedPlayers.add(players[i]);
			}
		}
		
		onChangeSelectedPlayers();
	}

	public void onPlayerSpinnerClick(View view) {
		
		boolean[] checkedPlayer = new boolean[players.length];
		int count = players.length;
		 
		for(int i = 0; i < count; i++)
		{
			checkedPlayer[i] = selectedPlayers.contains(players[i]);
		}
		 
		DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if(isChecked) {
					selectedPlayers.add(players[which]);
				}
			    else {
			    	selectedPlayers.remove(players[which]);
			    }
			 
			    onChangeSelectedPlayers();
			}
		};
		 
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_select_players);
		builder.setMultiChoiceItems(players, checkedPlayer, coloursDialogListener);
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void onChangeSelectedPlayers() {
		StringBuilder sBuilder = new StringBuilder();
		
		for(CharSequence player: selectedPlayers) {
			sBuilder.append(player);
			if(selectedPlayers.indexOf(player)<(selectedPlayers.size()-1)) {
				sBuilder.append(",");
			}
		}
		
		if(selectedPlayers.isEmpty()) {
			sBuilder.append(getString(R.string.no_selected_players));
		}
		
		selectPlayersButton.setText(sBuilder.toString());
	}
	
	public void onCreateGame(View view) {
		Integer playerCount = selectedPlayers.size();
		Appel appel = (Appel)((Spinner)findViewById(R.id.spinner_game_create_appel)).getSelectedItem();
		
		Integer takerIndex = ((Spinner)findViewById(R.id.spinner_game_create_taker)).getSelectedItemPosition();
		cursorPlayers.moveToPosition(takerIndex);
		Integer takerId = cursorPlayers.getInt(0);
		String takerName = cursorPlayers.getString(1);
		
		Integer associateIndex = ((Spinner)findViewById(R.id.spinner_game_create_associate)).getSelectedItemPosition();
		cursorPlayers.moveToPosition(associateIndex);
		Integer associateId = cursorPlayers.getInt(0);
		String associatedName = cursorPlayers.getString(1);
		
		Prise prise = (Prise)((Spinner)findViewById(R.id.spinner_game_create_prise)).getSelectedItem();
		
		Integer result = Integer.valueOf((String)((Spinner)findViewById(R.id.spinner_game_create_result)).getSelectedItem());
		Boolean validated = ((CheckBox)findViewById(R.id.check_game_create_validated)).isChecked();
		
		if(playerCount<3) {
			Toast.makeText(this, R.string.toast_not_enough_player, Toast.LENGTH_SHORT).show();
		} else if(playerCount>5) {
			Toast.makeText(this,R.string.toast_too_many_players, Toast.LENGTH_SHORT).show();
		} else if(playerCount == 5 && appel == Appel.AUCUN) {
			Toast.makeText(this,R.string.toast_need_appel, Toast.LENGTH_SHORT).show();
		} else if(!selectedPlayers.contains(takerName)) { // check if taker is playing
			Toast.makeText(this,R.string.toast_taker_player, Toast.LENGTH_SHORT).show();
		} else if(appel != Appel.AUCUN && !selectedPlayers.contains(associatedName)){ // check if associate is playing
			Toast.makeText(this,R.string.toast_associate_player, Toast.LENGTH_SHORT).show();
		} else {
			ContentValues values = new ContentValues();
			values.put(Game.COLUMN_NAME_DATE, new Date().getTime()/1000);
			values.put(Game.COLUMN_NAME_EVENT_ID, eventId);
			values.put(Game.COLUMN_NAME_TAKER_ID, takerId);
			values.put(Game.COLUMN_NAME_PRISE, prise.toString());
			values.put(Game.COLUMN_NAME_APPEL, appel.toString());
			values.put(Game.COLUMN_NAME_ASSOCIATE_ID, associateId);
			values.put(Game.COLUMN_NAME_VALID, validated ? 1:0);
			values.put(Game.COLUMN_NAME_RESULT, result);

			SQLiteDatabase db = ScoreBoardDbHelper.getWriteDb(this);
			Long gameId = db.insert(Game.TABLE_NAME, null, values);
			
			cursorPlayers.moveToFirst();
			while(!cursorPlayers.isAfterLast()) {
				if(selectedPlayers.contains(cursorPlayers.getString(1))) {
					values.clear();
					values.put(PlayerInGame.COLUMN_NAME_GAME_ID, gameId);
					values.put(PlayerInGame.COLUMN_NAME_PLAYER_ID, cursorPlayers.getInt(0));
					
					db.insert(PlayerInGame.TABLE_NAME, null, values);
				}
				cursorPlayers.moveToNext();
			}
			
			//Redirect vers l'activité de détail d'une rencontre
			Intent intent = new Intent(this, EventBoardActivity.class);
			intent.putExtra(EventBoardActivity.EVENT_BOARD_ID, eventId.toString());
			startActivity(intent);
		}
	}
}















