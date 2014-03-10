package com.tx.framework.web.entity;

import com.tx.framework.common.excel.ExcelAnnotation;

public class ImportSeat {

	@ExcelAnnotation(exportName = "区域名称")
	private String areaName;

	@ExcelAnnotation(exportName = "桌位编号")
	private String code;

	@ExcelAnnotation(exportName = "桌位名称")
	private String seatName;

	@ExcelAnnotation(exportName = "建议人数")
	private String adviseNum;

	@ExcelAnnotation(exportName = "桌位类型")
	private String seatKindName;

	private Integer seatKind;

	public Integer getSeatKind() {
		return seatKind;
	}

	public void setSeatKind(Integer seatKind) {
		this.seatKind = seatKind;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getAdviseNum() {
		return adviseNum;
	}

	public void setAdviseNum(String adviseNum) {
		this.adviseNum = adviseNum;
	}

}