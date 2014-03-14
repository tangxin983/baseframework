package com.tx.framework.web.common.persistence.entity;

import java.util.Date;
import java.util.List;

public class Menu extends IdEntity {

	private String menuTypeId;

	private Integer sort;

	private String code;

	private String menuName;

	private String taste;

	private String spell;

	private String spell2;

	private String buyAccount;

	private String payAccount;

	private Double price;

	private Double specialPrice;

	private Double memberPrice;

	private Double reservePrice;

	private String attachmentId;

	private Integer attachmentVer;

	private String specId;

	private Integer consume;

	private String memo;

	private Byte ratio;

	private Byte status;

	private Date createTime;

	private Date updateTime;

	private String menuTypeName;

	private String ratioName;

	private Byte backAuth;

	private Byte changePrice;

	private String backAuthName;

	private String changePriceName;

	private String statusName;

	private List<Attachment> attachments;
	
	private String imagePath;// 图片地址

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public String getRatioName() {
		return ratioName;
	}

	public void setRatioName(String ratioName) {
		this.ratioName = ratioName;
	}

	public String getMenuTypeName() {
		return menuTypeName;
	}

	public void setMenuTypeName(String menuTypeName) {
		this.menuTypeName = menuTypeName;
	}

	public String getMenuTypeId() {
		return menuTypeId;
	}

	public void setMenuTypeId(String menuTypeId) {
		this.menuTypeId = menuTypeId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getSpell2() {
		return spell2;
	}

	public void setSpell2(String spell2) {
		this.spell2 = spell2;
	}

	public String getBuyAccount() {
		return buyAccount;
	}

	public void setBuyAccount(String buyAccount) {
		this.buyAccount = buyAccount;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Double specialPrice) {
		this.specialPrice = specialPrice;
	}

	public Double getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Double memberPrice) {
		this.memberPrice = memberPrice;
	}

	public Double getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(Double reservePrice) {
		this.reservePrice = reservePrice;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Integer getAttachmentVer() {
		return attachmentVer;
	}

	public void setAttachmentVer(Integer attachmentVer) {
		this.attachmentVer = attachmentVer;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public Integer getConsume() {
		return consume;
	}

	public void setConsume(Integer consume) {
		this.consume = consume;
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

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
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
}