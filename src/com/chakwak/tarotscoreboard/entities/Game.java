package com.chakwak.tarotscoreboard.entities;

import java.util.ArrayList;
import java.util.Date;

import com.chakwak.tarotscoreboard.enums.Appel;
import com.chakwak.tarotscoreboard.enums.Prise;

public class Game {
	
	private Date mDate;
	private Player mTaker;
	private Prise mPrise;
	private Appel mAppel;
	private Player mAssociate;
	private Boolean mValidated;
	private Integer mResult;
	private ArrayList<Player> mNonPlayer;
	private ArrayList<Flag> mFlags;
	
	public Game(Date date, Player taker, Prise prise, Appel appel, Player associate, Boolean validated, Integer result) {
		mDate = date;
		mTaker = taker;
		mPrise = prise;
		mAppel = appel;
		mAssociate = associate;
		mValidated = validated;
		mResult = result;
	}
	
	public Date getDate() {
		return mDate;
	}
	public void setDate(Date date) {
		this.mDate = date;
	}
	
	public Player getTaker() {
		return mTaker;
	}
	public void setTaker(Player taker) {
		this.mTaker = taker;
	}
	
	public Prise getPrise() {
		return mPrise;
	}
	public void setPrise(Prise prise) {
		this.mPrise = prise;
	}
	
	public Appel getAppel() {
		return mAppel;
	}
	public void setAppel(Appel appel) {
		this.mAppel = appel;
	}
	
	public Player getAssociate() {
		return mAssociate;
	}
	public void setAssociate(Player associate) {
		this.mAssociate = associate;
	}
	
	public Boolean getValidated() {
		return mValidated;
	}
	
	public void setValidated(Boolean b) {
		mValidated = b;
	}
	
	public Integer getResult() {
		return mResult;
	}
	public void setResult(Integer result) {
		this.mResult = result;
	}
	
	public ArrayList<Player> getNonPlayer() {
		return mNonPlayer;
	}
	public void setNonPlayer(ArrayList<Player> nonPlayer) {
		this.mNonPlayer = nonPlayer;
	}
	
	public ArrayList<Flag> getFlags() {
		return mFlags;
	}
	public void setFlags(ArrayList<Flag> flags) {
		this.mFlags = flags;
	}
	
}
