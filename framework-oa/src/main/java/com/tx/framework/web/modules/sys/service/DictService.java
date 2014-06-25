package com.tx.framework.web.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.tx.framework.web.common.persistence.entity.Dict;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.modules.sys.dao.DictDao;

/**
 * 字典Service
 * 
 * @author tangx
 * @since 2014-06-25
 */
@Service
@Transactional
public class DictService extends BaseService<Dict, String> {

	private DictDao dictDao;

	@Autowired
	public void setDictDao(DictDao dictDao) {
		super.setDao(dictDao);
		this.dictDao = dictDao;
	}

	/**
	 * 字典类型列表
	 * 
	 * @return
	 */
	public List<String> findTypeList() {
		return dictDao.findTypeList();
	}

	/**
	 * 根据字典类型获取字典列表
	 * @param type 字典类型
	 * @return
	 */
	@Cacheable(value = "dictCache", key = "#type")
	public List<Dict> findDictListByType(String type) {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("type", type);
		return select(searchParams);
	}

	@Override
	@CacheEvict(value = "dictCache", key="#entity.type")
	public void insert(Dict entity) {
		dao.insert(entity);
	}

	@Override
	@CacheEvict(value = "dictCache", key="#entity.type")
	public void update(Dict entity) {
		dao.update(entity);
	}

	@Override
	@CacheEvict(value = "dictCache", allEntries = true)
	public void deleteById(String id) {
		super.deleteById(id);
	}

	@Override
	@CacheEvict(value = "dictCache", allEntries = true)
	public void deleteByIds(List<String> ids) {
		super.deleteByIds(ids);
	}

}
