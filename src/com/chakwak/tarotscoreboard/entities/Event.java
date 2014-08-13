package com.chakwak.tarotscoreboard.entities;

import java.util.ArrayList;
import java.util.Date;

public class Event {
	
	private Date mStartDate;
	private String mLocation;
	private ArrayList<Player> mPlayersList;
	private ArrayList<Game> mGamesList;
	private Boolean mFinished;
	
	public Date getStartDate() {
		return mStartDate;
	}
	
	public void setStartDate(Date startDate) {
		mStartDate = startDate;
	}
	
	public String getLocation() {
		return mLocation;
	}
	
	public void setLocation(String location) {
		mLocation = location;
	}
	
	public ArrayList<Player> getPlayersList() {
		return mPlayersList;
	}
	
	public void setPlayerList(ArrayList<Player> playersList) {
		mPlayersList = playersList;
	}
	
	public ArrayList<Game> getGamesList() {
		return mGamesList;
	}
	
	public void setGamesList(ArrayList<Game> gamesList) {
		mGamesList = gamesList;
	}
	
	public Boolean getFinished() {
		return mFinished;
	}

	public void setFinished(Boolean finished) {
		this.mFinished = finished;
	}

	public Event(String location, ArrayList<Player> players, Date date) {
		mLocation = location;
		mPlayersList = players;
		mStartDate = date;
	}

}
