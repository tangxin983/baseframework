package com.tx.framework.web.common.enums;

/**
 * 付款类型
 * @author tangx
 *
 */
public enum PayType {

	CARD(1), // 卡消费
	CASH(2), // 现金
	CREDIT_CARD(3); // 信用卡

	private int value;

	private PayType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
