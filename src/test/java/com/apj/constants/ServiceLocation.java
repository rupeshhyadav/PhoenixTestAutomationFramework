package com.apj.constants;

public enum ServiceLocation {
	SERVICE_LOCATION_A(1), SERVICE_LOCATION_B(2);

	int code;

	ServiceLocation(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
