package com.tx.framework.web.entity;

import java.util.Date;

public class CardType extends UUIDEntity {

	private String cardtypename;
	private Long updegree;
	private String upcardid;
	private Double discount;
	private Byte needpwd;
	private Byte forcediscount;
	private Double exchangedegree;
	private Byte valid;
	private Date createtime;
	private Date updatetime;
	private Double precharge;
	private Byte predegree;
	private Byte discountdegree;
	private Double discountexchangedegree;
	private String memo;

	private String forcediscountchs;
	private String needpwdchs;
	private String predegreechs;
	private String discountdegreechs;

	public String getCardtypename() {
		return cardtypename;
	}

	public void setCardtypename(String cardtypename) {
		this.cardtypename = cardtypename;
	}

	public Long getUpdegree() {
		return updegree;
	}

	public void setUpdegree(Long updegree) {
		this.updegree = updegree;
	}

	public String getUpcardid() {
		return upcardid;
	}

	public void setUpcardid(String upcardid) {
		this.upcardid = upcardid;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Byte getNeedpwd() {
		return needpwd;
	}

	public void setNeedpwd(Byte needpwd) {
		this.needpwd = needpwd;
	}

	public Byte getForcediscount() {
		return forcediscount;
	}

	public void setForcediscount(Byte forcediscount) {
		this.forcediscount = forcediscount;
	}

	public Double getExchangedegree() {
		return exchangedegree;
	}

	public void setExchangedegree(Double exchangedegree) {
		this.exchangedegree = exchangedegree;
	}

	public Byte getValid() {
		return valid;
	}

	public void setValid(Byte valid) {
		this.valid = valid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Double getPrecharge() {
		return precharge;
	}

	public void setPrecharge(Double precharge) {
		this.precharge = precharge;
	}

	public Byte getPredegree() {
		return predegree;
	}

	public void setPredegree(Byte predegree) {
		this.predegree = predegree;
	}

	public Byte getDiscountdegree() {
		return discountdegree;
	}

	public void setDiscountdegree(Byte discountdegree) {
		this.discountdegree = discountdegree;
	}

	public Double getDiscountexchangedegree() {
		return discountexchangedegree;
	}

	public void setDiscountexchangedegree(Double discountexchangedegree) {
		this.discountexchangedegree = discountexchangedegree;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getForcediscountchs() {
		return forcediscountchs;
	}

	public void setForcediscountchs(String forcediscountchs) {
		this.forcediscountchs = forcediscountchs;
	}

	public String getNeedpwdchs() {
		return needpwdchs;
	}

	public void setNeedpwdchs(String needpwdchs) {
		this.needpwdchs = needpwdchs;
	}

	public String getPredegreechs() {
		return predegreechs;
	}

	public void setPredegreechs(String predegreechs) {
		this.predegreechs = predegreechs;
	}

	public String getDiscountdegreechs() {
		return discountdegreechs;
	}

	public void setDiscountdegreechs(String discountdegreechs) {
		this.discountdegreechs = discountdegreechs;
	}

}