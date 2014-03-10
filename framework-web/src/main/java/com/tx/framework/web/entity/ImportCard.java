package com.tx.framework.web.entity;

import com.tx.framework.common.excel.ExcelAnnotation;

public class ImportCard {

	@ExcelAnnotation(exportName = "卡号")
	private String code;

	@ExcelAnnotation(exportName = "内部卡号")
	private String innerCode;

	@ExcelAnnotation(exportName = "卡密码")
	private String pwd;

	@ExcelAnnotation(exportName = "卡类型")
	private String cardTypeName;

	@ExcelAnnotation(exportName = "会员")
	private String customerName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}