package com.tx.framework.web.modules.cms.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.modules.cms.dao.CategoryDao;
import com.tx.framework.web.modules.cms.entity.Category;

/**
 * cms栏目Service
 * @author tangx
 * @since 2014-09-10
 */
@Service
@Transactional
public class CategoryService extends BaseService<Category> {

	private CategoryDao categoryDao;

	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		super.setDao(categoryDao);
		this.categoryDao = categoryDao;
	}
	
	/**
	 * 按照排序获取栏目列表
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<Category> findCategoryBySort(Map<String, Object> searchParams) {
		Map<String, String> orders = Maps.newHashMap();
		orders.put("sort", "asc");
		return select(searchParams, orders);
	}
	
	@Override
	public void insert(Category entity) {
		Category parent = selectById(entity.getParentId());// 获取父栏目
		entity.setParentIds(parent.getParentIds() + parent.getId() + ",");
		categoryDao.insert(entity);
	}
	
	@Override
	public void update(Category entity) {
		String oldParentIds = entity.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		// 更新parentIds
		Category parent = selectById(entity.getParentId());
		entity.setParentIds(parent.getParentIds() + parent.getId() + ",");
		categoryDao.update(entity);
		// 更新子节点的parentIds
		if(StringUtils.isNotBlank(oldParentIds)){
			List<Category> childs = findChildsByPid(entity.getId());
			for (Category e : childs) {
				e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
				categoryDao.update(e);
			}
		}
	}
	
	 
	@Override
	public void delete(String id) {
		// 查找子节点的id
		List<Category> childs = findChildsByPid(id);
		List<String> ids = CollectionUtils.extractToList(childs, "id", true);
		// 将自身id加入
		ids.add(id);
		super.delete(ids);
	}
	
	/**
	 * 根据父节点id查找所有子节点
	 * @param id
	 * @return
	 */
	public List<Category> findChildsByPid(String id){
		Table<String, String, Object> table = HashBasedTable.create();
		table.put("parentIds", "like", "," + id + ",");
		return select(table);
	}
}
