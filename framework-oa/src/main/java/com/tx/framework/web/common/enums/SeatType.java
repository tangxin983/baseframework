package com.tx.framework.web.common.enums;

public enum SeatType {

	BOX("包厢"), NON_BOX("散座");

	private String name;

	private SeatType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
