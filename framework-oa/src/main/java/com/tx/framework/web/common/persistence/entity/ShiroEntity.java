package com.tx.framework.web.common.persistence.entity;

import java.io.Serializable;
import java.util.List;
import com.tx.framework.web.modules.sys.entity.User;
import com.tx.framework.web.modules.sys.entity.Menu;
import com.tx.framework.web.modules.sys.entity.Role;

@SuppressWarnings("serial")
public class ShiroEntity implements Serializable {

	// 当前用户
	private User user;

	// 当前用户角色
	private List<Role> roles;

	// 当前用户权限
	private List<Menu> perms;

	// 当前用户菜单
	private List<Menu> menus;

	public ShiroEntity() {

	}

	public ShiroEntity(User user) {
		this.user = user;
	}

	public ShiroEntity(User user, List<Role> roles, List<Menu> perms) {
		this.user = user;
		this.roles = roles;
		this.perms = perms;
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

	public List<Menu> getPerms() {
		return perms;
	}

	public void setPerms(List<Menu> perms) {
		this.perms = perms;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.user != null ? this.user.getName() : "";
	}

}
