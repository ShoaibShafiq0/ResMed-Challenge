package com.resmed.challenge.model.statistics;

public enum MatchStatus {
	WON, LOST, DRAW;

	public MatchStatus getEnumValue(int num) {
		switch (num) {
		case 0:
			return DRAW;
		case 1:
			return WON;
		default:
			return LOST;
		}
	}
}
