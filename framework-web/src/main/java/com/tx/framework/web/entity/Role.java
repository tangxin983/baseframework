package com.tx.framework.web.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class Role extends UUIDEntity {
	
	private String roleName;
	
	private String remark;
	
	private String state;
	
	private Long parentId;
	
	private List<User> users;
	
	private List<Resource> resources;
	
	 
	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
	public Long getParentId() {
		return parentId;
	}



	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}



	public List<User> getUsers() {
		return users;
	}



	public void setUsers(List<User> users) {
		this.users = users;
	}



	public List<Resource> getResources() {
		return resources;
	}



	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	

	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}