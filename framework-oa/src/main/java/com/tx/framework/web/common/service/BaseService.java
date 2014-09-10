package com.tx.framework.web.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;

import com.google.common.collect.Table;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.BaseEntity;
import com.tx.framework.web.common.persistence.entity.Page;



/**
 * 提供基础的crud等服务
 * @author tangx
 *
 * @param <T>
 * @param <PK>
 */
public abstract class BaseService<T extends BaseEntity> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected BaseDao<T> dao; 
	
	protected final Class<T> genericType;
	
	public void setDao(BaseDao<T> dao) {  
        this.dao = dao;  
    }  
	
	@SuppressWarnings("unchecked")
	public BaseService(){
		// 使用spring工具类运行时获取泛型class
		Class<?>[] clazzs = GenericTypeResolver.resolveTypeArguments(getClass(), BaseService.class);
		this.genericType = (Class<T>)clazzs[0];
	}
	
	/**
	 * 获取所有记录
	 * @return
	 */
	public List<T> select() {
		return dao.select(genericType);
	}
	 
	/**
	 * 根据主键获取记录
	 * @param id 主键
	 * @return
	 */
	public T selectById(Serializable id) {
		return dao.selectById(genericType, id);
	}
	
	/**
	 * 获取所有记录并排序
	 * @param orders 要排序的字段列表
	 * @return
	 */
	public List<T> selectByOrder(Map<String, String> orders) {
		return dao.selectByOrder(genericType, orders);
	}
	
	/**
	 * 精确查询
	 * @param searchParams
	 * @return
	 */
	public List<T> select(Map<String, Object> searchParams) {
		return dao.selectByCondition(genericType, null, searchParams, null);
	}
	
	/**
	 * 精确查询并排序
	 * @param searchParams
	 * @param orders 
	 * @return
	 */
	public List<T> select(Map<String, Object> searchParams, Map<String, String> orders) {
		return dao.selectByCondition(genericType, null, searchParams, orders);
	}
	
	/**
	 * 复杂查询
	 * @param searchTable
	 * @return
	 */
	public List<T> select(Table<String, String, Object> searchTable) {
		return dao.selectByComplicated(genericType, null, searchTable, null);
	}
	
	/**
	 * 复杂查询并排序
	 * @param searchTable
	 * @param orders
	 * @return
	 */
	public List<T> select(Table<String, String, Object> searchTable, Map<String, String> orders) {
		return dao.selectByComplicated(genericType, null, searchTable, orders);
	}
	
	/**
	 * 分页查询
	 * @param paraMap 精确查询参数
	 * @param orders 排序参数
	 * @param pageNumber 页码
	 * @param pageSize 每页数量
	 * @return
	 */
	public Page<T> selectByPage(Map<String, Object> paraMap, Map<String, String> orders, int pageNumber, int pageSize) {
		Page<T> p = buildPage(pageNumber, pageSize);
		p.setResult(dao.selectByCondition(genericType, p, paraMap, orders));
		return p;
	}
	
	/**
	 * 分页查询
	 * @param searchTable 复杂查询参数
	 * @param orders 排序参数
	 * @param pageNumber 页码
	 * @param pageSize 每页数量
	 * @return
	 */
	public Page<T> selectByPage(Table<String, String, Object> searchTable, Map<String, String> orders, int pageNumber, int pageSize) {
		Page<T> p = buildPage(pageNumber, pageSize);
		p.setResult(dao.selectByComplicated(genericType, p, searchTable, orders));
		return p;
	}
	
	/**
	 * 构建分页对象
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	protected Page<T> buildPage(int pageNumber, int pageSize) {
		Page<T> page = new Page<T>();
		page.setCurrentPage(pageNumber);
		page.setSize(pageSize);
		return page;
	}
	
	/**
	 * 获取表记录数
	 * @return
	 */
	public Long count() {
		return dao.count(genericType);
	}
	
	/**
	 * 根据查询参数获取表记录数(精确查询)
	 * @param searchParams
	 * @return
	 */
	public Long count(Map<String, Object> searchParams) {
		return dao.countByCondition(genericType, searchParams);
	}
	
	/**
	 * 插入记录
	 * @param entity
	 */
	public void insert(T entity) {
		dao.insert(entity);
	}
	
	/**
	 * 更新记录
	 * @param entity
	 */
	public void update(T entity) {
		dao.update(entity);
	}

	/**
	 * 根据主键删除记录
	 * @param id
	 */
	public void deleteById(Serializable id) {
		dao.deleteById(genericType, id);
	}
	
	/**
	 * 根据主键批量删除记录
	 * @param ids
	 */
	public void deleteByIds(List<? extends Serializable> ids) {
		for(Serializable id : ids){
			dao.deleteById(genericType, id);
		}
	}
	
}
