package com.tx.framework.web.manage.service.shiro;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.common.util.Encodes;
import com.tx.framework.web.common.utils.ShiroUtil;
import com.tx.framework.web.entity.Resource;
import com.tx.framework.web.entity.Role;
import com.tx.framework.web.entity.ShiroEntity;
import com.tx.framework.web.entity.User;
import com.tx.framework.web.manage.service.user.UserService;


public class ShiroDbRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);

	@Autowired
	private UserService userService;
	
	private List<String> defaultPermission = Lists.newArrayList();
	
	private List<String> defaultRole = Lists.newArrayList();
	
	/**
	 * 设置默认permission
	 * 
	 * @param defaultPermissionString permission 如果存在多个值，使用逗号","使用逗号分割
	 */
	public void setDefaultPermissionString(String defaultPermissionString) {
		String[] perms = StringUtils.split(defaultPermissionString,",");
		CollectionUtils.addAll(defaultPermission, perms);
	}
	
	/**
	 * 设置默认role
	 * 
	 * @param defaultRoleString role 如果存在多个值，使用逗号","使用逗号分割
	 */
	public void setDefaultRoleString(String defaultRoleString) {
		String[] roles = StringUtils.split(defaultRoleString,",");
		CollectionUtils.addAll(defaultRole, roles);
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		if (username == null) {
			throw new AccountException("用户名不能为空");
		}
		User user = userService.findUserByLoginName(username);
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(new ShiroEntity(user),
					user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			throw new UnknownAccountException("用户不存在");
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroEntity shiroEntity = (ShiroEntity) principals
				.getPrimaryPrincipal();
		// 获取登录用户的perm、role、menu信息
		List<Resource> permList = userService.getResourcesByUserId(shiroEntity.getUser().getId().toString());
		List<Resource> menuList = userService.getMenuList(permList);
		List<Role> roleList = shiroEntity.getUser().getRoles();
		shiroEntity.setPerms(permList);
		shiroEntity.setRoles(roleList);
		shiroEntity.setMenus(menuList);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 添加用户拥有的permission
		addPermissions(info, permList);
		// 添加用户拥有的role
		addRoles(info, roleList);
		// 将shiroEntity放入session
		ShiroUtil.setAttribute("shiroEntity", shiroEntity);
		return info;
	}

	private void addPermissions(SimpleAuthorizationInfo info,
			List<Resource> authorizationInfo) {
		// 解析当前用户资源中的permissions
		List<String> permissions = CollectionUtils.extractToList(authorizationInfo, "permission", true);
		//添加默认的permissions到permissions
        if (CollectionUtils.isNotEmpty(defaultPermission)) {
        	CollectionUtils.addAll(permissions, defaultPermission.iterator());
        }
		// 将当前用户拥有的permissions设置到SimpleAuthorizationInfo中
		if (permissions != null && !permissions.isEmpty()) {
			logger.debug("perms:{}", permissions.toString());
			info.addStringPermissions(permissions);
		}
	}

	private void addRoles(SimpleAuthorizationInfo info, List<Role> roleList) {
		// 解析当前用户组中的role
		List<String> roles = CollectionUtils.extractToList(roleList, "roleName", true);
		//添加默认的roles到roels
        if (CollectionUtils.isNotEmpty(defaultRole)) {
        	CollectionUtils.addAll(roles, defaultRole.iterator());
        }
		// 将当前用户拥有的roles设置到SimpleAuthorizationInfo中
		if (roles != null && !roles.isEmpty()) {
			logger.debug("roles:{}", roles.toString());
			info.addRoles(roles);
		}
	}
}
