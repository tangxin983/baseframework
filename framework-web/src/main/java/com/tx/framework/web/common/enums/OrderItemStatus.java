package com.tx.framework.web.common.enums;

/**
 * 订单项状态
 * 
 * @author tangx
 * 
 */
public enum OrderItemStatus {

	NORMAL(1), // 配菜
	WAIT(2), // 待配
	DONE(3), // 出菜
	HUA(4), // 划菜
	SERVED(5); // 已上菜

	private int value;

	private OrderItemStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
