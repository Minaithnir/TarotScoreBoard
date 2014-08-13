package com.chakwak.tarotscoreboard.activities;

import java.util.Date;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.chakwak.tarotscoreboard.R;
import com.chakwak.tarotscoreboard.ScoreBoardContract.Event;
import com.chakwak.tarotscoreboard.ScoreBoardContract.Player;
import com.chakwak.tarotscoreboard.ScoreBoardContract.PlayerToEvent;
import com.chakwak.tarotscoreboard.ScoreBoardDbHelper;
import com.chakwak.tarotscoreboard.dao.PlayerDao;

public class CreateEventActivity extends ActionBarActivity {
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		
		// Retrieve players names & id from dao    	
    	Cursor c = PlayerDao.getAllPlayers(this);
		
		String[] from = new String[] {Player.COLUMN_NAME_PLAYER_NAME};
		int[] to = new int[] {android.R.id.text1};
    	
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(
			this, android.R.layout.simple_list_item_multiple_choice, c, from, to);
    	
    	ListView listView = (ListView)findViewById(R.id.list_event_create_players);
    	listView.setAdapter(adapter);
    	listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	
	/*
	 * Method checking the form and creating the event if needed
	 * called by in-layout button
	 */
	public void onCreateEvent(View view) {
		ListView playerList = (ListView)findViewById(R.id.list_event_create_players);
		SparseBooleanArray checkedPlayers = playerList.getCheckedItemPositions();
		if(checkedPlayers.size() < 3) {
			Toast.makeText(this, R.string.toast_not_enough_player, Toast.LENGTH_LONG).show();
		} else {
			String location = ((EditText)findViewById(R.id.edit_event_creation_location)).getText().toString();
			
			if(location.length()<=0) {
				Toast.makeText(this, R.string.toast_location_empty, Toast.LENGTH_LONG).show();
			} else {
				ContentValues values = new ContentValues();
				values.put(Event.COLUMN_NAME_EVENT_DATE, new Date().getTime()/1000);
				values.put(Event.COLUMN_NAME_LOCATION, location);
				values.put(Event.COLUMN_NAME_FINISHED, false);
	
				SQLiteDatabase db = ScoreBoardDbHelper.getWriteDb(this);
				Long eventId = db.insert(Event.TABLE_NAME, null, values);
				
				int itemCount = playerList.getCount();
				Log.e("itemCount", String.valueOf(itemCount));
				Cursor item = null;
				for(int i=0; i<itemCount; i++) {
					if(checkedPlayers.get(i)==true) {
						item = (Cursor) playerList.getItemAtPosition(i);
						values.clear();
						values.put(PlayerToEvent.COLUMN_NAME_EVENT_ID, eventId);
						values.put(PlayerToEvent.COLUMN_NAME_PLAYER_ID, item.getInt(item.getColumnIndex(Player.COLUMN_NAME_PLAYER_ID)));
						
						db.insert(PlayerToEvent.TABLE_NAME, null, values);
					}
				}
				
				//Redirect vers l'activité de détail d'une rencontre
				Intent intent = new Intent(this, EventBoardActivity.class);
				intent.putExtra(EventBoardActivity.EVENT_BOARD_ID, eventId.toString());
				startActivity(intent);
			}
		}
	}
}
