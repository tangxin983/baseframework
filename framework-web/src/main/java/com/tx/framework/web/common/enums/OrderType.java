package com.tx.framework.web.common.enums;

/**
 * 订单类型
 * @author tangx
 *
 */
public enum OrderType {

	NORMAL(1), // 正常
	RESERVE(2);// 预定

	private int value;

	private OrderType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
