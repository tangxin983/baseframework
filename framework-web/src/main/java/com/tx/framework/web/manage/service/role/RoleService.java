package com.tx.framework.web.manage.service.role;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tx.framework.web.common.base.BaseService;
import com.tx.framework.web.dao.role.RoleDao;
import com.tx.framework.web.entity.Role;


@Service
@Transactional
public class RoleService extends BaseService<Role>{

	private RoleDao roleDao;

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		super.setDao(roleDao);
		this.roleDao = roleDao;
	}
 
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	public void saveRole(Role role, List<String> resourceIds) {
		roleDao.save(role);
		//保存角色资源对应关系
		if(resourceIds != null && !resourceIds.isEmpty()){
			Map<String, Object> roleResourceMap = Maps.newHashMap();
			for(String resourceId : resourceIds){
				roleResourceMap.put("resourceId", resourceId);
				roleResourceMap.put("roleId", role.getId());
				roleDao.saveRoleResource(roleResourceMap);
			}
		}
	}
	
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	public void updateRole(Role role, List<String> resourceIds) {
		roleDao.update(role);
		//删除角色对应资源
		roleDao.deleteRoleResource(role.getId().toString());
		//保存角色资源对应关系
		if(resourceIds != null && !resourceIds.isEmpty()){
			Map<String, Object> roleResourceMap = Maps.newHashMap();
			for(String resourceId : resourceIds){
				roleResourceMap.put("resourceId", resourceId);
				roleResourceMap.put("roleId", role.getId());
				roleDao.saveRoleResource(roleResourceMap);
			}
		}
	}
	
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	public void deleteRole(String id) {
		roleDao.delete(id);
		roleDao.deleteRoleResource(id);
		roleDao.deleteRoleUser(id);
	}
	
	@CacheEvict(value = "shiroAuthorizationCache", allEntries = true)
	public void deleteRoles(List<String> ids) {
		for(String id : ids){
			roleDao.delete(id);
			roleDao.deleteRoleResource(id);
			roleDao.deleteRoleUser(id);
		}
	}
	
	/**
	 * 如果角色已关联用户则不能删除
	 * @param ids
	 * @return
	 */
	public boolean isDeleteRole(List<String> ids) {
		for(String id : ids){
			if(roleDao.findUserCount(id) > 0){
				return false;
			}
		}
		return true;
	}
 
}
