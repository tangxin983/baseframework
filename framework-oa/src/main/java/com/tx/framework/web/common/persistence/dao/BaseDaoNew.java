package com.tx.framework.web.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.tx.framework.web.common.persistence.entity.MybatisEntity;

/**
 * 提供基本增删改查方法的基础接口<br>
 * 使用SqlProvider生成动态SQL，查询方法必须以select开头
 * 
 * @author tangx
 *
 * @param <T>
 */
public interface BaseDaoNew<T extends MybatisEntity, PK> extends BaseDao {
	
	public static final String CLASS_KEY = "clazz";
	
	public static final String ID_KEY = "id";
	
	public static final String PARA_KEY = "para";
	
	/**
	 * 查找所有记录
	 * @param clazz 实体class
	 * @return
	 */
	@SelectProvider(type = CrudProvider.class, method = "selectAll")
	public List<T> selectAll(Class<T> clazz);  
	
	/**
	 * 根据条件查找记录
	 * @param clazz 实体class
	 * @param param 参数Map
	 * @return
	 */
	@SelectProvider(type = CrudProvider.class, method = "selectByCondition")
	public List<T> selectByCondition(@Param(CLASS_KEY)Class<T> clazz, @Param(PARA_KEY)Map<String, Object> paramMap);  
	
	/**
	 * 根据主键查找记录
	 * @param clazz 实体class
	 * @param id 主键
	 * @return
	 */
	@SelectProvider(type = CrudProvider.class, method = "selectById")
	public T selectById(@Param(CLASS_KEY)Class<T> clazz, @Param(ID_KEY)PK id);  
	
	/**
	 * 更新(忽略空字段)
	 * @param t 实体对象
	 */
	@UpdateProvider(type = CrudProvider.class, method = "update")
	public void update(T t);

	/**
	 * 有主键表插入(忽略空字段)<br>
	 * keyProperty是硬编码，如果实体有定义@Id而不是使用MybatisEntity通用id字段的情况下会有问题
	 * 
	 * @param t 实体对象
	 */
	@InsertProvider(type = CrudProvider.class, method = "insert")
	@SelectKey(statement = "select REPLACE(UUID(),'-','')", keyProperty = MybatisEntity.nameId, before = true, resultType = String.class)
	public void insert(T t);
	
	/**
	 * 无主键表插入(忽略空字段)<br>
	 * 
	 * @param t 实体对象
	 */
	@InsertProvider(type = CrudProvider.class, method = "insertWithoutId")
	public void insertWithoutId(T t);
	
	/**
	 * 根据主键删除记录
	 * @param clazz 实体class
	 * @param id 主键
	 * @return
	 */
	@DeleteProvider(type = CrudProvider.class, method = "deleteById")
	public void deleteById(@Param(CLASS_KEY)Class<T> clazz, @Param(ID_KEY)PK id);  


}
