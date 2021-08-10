package com.resmed.challenge.controller;

public enum Hands {
	ROCK(0), PAPER(1), SCISSOR(2);

	private int index;
	
	Hands(int i) {
		// TODO Auto-generated constructor stub
		this.index = i;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
