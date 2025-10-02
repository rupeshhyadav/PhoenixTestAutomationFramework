package com.apj.constants;

public enum OEM {
	GOOGLE(1), APPLE(2);

	int code;

	OEM(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
