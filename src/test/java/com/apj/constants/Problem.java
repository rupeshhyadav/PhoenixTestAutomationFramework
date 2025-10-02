package com.apj.constants;

public enum Problem {
	SMARTPHONE_IS_RUNNING_SLOW(1), POOR_BATTERY_LIFE(2), SYNC_ISSUE(3);

	int code;

	Problem(int code) {
		this.code = code;

	}

	public int getCode() {
		return code;
	}

}
