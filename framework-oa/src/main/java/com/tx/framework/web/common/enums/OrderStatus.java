package com.tx.framework.web.common.enums;

/**
 * 订单状态
 * 
 * @author tangx
 * 
 */
public enum OrderStatus {

	NORMAL(1), // 正常
	CANCEL(3), // 撤销
	CHECKOUT(4);// 结账

	private int value;

	private OrderStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
