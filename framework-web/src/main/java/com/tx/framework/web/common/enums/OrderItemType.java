package com.tx.framework.web.common.enums;

/**
 * 订单项类型
 * 
 * @author tangx
 * 
 */
public enum OrderItemType {

	NORMAL(1), // 正常
	RETURN(2); // 退菜

	private int value;

	private OrderItemType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
