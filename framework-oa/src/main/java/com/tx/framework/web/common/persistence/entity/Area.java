package com.tx.framework.web.common.persistence.entity;

import java.util.Date;
import java.sql.*;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 区域实体
 * @author tangx
 * @since 2014-05-07
 */
@SuppressWarnings("serial")
@Table(name = "sys_area")
public class Area extends BaseEntity {

	@Column(name = "name")
	private String name;
	
	@Column(name = "office_id")
	private String officeId;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
}


