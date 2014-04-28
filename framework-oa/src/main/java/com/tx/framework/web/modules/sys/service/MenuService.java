package com.tx.framework.web.modules.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.service.BaseServiceNew;
import com.tx.framework.web.modules.sys.dao.MenuDao;
import com.tx.framework.web.modules.sys.dao.RoleMenuDao;
import com.tx.framework.web.modules.sys.entity.Menu;
import com.tx.framework.web.modules.sys.entity.RoleMenu;
import com.tx.framework.web.modules.sys.security.ShiroAuthorizingRealm;

@Service
@Transactional
public class MenuService extends BaseServiceNew<Menu, String> {
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	private ShiroAuthorizingRealm shiroAuthorizingRealm;

	private MenuDao menuDao;

	@Autowired
	public void setMenuDao(MenuDao menuDao) {
		super.setDao(menuDao);
		this.menuDao = menuDao;
	}
	
	/**
	 * 根据用户id获取资源（包括权限和菜单）
	 * @param id
	 * @return
	 */
	public List<Menu> getAuthByUserId(String id) {
		return menuDao.findByUserId(id);
	}
	
	/**
	 * 获取侧边栏菜单
	 * 
	 * @param list 用户资源集合
	 */
	public List<Menu> getSidebarMenus(List<Menu> list) {
		List<Menu> result = Lists.newArrayList();
		for (Menu menu : list) {
			// 选出一级菜单
			if (menu.getParentId().equals("1") && menu.getIsShow().equals("1")) {
				getSubMenus(list, menu);
				result.add(menu);
			}
		}
		return result;
	}
	
	/**
	 * 获取shiro权限列表
	 * 
	 * @param list 用户资源集合
	 */
	public List<Menu> getPerms(List<Menu> list) {
		List<Menu> result = Lists.newArrayList();
		for (Menu menu : list) {
			if (StringUtils.isNotBlank(menu.getPermission())) {
				result.add(menu);
			}
		}
		return result;
	}
	
	/**
	 * 递归获取次级菜单
	 * 
	 * @param list 用户权限集合
	 */
	private void getSubMenus(List<Menu> list, Menu parent) {
		parent.setChildren(new ArrayList<Menu>());
		for (Menu menu : list) {
			if (menu.getIsShow().equals("1") && menu.getParentId().equals(parent.getId())) {
				getSubMenus(list, menu);
				parent.getChildren().add(menu);
			}
		}
	}

	/**
	 * 按照排序获取菜单列表
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<Menu> findAllMenuBySort(Map<String, Object> searchParams) {
		List<String> orders = Lists.newArrayList();
		orders.add("sort");
		return select(searchParams, orders);
	}

	/**
	 * 删除菜单及其子菜单
	 * 
	 * @param id 要删除的菜单id
	 */
	public void deleteMenu(String id) {
		List<Menu> childs = findChildsByPid(id);
		List<String> ids = CollectionUtils.extractToList(childs, "id", true);
		ids.add(id);
		deleteByIds(ids);
		// 删除关联的RoleMenu记录
		for(String menuId : ids){
			Map<String, Object> para = Maps.newHashMap();
			para.put("menuId", menuId);
			roleMenuDao.deleteByCondition(RoleMenu.class, para);
		}
		shiroAuthorizingRealm.clearCacheAndReloadFilterChain();
	}

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 */
	public void saveMenu(Menu menu) {
		Menu parent = selectById(menu.getParentId());// 获取父菜单
		menu.setParentIds(parent.getParentIds() + parent.getId() + ",");
		dao.insert(menu);
		shiroAuthorizingRealm.clearCacheAndReloadFilterChain();
	}

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 */
	public void updateMenu(Menu menu) {
		String oldParentIds = menu.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		// 更新parentIds
		Menu parent = selectById(menu.getParentId());
		menu.setParentIds(parent.getParentIds() + parent.getId() + ",");
		dao.update(menu);
		// 更新子节点的parentIds
		if(StringUtils.isNotBlank(oldParentIds)){
			List<Menu> childs = findChildsByPid(menu.getId());
			for (Menu e : childs) {
				e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
				dao.update(e);
			}
		}
		shiroAuthorizingRealm.clearCacheAndReloadFilterChain();
	}
	
	/**
	 * 根据父节点id查找所有子节点
	 * @param id
	 * @return
	 */
	public List<Menu> findChildsByPid(String id){
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("parentIds", "," + id + ",");
		return selectByLikeCondition(searchParams);
	}

}
