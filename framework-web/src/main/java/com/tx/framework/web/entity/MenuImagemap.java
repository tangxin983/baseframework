package com.tx.framework.web.entity;

import java.util.Date;

public class MenuImagemap extends UUIDEntity {

	private String menuLayoutId;

	private String menuId;

	private Integer sort;

	private Integer width;

	private Integer height;

	private Integer topx;

	private Integer topy;

	private Date createTime;

	private Integer bottomx;

	private Integer bottomy;

	private String coord;

	private String menuName;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getCoord() {
		return coord;
	}

	public void setCoord(String coord) {
		this.coord = coord;
	}

	public String getMenuLayoutId() {
		return menuLayoutId;
	}

	public void setMenuLayoutId(String menuLayoutId) {
		this.menuLayoutId = menuLayoutId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getTopx() {
		return topx;
	}

	public void setTopx(Integer topx) {
		this.topx = topx;
	}

	public Integer getTopy() {
		return topy;
	}

	public void setTopy(Integer topy) {
		this.topy = topy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getBottomx() {
		return bottomx;
	}

	public void setBottomx(Integer bottomx) {
		this.bottomx = bottomx;
	}

	public Integer getBottomy() {
		return bottomy;
	}

	public void setBottomy(Integer bottomy) {
		this.bottomy = bottomy;
	}
}