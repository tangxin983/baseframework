package com.tx.framework.web.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.common.persistence.entity.User;
import com.tx.framework.web.common.persistence.entity.UserRole;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.common.utils.DigestUtil;
import com.tx.framework.web.common.utils.ShiroUtil;
import com.tx.framework.web.modules.sys.dao.UserDao;
import com.tx.framework.web.modules.sys.dao.UserRoleDao;
import com.tx.framework.web.modules.sys.security.ShiroAuthorizingRealm;

@Service
@Transactional
public class UserService extends BaseService<User, String> {

	@Autowired
	private UserRoleDao userRoleDao;

	private UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		super.setDao(userDao);
		this.userDao = userDao;
	}
	
	/**
	 * 列表页分页查询(覆盖BaseService)
	 */
	@Override
	public Page<User> selectByPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<User> p = buildPage(pageNumber, pageSize);
		p.setResult(userDao.findUserByPageAndLikeCondition(p, searchParams));
		return p;
	}

	/**
	 * 根据登录名查找用户
	 * 
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName) {
		return userDao.findUserByLoginName(loginName);
	}
	
	/**
	 * 根据id查找用户
	 * 
	 * @param id 用户id
	 * @return
	 */
	public User findUserById(String id) {
		User user = userDao.findUserById(id);
		if(user != null) {
			List<String> roleIds = CollectionUtils.extractToList(user.getRoles(), "id", true);
			user.setRoleIds(roleIds);
		}
		return user;
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
		userDao.insert(user);
		saveUserRole(user);
	}

	/**
	 * 更新用户（涉及到权限变更将清除权限缓存以便重新加载）
	 * 
	 * @param user
	 */
	@CacheEvict(value = ShiroAuthorizingRealm.CACHE_NAME, allEntries = true)
	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			user.setSalt(DigestUtil.generateSalt());
			user.setPassword(DigestUtil.generateHash(user.getPlainPassword(),
					user.getSalt()));
		}
		userDao.update(user);
		saveUserRole(user);
	}
	
	/**
	 * 首页更新个人信息
	 * @param user
	 */
	public void updateInfo(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			user.setSalt(DigestUtil.generateSalt());
			user.setPassword(DigestUtil.generateHash(user.getPlainPassword(),
					user.getSalt()));
		}
		userDao.update(user);
		user.setRoles(ShiroUtil.getCurrentUser().getRoles());
		ShiroUtil.updateCurrentUser(user);
	}

	/**
	 * 删除用户及用户角色关系
	 * 
	 * @param id
	 */
	public void deleteUser(String id) {
		userDao.deleteById(genericType, id);
		Map<String, Object> para = Maps.newHashMap();
		para.put("userId", id);
		userRoleDao.deleteByCondition(UserRole.class, para);
	}
	
	/**
	 * 批量删除用户及用户角色关系
	 * 
	 * @param ids
	 */
	public void deleteUser(List<String> ids) {
		for(String id : ids){
			deleteUser(id);
		}
	}
	
	/**
	 * 保存用户角色对应关系
	 * 
	 * @param user
	 */
	private void saveUserRole(User user) {
		// 删除旧的用户角色对应关系
		Map<String, Object> para = Maps.newHashMap();
		para.put("userId", user.getId());
		userRoleDao.deleteByCondition(UserRole.class, para);
		// 保存新的用户角色对应关系
		if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
			for (String roleId : user.getRoleIds()) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getId());
				userRoleDao.insertWithoutId(userRole);
			}
		}
	}

}