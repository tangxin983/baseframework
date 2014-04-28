package com.tx.framework.web.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.service.BaseServiceNew;
import com.tx.framework.web.modules.sys.dao.RoleMenuDao;
import com.tx.framework.web.modules.sys.dao.RoleNewDao;
import com.tx.framework.web.modules.sys.entity.Role;
import com.tx.framework.web.modules.sys.entity.RoleMenu;

@Service
@Transactional
public class RoleNewService extends BaseServiceNew<Role, String> {

	private RoleNewDao roleNewDao;

	@Autowired
	private RoleMenuDao roleMenuDao;

	@Autowired
	public void setRoleNewDao(RoleNewDao roleNewDao) {
		super.setDao(roleNewDao);
		this.roleNewDao = roleNewDao;
	}

	/**
	 * 根据角色名称查询角色
	 * 
	 * @param roleName
	 * @return
	 */
	public List<Role> findRoleByName(String roleName) {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("name", roleName);
		return dao.selectByCondition(genericType, searchParams);
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
		List<RoleMenu> roleMenus = roleMenuDao.selectByCondition(RoleMenu.class, searchParams);
		return CollectionUtils.extractToList(roleMenus, "menuId", true);
	}

	/**
	 * 保存角色
	 * 
	 * @param entity
	 */
	public void saveRole(Role entity) {
		dao.insert(entity);
		// 保存角色菜单对应关系
		if (entity.getMenuIds() != null && !entity.getMenuIds().isEmpty()) {
			for (String menuId : entity.getMenuIds()) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(entity.getId());
				roleMenu.setMenuId(menuId);
				roleMenuDao.insertWithoutId(roleMenu);
			}
		}
	}

	/**
	 * 更新角色
	 * @param entity
	 */
	public void updateRole(Role entity) {
		dao.update(entity);
		// 删除角色与菜单对应关系
		Map<String, Object> para = Maps.newHashMap();
		para.put("roleId", entity.getId());
		roleMenuDao.deleteByCondition(RoleMenu.class, para);
		// 保存角色菜单对应关系
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
