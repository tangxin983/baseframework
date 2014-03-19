package com.tx.framework.web.common.persistence.entity;

import java.io.Serializable;

/**
 * 通用Mybatis实体，其他实体需继承此类
 * 
 * @author tangx
 * 
 */
public class MybatisEntity implements Serializable {
	
	public static final String nameId = "id";

	private static final long serialVersionUID = 1L;

	protected String id;// 如果子类没定义@Id字段，用这个作为默认主键

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
