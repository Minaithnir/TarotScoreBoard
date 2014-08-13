package com.chakwak.tarotscoreboard.enums;

public enum Appel {
	AUCUN("Aucun"),
	COEUR("Coeur"),
	CARREAU("Carreau"),
	TREFLE("Trèfle"),
	PIQUE("Pique");
	
	private String mAppel;
	
	Appel(String appel) {
		mAppel = appel;
	}
	
	@Override
	public String toString() {
		return mAppel;
	}
	
	public static Appel get(String name) {
		for(Appel a:values()) {
			if(name.equals(a.mAppel)) {
				return a;
			}
		}
		return null;
	}
}
