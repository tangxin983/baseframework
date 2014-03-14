package com.tx.framework.web.common.enums;

/**
 * 商品状态
 * 
 * @author tangx
 * 
 */
public enum MenuStatus {

	NORMAL(1), // 在售
	STOP(2), // 停售
	OOS(3);// 沽清

	private int value;

	private MenuStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
