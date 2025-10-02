package com.apj.constants;

public enum MST_MODEL {
	NEXUS_2_BLUE(1), GALLEXY(2);

	int code;

	MST_MODEL(int code) {
		this.code = code;

	}

	public int getCode() {
		return code;
	}

}
