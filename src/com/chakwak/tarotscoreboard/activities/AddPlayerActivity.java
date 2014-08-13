package com.chakwak.tarotscoreboard.activities;
/*
 * AddPlayerActivity.java
 * 
 * handle new player creation.
 * accessible from the main screen option while on the players list
 * 
 */

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chakwak.tarotscoreboard.R;
import com.chakwak.tarotscoreboard.ScoreBoardContract.Player;
import com.chakwak.tarotscoreboard.ScoreBoardDbHelper;
import com.chakwak.tarotscoreboard.dao.PlayerDao;

public class AddPlayerActivity extends ActionBarActivity {
	
	public static String PLAYER_CREATED = "com.chakwak.tarotscoreboard.PLAYER_CREATED";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_player);
	}
	
	/*
	 * Player creation method called from onClick on a button in the layout
	 */
	public void createPlayer(View view) {
		EditText et = (EditText) findViewById(R.id.new_player_edit);
		String playerName = et.getText().toString();

		// On enregistre le nouveau joueur si le nom n'est pas déjà pris.
		if(playerName.length()<=0) {
			Toast.makeText(this, R.string.toast_add_player_empty, Toast.LENGTH_SHORT).show();
		} else {
			if(PlayerDao.isNameTaken(this, playerName)) {
				Toast.makeText(this, getString(R.string.toast_name_exist, playerName), Toast.LENGTH_SHORT).show();
			} else {
				SQLiteDatabase db = ScoreBoardDbHelper.getWriteDb(this);
				
				ContentValues values = new ContentValues();
				values.put(Player.COLUMN_NAME_PLAYER_NAME, playerName);
				
				db.insert(Player.TABLE_NAME, null, values);
				
				// Create the intent and redirect the user to the main screen
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra(PLAYER_CREATED, playerName);
				startActivity(intent);
			}
		}
	}
}
