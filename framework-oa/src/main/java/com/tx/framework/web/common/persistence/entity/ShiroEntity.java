package com.tx.framework.web.common.persistence.entity;

import java.io.Serializable;
import java.util.List;

import com.tx.framework.web.modules.sys.entity.Menu;
import com.tx.framework.web.modules.sys.entity.Role;
import com.tx.framework.web.modules.sys.entity.User;

@SuppressWarnings("serial")
public class ShiroEntity implements Serializable {

	// 当前用户
	private User user;

	// 当前用户角色
	private List<Role> roles;

	// 当前用户权限
	private List<Menu> perms;

	// 侧边栏菜单
	private List<Menu> menus;

	// 可点击的菜单
	private List<Menu> navs;

	// 当前用户总资源
	private List<Menu> resources;

	public ShiroEntity(User user) {
		this.user = user;
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

	public List<Menu> getNavs() {
		return navs;
	}

	public void setNavs(List<Menu> navs) {
		this.navs = navs;
	}

	public List<Menu> getResources() {
		return resources;
	}

	public void setResources(List<Menu> resources) {
		this.resources = resources;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.user != null ? this.user.getName() : "";
	}

}
