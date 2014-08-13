package com.chakwak.tarotscoreboard.entities;

public class Player {
	
	private Integer mId;
	private String mName;
	
	public Integer getId() {
		return mId;
	}
	
	public void setId(int id) {
		mId = id;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public Player() {};
	
	public Player(int id, String name) {
		mId = id;
		mName = name;
	}
	
	@Override
	public String toString() {
		return mName;
	}
}
