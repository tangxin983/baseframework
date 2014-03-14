package com.tx.framework.web.common.persistence.entity;

import com.tx.framework.common.excel.ExcelAnnotation;

public class ImportMenu {

	@ExcelAnnotation(exportName = "商品分类")
	private String menuTypeName;

	@ExcelAnnotation(exportName = "商品名称")
	private String menuName;

	@ExcelAnnotation(exportName = "商品编码")
	private String code;

	@ExcelAnnotation(exportName = "单价(元)")
	private String price;

	@ExcelAnnotation(exportName = "结账单位")
	private String payAccount;

	@ExcelAnnotation(exportName = "是否打折")
	private String ratioName;

	@ExcelAnnotation(exportName = "退货验证")
	private String backAuthName;

	@ExcelAnnotation(exportName = "收银改价")
	private String changePriceName;

	@ExcelAnnotation(exportName = "商品介绍")
	private String memo;

	private Byte ratio;

	private Byte backAuth;

	private Byte changePrice;

	public String getMenuTypeName() {
		return menuTypeName;
	}

	public void setMenuTypeName(String menuTypeName) {
		this.menuTypeName = menuTypeName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getRatioName() {
		return ratioName;
	}

	public void setRatioName(String ratioName) {
		this.ratioName = ratioName;
	}

	public String getBackAuthName() {
		return backAuthName;
	}

	public void setBackAuthName(String backAuthName) {
		this.backAuthName = backAuthName;
	}

	public String getChangePriceName() {
		return changePriceName;
	}

	public void setChangePriceName(String changePriceName) {
		this.changePriceName = changePriceName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Byte getRatio() {
		return ratio;
	}

	public void setRatio(Byte ratio) {
		this.ratio = ratio;
	}

	public Byte getBackAuth() {
		return backAuth;
	}

	public void setBackAuth(Byte backAuth) {
		this.backAuth = backAuth;
	}

	public Byte getChangePrice() {
		return changePrice;
	}

	public void setChangePrice(Byte changePrice) {
		this.changePrice = changePrice;
	}

}