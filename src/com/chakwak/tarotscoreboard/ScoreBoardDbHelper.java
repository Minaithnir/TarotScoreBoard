package com.chakwak.tarotscoreboard;
/*
 * ScoreBoardDbHelper.java
 * 
 * Contains the helper for handling database connection
 * and handle the creation / update and downgrade of the database
 * 
 */

import com.chakwak.tarotscoreboard.ScoreBoardContract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreBoardDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "TarotScoreBoard.db";

	public static SQLiteDatabase dbRead = null;
	public static SQLiteDatabase dbWrite = null;
    
    public ScoreBoardDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
    public static SQLiteDatabase getWriteDb(Context context) {
    	if(dbWrite == null) {
        	ScoreBoardDbHelper dbHelper = new ScoreBoardDbHelper(context);
        	dbWrite = dbHelper.getWritableDatabase();
    	}
    	return dbWrite;
    }
    
    public static SQLiteDatabase getReadDb(Context context) {
    	if(dbRead == null) {
        	ScoreBoardDbHelper dbHelper = new ScoreBoardDbHelper(context);
        	dbRead = dbHelper.getReadableDatabase();
    	}
    	return dbRead;
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ScoreBoardContract.SQL_CREATE_PLAYERS);
		db.execSQL(ScoreBoardContract.SQL_CREATE_EVENTS);
		db.execSQL(ScoreBoardContract.SQL_CREATE_PTOE);
		db.execSQL(ScoreBoardContract.SQL_CREATE_GAMES);
		db.execSQL(ScoreBoardContract.SQL_CREATE_PIG);
		db.execSQL(ScoreBoardContract.SQL_CREATE_FLAGS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL(ScoreBoardContract.SQL_DELETE_PLAYERS);
		db.execSQL(ScoreBoardContract.SQL_DELETE_EVENTS);
		db.execSQL(ScoreBoardContract.SQL_DELETE_PTOE);
		db.execSQL(ScoreBoardContract.SQL_DELETE_GAMES);
		db.execSQL(ScoreBoardContract.SQL_DELETE_PIG);
		db.execSQL(ScoreBoardContract.SQL_DELETE_FLAGS);
		
		onCreate(db);
	}

	@Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
