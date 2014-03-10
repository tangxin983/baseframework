package com.tx.framework.web.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Card extends UUIDEntity {

	private String cardTypeId;

	private String customerId;

	private String code;

	private String innerCode;

	private String pwd;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date beginDate;

	private Date endDate;

	private String operator;

	private String auditor;

	private Date auditDate;

	private Double balance;

	private Double giftBalance;

	private Double realBalance;

	private Long degree;

	private Double payAmount;

	private Double consumeAmount;

	private Double discountAmount;

	private Byte status;

	private Byte getStatus;

	private Date createTime;

	private Date updateTime;

	private Date activeDate;

	private String cardTypeName;

	private String customerName;

	private Double precharge;

	private Double charge;// 卡充值、退卡模块使用

	private Double returnBalance;

	private String cardStatus;

	private String salt;

	private String updatePwd;// 建卡模块更新密码用

	private Double discount;// 折扣

	private Byte forcediscount;// 是否强制打折

	public Byte getForcediscount() {
		return forcediscount;
	}

	public void setForcediscount(Byte forcediscount) {
		this.forcediscount = forcediscount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getUpdatePwd() {
		return updatePwd;
	}

	public void setUpdatePwd(String updatePwd) {
		this.updatePwd = updatePwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public Double getReturnBalance() {
		return returnBalance;
	}

	public void setReturnBalance(Double returnBalance) {
		this.returnBalance = returnBalance;
	}

	public Double getCharge() {
		return charge;
	}

	public void setCharge(Double charge) {
		this.charge = charge;
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

	public Double getPrecharge() {
		return precharge;
	}

	public void setPrecharge(Double precharge) {
		this.precharge = precharge;
	}

	public String getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(String cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

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

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getGiftBalance() {
		return giftBalance;
	}

	public void setGiftBalance(Double giftBalance) {
		this.giftBalance = giftBalance;
	}

	public Double getRealBalance() {
		return realBalance;
	}

	public void setRealBalance(Double realBalance) {
		this.realBalance = realBalance;
	}

	public Long getDegree() {
		return degree;
	}

	public void setDegree(Long degree) {
		this.degree = degree;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getGetStatus() {
		return getStatus;
	}

	public void setGetStatus(Byte getStatus) {
		this.getStatus = getStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}
}