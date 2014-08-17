package com.chakwak.tarotscoreboard.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chakwak.tarotscoreboard.ScoreBoardContract.Event;
import com.chakwak.tarotscoreboard.ScoreBoardContract.Game;
import com.chakwak.tarotscoreboard.ScoreBoardDbHelper;

public class GameDao {

	public static Cursor getGames(Context context, Long eventId) {
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);
    	
    	String table = 
			Game.TABLE_NAME + " as g, " + Event.TABLE_NAME + " as e";
    	
    	String[] projection = {
    		"g."+Game.COLUMN_NAME_TAKER_ID,
    		" g."+Game.COLUMN_NAME_PRISE,
    		" g."+Game.COLUMN_NAME_APPEL, 
    		" g."+Game.COLUMN_NAME_ASSOCIATE_ID, 
    		" g."+Game.COLUMN_NAME_VALID,
    		" g."+Game.COLUMN_NAME_RESULT,
    		" g."+Game.COLUMN_NAME_GAME_ID
    	};
    	
    	String where = 
			"g."+Game.COLUMN_NAME_EVENT_ID + " = e."+Event.COLUMN_NAME_EVENT_ID + 
			" AND e."+Event.COLUMN_NAME_EVENT_ID + " = " + eventId.toString();
    	
    	String sortOrder = "g."+Game.COLUMN_NAME_DATE + " ASC";
    	
    	Cursor c = db.query(
			table, 
			projection, 
			where, 
			null, 
			null, 
			null, 
			sortOrder);
    	
    	return c;
	}
	
	public static void deleteRelatedGames(Context context, Integer eventId) {
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);
    	
    	String where = Game.COLUMN_NAME_EVENT_ID + " = ?";
    	String args[] = {String.valueOf(eventId)};
    	
    	db.delete(
    		Game.TABLE_NAME, 
			where, 
			args);
	}
	
}
