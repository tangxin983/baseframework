package com.tx.framework.web.entity;

import java.util.Date;

public class Seat extends UUIDEntity {

	private String areaId;

	private String seatName;

	private String code;

	private Integer adviseNum;

	private Integer seatKind;

	private Integer sort;

	private String memo;

	private Byte reserve;

	private Byte valid;

	private Date createTime;

	private Date updateTime;

	private String areaName;

	private String seatKindName;

	public String getSeatKindName() {
		return seatKindName;
	}

	public void setSeatKindName(String seatKindName) {
		this.seatKindName = seatKindName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getAdviseNum() {
		return adviseNum;
	}

	public void setAdviseNum(Integer adviseNum) {
		this.adviseNum = adviseNum;
	}

	public Integer getSeatKind() {
		return seatKind;
	}

	public void setSeatKind(Integer seatKind) {
		this.seatKind = seatKind;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Byte getReserve() {
		return reserve;
	}

	public void setReserve(Byte reserve) {
		this.reserve = reserve;
	}

	public Byte getValid() {
		return valid;
	}

	public void setValid(Byte valid) {
		this.valid = valid;
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