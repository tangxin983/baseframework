package com.tx.framework.web.common.persistence.entity;

import java.util.Date;

public class OperateFlow extends IdEntity {

	private Byte action;

	private String cardid;

	private String customerid;

	private Double pay;

	private String operatorid;

	private String disposename;

	private String payid;

	private String shopentityid;

	private Byte valid;

	private Date optime;

	private String opAction;// action中文

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getPayid() {
		return payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}

	public String getShopentityid() {
		return shopentityid;
	}

	public void setShopentityid(String shopentityid) {
		this.shopentityid = shopentityid;
	}

	public String getOpAction() {
		return opAction;
	}

	public void setOpAction(String opAction) {
		this.opAction = opAction;
	}

	public Byte getAction() {
		return action;
	}

	public void setAction(Byte action) {
		this.action = action;
	}

	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public String getOperatorid() {
		return operatorid;
	}

	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}

	public String getDisposename() {
		return disposename;
	}

	public void setDisposename(String disposename) {
		this.disposename = disposename;
	}

	public Byte getValid() {
		return valid;
	}

	public void setValid(Byte valid) {
		this.valid = valid;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}
}