package com.tx.framework.web.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 统一定义id的entity基类.
 */

@SuppressWarnings("serial")
public abstract class UUIDEntity implements Serializable{

	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
