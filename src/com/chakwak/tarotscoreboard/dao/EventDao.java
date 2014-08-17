package com.chakwak.tarotscoreboard.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chakwak.tarotscoreboard.ScoreBoardContract.Event;
import com.chakwak.tarotscoreboard.ScoreBoardDbHelper;

public class EventDao {

	public static Cursor getAllEvents(Context context) {
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);
    	
    	String[] projection = {
			Event.COLUMN_NAME_EVENT_ID,
			"strftime( \"%d-%m-%Y %H:%M\", cast(" + Event.COLUMN_NAME_EVENT_DATE + " as TEXT),'unixepoch', 'localtime' ) as " + Event.COLUMN_NAME_EVENT_DATE,
			Event.COLUMN_NAME_LOCATION,
			Event.COLUMN_NAME_FINISHED
    	};
    	
    	String sortOrder = 
			Event.COLUMN_NAME_EVENT_DATE + " DESC";
    	
    	Cursor c = db.query(
			Event.TABLE_NAME, 
			projection, 
			null, 
			null, 
			null, 
			null, 
			sortOrder);
    	
    	return c;
	}
	
	public static void deleteEvent(Context context, Integer eventId) {
		GameDao.deleteRelatedGames(context, eventId);
		
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);
    	
    	String where = Event.COLUMN_NAME_EVENT_ID + " = ?";
    	String args[] = {String.valueOf(eventId)};
    	
    	db.delete(
    		Event.TABLE_NAME, 
			where, 
			args);
    	
	}
}
