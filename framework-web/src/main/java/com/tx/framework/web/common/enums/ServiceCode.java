package com.tx.framework.web.common.enums;

/**
 * 接口代码定义
 * 
 * @author tangx
 * 
 */
public enum ServiceCode {

	SUCCESS(0), //成功
	FAIL(1),  //失败
	PARSE_ERROR(2),  //参数解析失败
	ILLEGAL_PARAM(3),  //必填参数为空
	AUTH_FAIL(4), //认证失败
	
	USER_NOT_FOUND(11),//找不到用户
	
	CARD_NOT_FOUND(21),//找不到卡
	ILLEGAL_CARD_STATUS(22),//卡状态非法
	NOT_BIND_CUSTOMER(23),//卡未绑定会员
	NOT_ENOUGH_BALANCE(24),//卡余额不足
	
	MENU_NOT_FOUND(31),//找不到商品
	ILLEGAL_MENU_STATUS(32),//商品状态非法
	
	ORDER_NOT_FOUND(41),//找不到订单
	ILLEGAL_ORDER_STATUS(42),//订单状态非法
	ITEM_NOT_FOUND(43),//找不到订单项
	ILLEGAL_ITEM_STATUS(44),//订单项状态为不可退菜
	
	AMOUNT_NOT_EQUAL(51);//实收金额与应付金额不等
	
	private int value;

	private ServiceCode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
