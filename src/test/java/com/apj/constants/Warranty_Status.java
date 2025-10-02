package com.apj.constants;

public enum Warranty_Status {
	IN_WARRANTY(1), OUT_WARRANTY(2);

	int code;

	Warranty_Status(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
