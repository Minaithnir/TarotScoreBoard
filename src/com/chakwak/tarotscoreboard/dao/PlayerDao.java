package com.chakwak.tarotscoreboard.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chakwak.tarotscoreboard.ScoreBoardContract.Player;
import com.chakwak.tarotscoreboard.ScoreBoardContract.PlayerInGame;
import com.chakwak.tarotscoreboard.ScoreBoardContract.PlayerToEvent;
import com.chakwak.tarotscoreboard.ScoreBoardDbHelper;

public class PlayerDao {
	
	public static Cursor getAllPlayers(Context context) {
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);
    	
    	String[] projection = {
			Player.COLUMN_NAME_PLAYER_ID,
			Player.COLUMN_NAME_PLAYER_NAME
    	};
    	
    	String sortOrder = 
			"UPPER(" + Player.COLUMN_NAME_PLAYER_NAME + ") ASC";
    	
    	Cursor c = db.query(
			Player.TABLE_NAME, 
			projection, 
			null, 
			null, 
			null, 
			null, 
			sortOrder);
    	
    	return c;
	}

	public static Boolean isNameTaken(Context context, String playerName) {
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);
    	
    	String[] projection = {
			Player.COLUMN_NAME_PLAYER_NAME
    	};
    	
    	String where = Player.COLUMN_NAME_PLAYER_NAME + " = ?";
    	String args[] = {playerName};
    	
    	Cursor c = db.query(
			Player.TABLE_NAME, 
			projection, 
			where, 
			args, 
			null, 
			null, 
			null);
    	
    	return c.getCount() != 0;
	}
	
	public static Cursor getPlayersInEvent(Context context, Long eventId) {
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);
    	
    	String table = 
			Player.TABLE_NAME + ", " + PlayerToEvent.TABLE_NAME;
    	
    	String[] projection = {
			Player.TABLE_NAME + "." + Player.COLUMN_NAME_PLAYER_ID + " as " + Player.COLUMN_NAME_PLAYER_ID,
			Player.TABLE_NAME + "." + Player.COLUMN_NAME_PLAYER_NAME + " as " + Player.COLUMN_NAME_PLAYER_NAME
    	};
    	
    	String where = 
			Player.TABLE_NAME+"."+Player.COLUMN_NAME_PLAYER_ID + " = " +  PlayerToEvent.TABLE_NAME+"."+PlayerToEvent.COLUMN_NAME_PLAYER_ID + 
			" AND " + PlayerToEvent.TABLE_NAME+"."+PlayerToEvent.COLUMN_NAME_EVENT_ID + " = " + eventId.toString();
    	
    	String sortOrder = 
			"UPPER(" + Player.COLUMN_NAME_PLAYER_NAME + ") ASC";
    	
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
	
	public static Cursor getPlayersInGame(Context context, Long gameId) {
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);
    	
    	String table = 
			PlayerInGame.TABLE_NAME;
    	
    	String[] projection = {
			PlayerInGame.COLUMN_NAME_PLAYER_ID
    	};
    	
    	String where = 
			PlayerInGame.COLUMN_NAME_GAME_ID + " = " + gameId.toString();
    	
    	Cursor c = db.query(
			table, 
			projection, 
			where, 
			null, 
			null, 
			null, 
			null);
    	
    	return c;
	}
	
	public static boolean isPlayerInAnyEvent(Context context, Integer playerId) {
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);

    	String[] projection = {
			PlayerInGame.COLUMN_NAME_PIG_ID
    	};
    	
    	String where = PlayerInGame.COLUMN_NAME_PLAYER_ID + " = ?";
    	String args[] = {String.valueOf(playerId)};
    	
    	Cursor c = db.query(
    		PlayerInGame.TABLE_NAME, 
			projection, 
			where, 
			args, 
			null, 
			null, 
			null);
    	
    	return c.getCount() != 0;
	}
	
	public static void deletePlayer(Context context, Integer playerId) {
    	SQLiteDatabase db = ScoreBoardDbHelper.getReadDb(context);
    	
    	String where = Player.COLUMN_NAME_PLAYER_ID + " = ?";
    	String args[] = {String.valueOf(playerId)};
    	
    	db.delete(
    		Player.TABLE_NAME, 
			where, 
			args);
    	
	}
}
