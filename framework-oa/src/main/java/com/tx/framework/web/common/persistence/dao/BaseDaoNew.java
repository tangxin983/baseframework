package com.tx.framework.web.common.persistence.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.tx.framework.web.common.persistence.entity.MybatisEntity;

/**
 * 提供基本增删改查方法的基础接口
 * @author tangx
 *
 * @param <T>
 */
public interface BaseDaoNew<T extends MybatisEntity> extends BaseDao {
	
	/**
	 * 查找所有记录
	 * @param t 实体对象
	 * @return 实体列表
	 */
	@SelectProvider(type = CrudProvider.class, method = "select")
	public List<T> select(Class<?> clazz);  
	
	/**
	 * 更新(忽略空字段)
	 * @param t 实体对象
	 */
	@UpdateProvider(type = CrudProvider.class, method = "update")
	public void update(T t);

	/**
	 * 插入(忽略空字段)<br>
	 * keyProperty是硬编码，如果实体有定义@Id而不是使用MybatisEntity通用id字段的情况下会有问题
	 * 
	 * @param t 实体对象
	 */
	@InsertProvider(type = CrudProvider.class, method = "insert")
	@SelectKey(statement = "select REPLACE(UUID(),'-','')", keyProperty = MybatisEntity.nameId, before = true, resultType = String.class)
	public void insert(T t);
	
	/**
	 * 根据主键删除记录
	 * @param t 实体对象
	 */
	@DeleteProvider(type = CrudProvider.class, method = "delete")
	public void delete(T t);


}
