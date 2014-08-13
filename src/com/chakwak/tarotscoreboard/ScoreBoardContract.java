package com.chakwak.tarotscoreboard;
/*
 * ScoreBoardContract.java 
 * 
 * this file contain the class describing the database structure and create/update queries
 * 
 */

import android.provider.BaseColumns;

public final class ScoreBoardContract {
	
	public ScoreBoardContract() {}
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	
	public static abstract class Player implements BaseColumns {
		public static final String TABLE_NAME = "player";
		public static final String COLUMN_NAME_PLAYER_ID = "_id";
		public static final String COLUMN_NAME_PLAYER_NAME = "name";
	}
	
	public static final String SQL_CREATE_PLAYERS =
	    "CREATE TABLE " + Player.TABLE_NAME + " (" +
		Player.COLUMN_NAME_PLAYER_ID + " INTEGER PRIMARY KEY," +
	    Player.COLUMN_NAME_PLAYER_NAME + TEXT_TYPE +
	    " )";

	public static final String SQL_DELETE_PLAYERS =
	    "DROP TABLE IF EXISTS " + Player.TABLE_NAME;

	public static abstract class Event implements BaseColumns {
		public static final String TABLE_NAME = "event";
		public static final String COLUMN_NAME_EVENT_ID = "_id";
		public static final String COLUMN_NAME_EVENT_DATE = "date";
		public static final String COLUMN_NAME_LOCATION = "location";
		public static final String COLUMN_NAME_FINISHED = "finished";
	}
	
	public static final String SQL_CREATE_EVENTS =
	    "CREATE TABLE " + Event.TABLE_NAME + " (" +
		Event.COLUMN_NAME_EVENT_ID + " INTEGER PRIMARY KEY," +
	    Event.COLUMN_NAME_EVENT_DATE + INTEGER_TYPE + COMMA_SEP +
	    Event.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
	    Event.COLUMN_NAME_FINISHED + INTEGER_TYPE +
	    " )";

	public static final String SQL_DELETE_EVENTS =
	    "DROP TABLE IF EXISTS " + Event.TABLE_NAME;
	
	public static abstract class PlayerToEvent implements BaseColumns {
		public static final String TABLE_NAME = "player_at_event";
		public static final String COLUMN_NAME_PTE_ID = "_Id";
		public static final String COLUMN_NAME_EVENT_ID = "eventId";
		public static final String COLUMN_NAME_PLAYER_ID = "playerId";
	}
	
	public static final String SQL_CREATE_PTOE =
	    "CREATE TABLE " + PlayerToEvent.TABLE_NAME + " (" +
	    PlayerToEvent.COLUMN_NAME_PTE_ID + "INTEGER PRIMARY KEY," +
		PlayerToEvent.COLUMN_NAME_EVENT_ID + INTEGER_TYPE + COMMA_SEP +
	    PlayerToEvent.COLUMN_NAME_PLAYER_ID + INTEGER_TYPE + 
	    " )";

	public static final String SQL_DELETE_PTOE =
	    "DROP TABLE IF EXISTS " + PlayerToEvent.TABLE_NAME;
	
	public static abstract class Game implements BaseColumns {
		public static final String TABLE_NAME = "game";
		public static final String COLUMN_NAME_GAME_ID = "_id";
		public static final String COLUMN_NAME_EVENT_ID = "evenId";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_TAKER_ID = "takerId";
		public static final String COLUMN_NAME_PRISE = "prise";
		public static final String COLUMN_NAME_APPEL = "appel";
		public static final String COLUMN_NAME_ASSOCIATE_ID = "associateId";
		public static final String COLUMN_NAME_VALID = "validated";
		public static final String COLUMN_NAME_RESULT = "result";
	}
		
	public static final String SQL_CREATE_GAMES =
	    "CREATE TABLE " + Game.TABLE_NAME + " (" +
		Game.COLUMN_NAME_GAME_ID + " INTEGER PRIMARY KEY," +
	    Game.COLUMN_NAME_DATE + INTEGER_TYPE + COMMA_SEP +
	    Game.COLUMN_NAME_EVENT_ID + INTEGER_TYPE + COMMA_SEP +
	    Game.COLUMN_NAME_TAKER_ID + INTEGER_TYPE + COMMA_SEP +
		Game.COLUMN_NAME_PRISE + INTEGER_TYPE + COMMA_SEP +
	    Game.COLUMN_NAME_APPEL + INTEGER_TYPE + COMMA_SEP +
	    Game.COLUMN_NAME_ASSOCIATE_ID + INTEGER_TYPE + COMMA_SEP +
	    Game.COLUMN_NAME_VALID + INTEGER_TYPE + COMMA_SEP +
	    Game.COLUMN_NAME_RESULT + INTEGER_TYPE +
	    " )";

	public static final String SQL_DELETE_GAMES =
	    "DROP TABLE IF EXISTS " + Game.TABLE_NAME;
	
	public static abstract class PlayerInGame implements BaseColumns {
		public static final String TABLE_NAME = "player_not_in_game";
		public static final String COLUMN_NAME_PIG_ID = "_id";
		public static final String COLUMN_NAME_GAME_ID = "gameId";
		public static final String COLUMN_NAME_PLAYER_ID = "playerId";
	}
	
	public static final String SQL_CREATE_PIG =
	    "CREATE TABLE " + PlayerInGame.TABLE_NAME + " (" +
	    PlayerInGame.COLUMN_NAME_PIG_ID + " INTEGER PRIMARY KEY," + 
		PlayerInGame.COLUMN_NAME_GAME_ID + INTEGER_TYPE + COMMA_SEP +
	    PlayerInGame.COLUMN_NAME_PLAYER_ID + INTEGER_TYPE +
	    " )";
	
	public static final String SQL_DELETE_PIG =
	    "DROP TABLE IF EXISTS " + PlayerInGame.TABLE_NAME;
	
	public static abstract class Flag implements BaseColumns {
		public static final String TABLE_NAME = "flag";
		public static final String COLUMN_NAME_FLAG_ID = "_id";
		public static final String COLUMN_NAME_GAME_ID = "gameId";
		public static final String COLUMN_NAME_PLAYER_ID = "playerId";
		public static final String COLUMN_NAME_FLAG_TYPE = "flagType";
	}
	
	public static final String SQL_CREATE_FLAGS =
	    "CREATE TABLE " + Flag.TABLE_NAME + " (" +
	    Flag.COLUMN_NAME_FLAG_ID + " INTEGER PRIMARY KEY," +
		Flag.COLUMN_NAME_GAME_ID + INTEGER_TYPE + COMMA_SEP +
		Flag.COLUMN_NAME_PLAYER_ID + INTEGER_TYPE + COMMA_SEP +
	    Flag.COLUMN_NAME_FLAG_TYPE + INTEGER_TYPE +
	    " )";
	
	public static final String SQL_DELETE_FLAGS =
	    "DROP TABLE IF EXISTS " + Flag.TABLE_NAME;
	
}
