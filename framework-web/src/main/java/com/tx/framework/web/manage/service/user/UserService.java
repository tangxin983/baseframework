package com.tx.framework.web.manage.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.base.BaseService;
import com.tx.framework.web.common.page.Page;
import com.tx.framework.web.common.utils.DigestUtil;
import com.tx.framework.web.dao.account.UserDao;
import com.tx.framework.web.dao.resource.ResourceDao;
import com.tx.framework.web.dao.role.RoleDao;
import com.tx.framework.web.entity.Resource;
import com.tx.framework.web.entity.Role;
import com.tx.framework.web.entity.User;


/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Service
@Transactional
public class UserService extends BaseService<User>{

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		super.setDao(userDao);
		this.userDao = userDao;
	}
 
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleDao roleDao;
	
	
	private DateProvider dateProvider = DateProvider.DEFAULT;
	

	public List<Resource> getResourcesByUserId(String id) {
		return resourceDao.findByUserId(id);
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public Page<User> getEntityByPage(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		Page<User> p = buildPage(pageNumber, pageSize);
		List<User> users = dao.getPage(p, searchParams);
		//设置角色名
		if(users != null && !users.isEmpty()){
			for(User user : users){
				List<String> roleNames = CollectionUtils.extractToList(user.getRoles(), "remark", true);
				user.setRoleNames(StringUtils.join(roleNames, ","));
			}
		}
		p.setResult(users);
		return p;
	}
	
	/**
	 * 查询某个用户
	 * @param id
	 * @return
	 */
	@Override
	public User getEntity(String id) {
		User user = dao.findOne(id);
		List<String> roleNames = CollectionUtils.extractToList(user.getRoles(), "remark", true);
		List<String> roleIds = CollectionUtils.extractToList(user.getRoles(), "id", true);
		user.setRoleNames(StringUtils.join(roleNames, ","));
		user.setRoleIds(StringUtils.join(roleIds, ","));
		return user;
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @param roleIds
	 */
	public void saveUser(User user, List<String> roleIds) {
		user.setSalt(DigestUtil.generateSalt());
		user.setPassword(DigestUtil.generateHashBySha1(user.getPlainPassword(), user.getSalt()));
		user.setRegisterDate(dateProvider.getDate());
		userDao.save(user);
		//保存用户角色对应关系
		if(roleIds != null && !roleIds.isEmpty()){
			Map<String, Object> roleUserMap = Maps.newHashMap();
			for(String roleId : roleIds){
				roleUserMap.put("userId", user.getId());
				roleUserMap.put("roleId", roleId);
				roleDao.saveRoleUser(roleUserMap);
			}
		}
	}
 
	/**
	 * 更新用户
	 * @param user
	 * @param roleIds
	 */
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	public void updateUser(User user, List<String> roleIds) {
		if (StringUtils.isNotBlank(user.getUpdatePlainPassword())) {
			user.setSalt(DigestUtil.generateSalt());
			user.setPassword(DigestUtil.generateHashBySha1(user.getUpdatePlainPassword(), user.getSalt()));
		}
		userDao.update(user);
		roleDao.deleteUserRole(user.getId());
		//保存用户角色对应关系
		if(roleIds != null && !roleIds.isEmpty()){
			Map<String, Object> roleUserMap = Maps.newHashMap();
			for(String roleId : roleIds){
				roleUserMap.put("userId", user.getId());
				roleUserMap.put("roleId", roleId);
				roleDao.saveRoleUser(roleUserMap);
			}
		}
	}

	/**
	 * 删除用户及用户角色关系
	 * @param id
	 */
	public void deleteUser(String id) {
		userDao.delete(id);
		roleDao.deleteUserRole(id);
	}
	
	/**
	 * 批量删除用户及用户角色关系
	 * @param ids
	 */
	public void MultiDeleteUser(List<String> ids) {
		for(String id : ids){
			userDao.delete(id);
			roleDao.deleteUserRole(id);
		}
	}
	
	public List<Role> getAllRoles() {
		return roleDao.findAll();
	}
	
	/**
	 * 获取菜单列表
	 * 
	 * @param list 资源集合
	 */
	public List<Resource> getMenuList(List<Resource> list) {
		List<Resource> result = new ArrayList<Resource>();
		
		for (Resource r : list) {
			if (StringUtils.isBlank(r.getParentId()) && StringUtils.equals("1",r.getType())) {
				mergeResourcesToParent(list,r);
				result.add(r);
			}
		}
		
		return result;
	}
	
	/**
	 * 遍历资源列表的数据,如果父资源ID与parent相等，将资源加入parent的children中
	 * 
	 * @param list 资源集合
	 */
	private void mergeResourcesToParent(List<Resource> list, Resource parent) {
		parent.setChildren(new ArrayList<Resource>());
		for (Resource r: list) {
			if (StringUtils.equals(r.getType(), "1") && StringUtils.equals(r.getParentId(),parent.getId())) {
				mergeResourcesToParent(list,r);
				parent.getChildren().add(r);
			}
		}
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(String id) {
		return id.equals("1");
	}
 
}
