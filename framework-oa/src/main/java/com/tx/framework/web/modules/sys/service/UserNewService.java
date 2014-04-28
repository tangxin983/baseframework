package com.tx.framework.web.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.service.BaseServiceNew;
import com.tx.framework.web.common.utils.DigestUtil;
import com.tx.framework.web.modules.sys.dao.UserNewDao;
import com.tx.framework.web.modules.sys.dao.UserRoleDao;
import com.tx.framework.web.modules.sys.entity.User;
import com.tx.framework.web.modules.sys.entity.UserRole;

@Service
@Transactional
public class UserNewService extends BaseServiceNew<User, String> {

	@Autowired
	private UserRoleDao userRoleDao;

	private UserNewDao userNewDao;

	@Autowired
	public void setUserNewDao(UserNewDao userNewDao) {
		super.setDao(userNewDao);
		this.userNewDao = userNewDao;
	}
	
	/**
	 * 根据登录名查找用户
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName) {
		Map<String, Object> para = Maps.newHashMap();
		para.put("loginName", loginName);
		List<User> users = userNewDao.selectByCondition(genericType, para);
		if(users != null && !users.isEmpty()){
			return users.get(0);
		}
		return null;
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void saveUser(User user) {
		user.setSalt(DigestUtil.generateSalt());
		user.setPassword(DigestUtil.generateHash(user.getPlainPassword(),
				user.getSalt()));
		userNewDao.insert(user);
		// 保存用户角色对应关系
		if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
			for (String roleId : user.getRoleIds()) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				userRoleDao.insertWithoutId(userRole);
			}
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	// @CacheEvict(value = ShiroAuthorizingRealm.CACHE_NAME, allEntries = true)
	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			user.setSalt(DigestUtil.generateSalt());
			user.setPassword(DigestUtil.generateHash(user.getPlainPassword(),
					user.getSalt()));
		}
		userNewDao.update(user);
		// 删除用户角色对应关系
		Map<String, Object> para = Maps.newHashMap();
		para.put("userId", user.getId());
		userRoleDao.deleteByCondition(UserRole.class, para);
		// 保存用户角色对应关系
		if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
			for (String roleId : user.getRoleIds()) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				userRoleDao.insertWithoutId(userRole);
			}
		}
	}
	
	/**
	 * 查询用户对应的角色列表
	 * 
	 * @param id 用户id
	 * @return
	 */
	public List<String> findUserRole(String id) {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("userId", id);
		List<UserRole> userRoles = userRoleDao.selectByCondition(UserRole.class, searchParams);
		return CollectionUtils.extractToList(userRoles, "roleId", true);
	}

}
