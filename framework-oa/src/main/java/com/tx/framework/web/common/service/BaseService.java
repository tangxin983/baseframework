package com.tx.framework.web.common.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;

import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Page;



/**
 * 提供基础的crud等服务
 * @author tangx
 *
 * @param <T>
 * @param <PK>
 */
public abstract class BaseService<T, PK> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected BaseDao<T, PK> dao; 
	
	protected final Class<T> genericType;
	
	public void setDao(BaseDao<T, PK> dao) {  
        this.dao = dao;  
    }  
	
	@SuppressWarnings("unchecked")
	public BaseService(){
		// 使用spring工具类运行时获取泛型class
		Class<?>[] clazzs = GenericTypeResolver.resolveTypeArguments(getClass(), BaseService.class);
		this.genericType = (Class<T>)clazzs[0];
	}
	 
	/**
	 * 根据主键获取记录
	 * @param id 主键
	 * @return
	 */
	public T selectById(PK id) {
		return dao.selectById(genericType, id);
	}
	
	/**
	 * 根据查询参数获取记录集(模糊查询)
	 * @param searchParams
	 * @return
	 */
	public List<T> selectByLikeCondition(Map<String, Object> searchParams) {
		return dao.selectByLikeCondition(genericType, searchParams);
	}
	
	/**
	 * 获取所有记录集
	 * @return
	 */
	public List<T> select() {
		return dao.select(genericType);
	}
	
	/**
	 * 获取所有记录集并排序（升序排列）
	 * @param orders 要排序的字段列表
	 * @return
	 */
	public List<T> select(List<String> orders) {
		return dao.selectByOrder(genericType, orders);
	}
	
	/**
	 * 根据查询参数获取记录集(精确查询)
	 * @param searchParams
	 * @return
	 */
	public List<T> select(Map<String, Object> searchParams) {
		return dao.selectByCondition(genericType, searchParams);
	}
	
	/**
	 * 根据查询参数获取记录集并排序(精确查询)
	 * @param searchParams
	 * @param orders 要排序的字段列表
	 * @return
	 */
	public List<T> select(Map<String, Object> searchParams, List<String> orders) {
		return dao.selectByConditionAndOrder(genericType, searchParams, orders);
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
	public void deleteById(PK id) {
		dao.deleteById(genericType, id);
	}
	
	/**
	 * 根据主键批量删除记录
	 * @param ids
	 */
	public void deleteByIds(List<PK> ids) {
		for(PK id : ids){
			dao.deleteById(genericType, id);
		}
	}
	
	/**
	 * 根据查询参数进行分页查询(模糊查询)
	 * @param searchParams 查询参数
	 * @param pageNumber 当前页
	 * @param pageSize 每页数量
	 * @return
	 */
	public Page<T> selectByPage(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		Page<T> p = buildPage(pageNumber, pageSize);
		p.setResult(dao.selectByPageAndLikeCondition(genericType, p, searchParams));
		return p;
	}
	
	/**
	 * 分页查询
	 * @param searchParams 查询参数
	 * @param pageNumber 当前页
	 * @param pageSize 每页数量
	 * @return
	 */
	public Page<T> selectByPage(int pageNumber, int pageSize) {
		Page<T> p = buildPage(pageNumber, pageSize);
		p.setResult(dao.selectByPage(genericType, p));
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
}
