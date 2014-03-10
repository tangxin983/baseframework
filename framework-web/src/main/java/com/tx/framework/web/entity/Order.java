package com.tx.framework.web.entity;

import java.util.List;

public class Order extends UUIDEntity {

	private Integer type;

	private String code;

	private String innerCode;

	private String seatId;

	private String reserveId;

	private String billId;

	private String workerId;

	private Integer peopleNum;

	private Long createTime;

	private Long endTime;

	private Integer status;

	private String memo;

	private Integer directSend;
	
	private Integer urge;

	private Integer itemStatus;// 接口用 表示要查询的订单项状态

	private Integer itemType;// 接口用 表示要查询的订单项类型

	private String queryDate;// 接口用 表示要查询的订单日期
	
	private String menuName;// 接口用 表示要查询的订单菜品名称

	private List<OrderDetail> items;// 已点列表
	
	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getUrge() {
		return urge;
	}

	public void setUrge(Integer urge) {
		this.urge = urge;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Integer getDirectSend() {
		return directSend;
	}

	public void setDirectSend(Integer directSend) {
		this.directSend = directSend;
	}

	public List<OrderDetail> getItems() {
		return items;
	}

	public void setItems(List<OrderDetail> items) {
		this.items = items;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getReserveId() {
		return reserveId;
	}

	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}