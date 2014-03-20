package com.tx.framework.web.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.tx.framework.web.common.persistence.entity.BaseEntity;
import com.tx.framework.web.common.persistence.entity.Page;

/**
 * 提供基本增删改查方法的基础接口<br>
 * 使用SqlProvider生成动态SQL，查询方法必须以select开头
 * 
 * @author tangx
 *
 * @param <T>
 */
public interface BaseDaoNew<T, PK> {
	
	public static final String CLASS_KEY = "clazz";
	
	public static final String ID_KEY = "id";
	
	public static final String PARA_KEY = "para";
	
	public static final String PAGE_KEY = "page";
	
	/**
	 * 查找所有记录
	 * @param clazz 实体class
	 * @return
	 */
	@SelectProvider(type = CrudProvider.class, method = "select")
	public List<T> select(Class<T> clazz);  
	
	
	/**
	 * 根据条件查找记录
	 * @param clazz 实体class
	 * @param paramMap 参数Map(如果传null则忽略条件)
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
	 * 分页查找记录
	 * @param clazz 实体class
	 * @param page 分页对象
	 * @return
	 */
	@SelectProvider(type = CrudProvider.class, method = "selectByPage")
	public List<T> selectByPage(@Param(CLASS_KEY)Class<T> clazz, @Param(PAGE_KEY)Page<T> page);  
	
	/**
	 * 根据条件分页查找记录
	 * @param clazz 实体class
	 * @param page 分页对象
	 * @param paramMap 参数Map(如果传null则忽略条件)
	 * @return
	 */
	@SelectProvider(type = CrudProvider.class, method = "selectByCondition")
	public List<T> selectByPageAndCondition(@Param(CLASS_KEY)Class<T> clazz, @Param(PAGE_KEY)Page<T> page, @Param(PARA_KEY)Map<String, Object> paramMap);  
	
	/**
	 * 获取表记录个数
	 * @param clazz 实体class
	 * @return
	 */
	@SelectProvider(type = CrudProvider.class, method = "count")
	public long count(Class<T> clazz); 
	
	/**
	 * 根据条件获取表记录个数
	 * @param clazz 实体class
	 * @param paramMap 参数Map(如果传null则忽略条件)
	 * @return
	 */
	@SelectProvider(type = CrudProvider.class, method = "countByCondition")
	public long countByCondition(@Param(CLASS_KEY)Class<T> clazz, @Param(PARA_KEY)Map<String, Object> paramMap);  
	
	/**
	 * 更新(忽略空字段)
	 * @param t 实体对象
	 */
	@UpdateProvider(type = CrudProvider.class, method = "update")
	public void update(T t);

	/**
	 * 有主键表插入(忽略空字段)<br>
	 * 主键生成方式：uuid<br>
	 * keyProperty是硬编码，如果实体有定义@Id而不使用公用id字段的情况下会有问题
	 * 
	 * @param t 实体对象
	 */
	@InsertProvider(type = CrudProvider.class, method = "insert")
	@SelectKey(statement = "select REPLACE(UUID(),'-','')", keyProperty = BaseEntity.ID_FIELD_NAME, before = true, resultType = String.class)
	public void insert(T t);
	
	/**
	 * 有主键表插入(忽略空字段)<br>
	 * 主键生成方式：自动递增<br>
	 * keyProperty是硬编码，如果实体有定义@Id而不使用公用id字段的情况下会有问题
	 * 
	 * @param t 实体对象
	 */
	@InsertProvider(type = CrudProvider.class, method = "insertWithoutId")
	@Options(useGeneratedKeys = true, keyProperty = BaseEntity.ID_FIELD_NAME)  
	public void insertByAutoIncId(T t);
	
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
