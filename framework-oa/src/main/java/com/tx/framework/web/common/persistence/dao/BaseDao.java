package com.tx.framework.web.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.common.persistence.interceptor.PageInterceptor;



/**
 * <p>
 * 把常用的方法抽象到此接口中，避免在多个接口中重复定义
 * </p>
 */

public interface BaseDao<T> {
	String PO_KEY = "po";
	
	/**
	 * 查找所有
	 * @return
	 */
	List<T> findAll(); 
	
	/**
	 * 按条件查询
	 * @param map 参数map
	 * @return
	 */
	List<T> findByParams(Map map); 
	
	/**
	 * 按条件查询
	 * @param map 参数map
	 * @return
	 */
	List<Map> findMapByParams(Map map); 
	
	/**
	 * 按条件查询记录数
	 * @param map 参数map
	 * @return
	 */
	Long findCountByParams(Map map); 

	/**
	 * 按主键查询
	 * @param id 主键
	 * @return
	 */
	T findOne(String id);
	
	/**
	 * 保存
	 * @param t 实体对象
	 */
	void save(T t);
	
	/**
	 * 更新
	 * @param t 实体对象
	 */
	void update(T t);

	/**
	 * 删除
	 * @param id 主键
	 */
	void delete(String id);

	/**
	 * 分页查询
	 * @param p page对象
	 * @param map 参数map
	 * @return
	 */
	List<T> getPage(@Param(PageInterceptor.PAGE_KEY) Page<T> p, @Param(PO_KEY) Map map);
}
