package com.tx.framework.web.entity;

@SuppressWarnings("serial")
public class Bill extends UUIDEntity {

	private String currDate;

	private Double consumeAmount;

	private Double discountAmount;

	private Double recieveAmount;

	private Double resultAmount;

	private String operator;

	private Long operateTime;

	private String cardId;

	private String cardCode;

	private Double ratio;

	private Integer invoice;

	private Integer status;

	private Integer type;

	private String orderId;

	private Double customDiscount;

	public Double getCustomDiscount() {
		return customDiscount;
	}

	public void setCustomDiscount(Double customDiscount) {
		this.customDiscount = customDiscount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
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

	public Double getRecieveAmount() {
		return recieveAmount;
	}

	public void setRecieveAmount(Double recieveAmount) {
		this.recieveAmount = recieveAmount;
	}

	public Double getResultAmount() {
		return resultAmount;
	}

	public void setResultAmount(Double resultAmount) {
		this.resultAmount = resultAmount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Long operateTime) {
		this.operateTime = operateTime;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public Integer getInvoice() {
		return invoice;
	}

	public void setInvoice(Integer invoice) {
		this.invoice = invoice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}