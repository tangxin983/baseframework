package com.tx.framework.web.entity;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ShiroEntity implements Serializable{

	// 当前用户
	private User user;

	// 当前用户角色
	private List<Role> roles;

	// 当前用户权限
	private List<Resource> perms;
	
	// 当前用户菜单
	private List<Resource> menus;

	public ShiroEntity() {

	}

	public ShiroEntity(User user) {
		this.user = user;
	}

	public ShiroEntity(User user, List<Role> roles, List<Resource> resources) {
		this.user = user;
		this.roles = roles;
		this.perms = resources;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Resource> getPerms() {
		return perms;
	}

	public void setPerms(List<Resource> perms) {
		this.perms = perms;
	}

	public List<Resource> getMenus() {
		return menus;
	}

	public void setMenus(List<Resource> menus) {
		this.menus = menus;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.user!=null ? this.user.getUserName() : "";
	}

}
