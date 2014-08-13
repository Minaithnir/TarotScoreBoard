package com.chakwak.tarotscoreboard.enums;



public enum Prise {
	PETITE("Petite"),
	POUSSE("Pousse"),
	GARDE("Garde"),
	GARDE_SANS("Garde sans"),
	GARDE_CONTRE("Garde contre");
	
	private String mPrise;
	
	Prise(String prise) {
		mPrise = prise;
	}
	
	@Override
	public String toString() {
		return mPrise;
	}
	
	public static Prise get(String name) {
		for(Prise p:values()) {
			if(name.equals(p.mPrise)) {
				return p;
			}
		}
		return null;
	}
}
