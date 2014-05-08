package com.tx.framework.web.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.persistence.entity.Role;
import com.tx.framework.web.common.persistence.entity.RoleMenu;
import com.tx.framework.web.common.persistence.entity.UserRole;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.modules.sys.dao.RoleMenuDao;
import com.tx.framework.web.modules.sys.dao.RoleDao;
import com.tx.framework.web.modules.sys.dao.UserRoleDao;
import com.tx.framework.web.modules.sys.security.ShiroAuthorizingRealm;

@Service
@Transactional
public class RoleService extends BaseService<Role, String> {

	private RoleDao roleDao;

	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		super.setDao(roleDao);
		this.roleDao = roleDao;
	}

	/**
	 * 根据角色名称查询记录条数
	 * 
	 * @param roleName
	 * @return
	 */
	public long countRoleByName(String roleName) {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("name", roleName);
		return dao.countByCondition(genericType, searchParams);
	}

	/**
	 * 查询角色对应的菜单id列表
	 * 
	 * @param roleName
	 * @return
	 */
	public List<String> findRoleMenu(String id) {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("roleId", id);
		List<RoleMenu> roleMenus = roleMenuDao.selectByCondition(
				RoleMenu.class, searchParams);
		return CollectionUtils.extractToList(roleMenus, "menuId", true);
	}

	/**
	 * 保存角色（涉及到权限变更将清除权限缓存以便重新加载）
	 * 
	 * @param entity
	 */
	@CacheEvict(value = ShiroAuthorizingRealm.CACHE_NAME, allEntries = true)
	public void saveRole(Role entity) {
		dao.insert(entity);
		saveRoleMenu(entity);
	}

	/**
	 * 更新角色（涉及到权限变更将清除权限缓存以便重新加载）
	 * 
	 * @param entity
	 */
	@CacheEvict(value = ShiroAuthorizingRealm.CACHE_NAME, allEntries = true)
	public void updateRole(Role entity) {
		dao.update(entity);
		saveRoleMenu(entity);
	}

	/**
	 * 删除角色（涉及到权限变更将清除权限缓存以便重新加载）
	 * 
	 * @param id
	 */
	@CacheEvict(value = ShiroAuthorizingRealm.CACHE_NAME, allEntries = true)
	public void deleteRole(String id) {
		dao.deleteById(genericType, id);
		// 删除角色与菜单对应关系
		Map<String, Object> para = Maps.newHashMap();
		para.put("roleId", id);
		roleMenuDao.deleteByCondition(RoleMenu.class, para);
	}
	
	/**
	 * 批量删除角色（涉及到权限变更将清除权限缓存以便重新加载）
	 * 
	 * @param ids
	 */
	@CacheEvict(value = ShiroAuthorizingRealm.CACHE_NAME, allEntries = true)
	public void deleteRole(List<String> ids) {
		for(String id : ids){
			deleteRole(id);
		}
	}
	

	/**
	 * 判断是否能够删除角色（已关联用户则不能删除）
	 * @param id
	 * @return 可以删除true不能删除false
	 */
	public boolean isDelete(String id) {
		Map<String, Object> para = Maps.newHashMap();
		para.put("roleId", id);
		if(userRoleDao.countByCondition(UserRole.class, para) > 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否能够删除角色（已关联用户则不能删除）
	 * @param ids
	 * @return 可以删除true不能删除false
	 */
	public boolean isDelete(List<String> ids) {
		for(String id : ids){
			if(!isDelete(id)){
				return false;
			}
		}
		return true;
	}

	/**
	 * 保存角色菜单关系
	 * 
	 * @param entity
	 */
	private void saveRoleMenu(Role entity) {
		// 删除旧的角色与菜单对应关系
		Map<String, Object> para = Maps.newHashMap();
		para.put("roleId", entity.getId());
		roleMenuDao.deleteByCondition(RoleMenu.class, para);
		// 保存新的角色菜单对应关系
		if (entity.getMenuIds() != null && !entity.getMenuIds().isEmpty()) {
			for (String menuId : entity.getMenuIds()) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(entity.getId());
				roleMenu.setMenuId(menuId);
				roleMenuDao.insertWithoutId(roleMenu);
			}
		}
	}
}
