package com.chakwak.tarotscoreboard.entities;

import com.chakwak.tarotscoreboard.enums.FlagType;

public class Flag {
	private FlagType mType;
	private Player mPlayer;
	
	public FlagType getType() {
		return mType;
	}
	public void setType(FlagType type) {
		this.mType = type;
	}
	public Player getPlayer() {
		return mPlayer;
	}
	public void setPlayer(Player player) {
		this.mPlayer = player;
	}
}
