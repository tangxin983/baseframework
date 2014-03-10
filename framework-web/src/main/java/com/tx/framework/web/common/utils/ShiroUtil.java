package com.tx.framework.web.common.utils;

import org.apache.shiro.SecurityUtils;

import com.tx.framework.web.entity.ShiroEntity;


public class ShiroUtil {

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public static String getCurrentUserId() {
		ShiroEntity entity = (ShiroEntity) SecurityUtils.getSubject().getPrincipal();
		return entity.getUser().getId().toString();
	}
	
	/**
	 * 取出Shiro中的当前用户name.
	 */
	public static String getCurrentUserName() {
		ShiroEntity entity = (ShiroEntity) SecurityUtils.getSubject().getPrincipal();
		return entity.toString();
	}

	/**
	 * 更新Shiro中当前用户的用户名.
	 */
	public static void updateCurrentUserName(String userName) {
		ShiroEntity entity = (ShiroEntity) SecurityUtils.getSubject().getPrincipal();
		entity.getUser().setUserName(userName);
	}
	
	/**
	 * 将对象放入shiro session中
	 * @param attrName 属性名
	 * @param obj 对象
	 */
	public static void setAttribute(String attrName, Object obj) {
		SecurityUtils.getSubject().getSession().setAttribute(attrName, obj);
	}
	
	/**
	 * 取出shiro session中的对象
	 * @param attrName 属性名
	 * @return
	 */
	public static Object getAttribute(String attrName) {
		return SecurityUtils.getSubject().getSession().getAttribute(attrName);
	}
	
	/**
	 * 判断当前会话是否处于登录状态
	 * @return
	 */
	public static boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}
	
	/**
	 * 判断之前是否有会话被记住
	 * @return
	 */
	public static boolean isRemembered(){
		return SecurityUtils.getSubject().isRemembered();
	}
	
	/**
	 * 判断当前会话是否过期
	 * @return 过期返回true
	 */
	public static boolean isSessionTimeOut() {
		return SecurityUtils.getSubject().getSession(false) == null;
	}
}
