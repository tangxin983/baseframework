package com.tx.framework.web.common.persistence.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tx.framework.web.common.persistence.dao.BaseDaoNew;

/**
 * 增删改查模板
 * 
 * @author tangx
 * 
 */
public class CrudProvider<T> {

	public String select(final Class<T> clazz) {
		return new SQL() {
			{
				SELECT("*");
				FROM(PersistenceUtil.getTableName(clazz));
			}
		}.toString();
	}

	@SuppressWarnings("unchecked")
	public String selectById(final Map<String, Object> parameter) {
		return new SQL() {
			{
				Class<T> clazz = (Class<T>) parameter.get(BaseDaoNew.CLASS_KEY);
				SELECT("*");
				FROM(PersistenceUtil.getTableName(clazz));
				WHERE(PersistenceUtil.getIdColumnName(clazz) + " = '" + parameter.get(BaseDaoNew.ID_KEY) + "'");
			}
		}.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String complicatedSelect(final Map<String, Object> parameter) {
		return new SQL() {
			{
				Class<T> clazz = (Class<T>) parameter.get(BaseDaoNew.CLASS_KEY);
				// 相等查询条件（key=value）
				Map<String, Object> paramMap = Maps.newHashMap();
				if(parameter.containsKey(BaseDaoNew.PARA_KEY)){
					paramMap = (Map<String, Object>) parameter.get(BaseDaoNew.PARA_KEY);
				}
				// like查询条件（key like '%value%'）
				Map<String, Object> likeMap = Maps.newHashMap();
				if(parameter.containsKey(BaseDaoNew.LIKE_KEY)){
					likeMap = (Map<String, Object>) parameter.get(BaseDaoNew.LIKE_KEY);
				}
				// 排序条件
				List<String> orders = Lists.newArrayList();
				if(parameter.containsKey(BaseDaoNew.ORDER_KEY)){
					orders = (List<String>) parameter.get(BaseDaoNew.ORDER_KEY);
				}
				String orderBy = StringUtils.join(orders, ",");
				SELECT("*");
				FROM(PersistenceUtil.getTableName(clazz));
				if(paramMap != null) {
					for(String key : paramMap.keySet())  {
						// 用map key查找数据库列名 如果找不到则以key作为列名
						String columnName = PersistenceUtil.getFieldNameByColumnName(clazz, key);
						if(StringUtils.isBlank(columnName)){
							columnName = key;
						}
						if(paramMap.get(key) != null && (StringUtils.isNotBlank(paramMap.get(key).toString()))){
							WHERE(columnName + "= #{ " + BaseDaoNew.PARA_KEY + "." + key + "}");
						}
					}
				}
				if(likeMap != null) {
					for(String key : likeMap.keySet())  {
						// 用map key查找数据库列名 如果找不到则以key作为列名
						String columnName = PersistenceUtil.getFieldNameByColumnName(clazz, key);
						if(StringUtils.isBlank(columnName)){
							columnName = key;
						}
						if(likeMap.get(key) != null && (StringUtils.isNotBlank(likeMap.get(key).toString()))){
							WHERE(columnName + " like CONCAT('%',#{" + BaseDaoNew.LIKE_KEY + "." + key + "},'%')");
						}
					}
				}
				if(StringUtils.isNotBlank(orderBy)){
					ORDER_BY(orderBy);
				}
			}
		}.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String selectByPage(final Map<String, Object> parameter) {
		Class<T> clazz = (Class<T>) parameter.get(BaseDaoNew.CLASS_KEY);
		return select(clazz);
	}
	
	public String count(final Class<T> clazz) {
		return new SQL() {
			{
				SELECT("count(*)");
				FROM(PersistenceUtil.getTableName(clazz));
			}
		}.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String countByCondition(final Map<String, Object> parameter) {
		return new SQL() {
			{
				Class<T> clazz = (Class<T>) parameter.get(BaseDaoNew.CLASS_KEY);
				Map<String, Object> paramMap = Maps.newHashMap();
				if(parameter.containsKey(BaseDaoNew.PARA_KEY)){
					paramMap = (Map<String, Object>) parameter.get(BaseDaoNew.PARA_KEY);
				}
				SELECT("count(*)");
				FROM(PersistenceUtil.getTableName(clazz));
				if(paramMap != null){
					for(String key : paramMap.keySet())  {
						// 用map key查找数据库列名 如果找不到则以key作为列名
						String columnName = PersistenceUtil.getFieldNameByColumnName(clazz, key);
						if(StringUtils.isBlank(columnName)){
							columnName = key;
						}
						if(paramMap.get(key) != null && (StringUtils.isNotBlank(paramMap.get(key).toString()))){
							WHERE(columnName + "= #{ " + BaseDaoNew.PARA_KEY + "." + key + "}");
						}
					}
				}
			}
		}.toString();
	}

	public String insert(final T t) {
		return new SQL() {
			{
				INSERT_INTO(PersistenceUtil.getTableName(t.getClass()));
				VALUES(PersistenceUtil.getIdColumnName(t.getClass()) + "," + PersistenceUtil.insertColumnNameList(t),
						"#{" + PersistenceUtil.getIdFieldName(t.getClass()) + "}," + PersistenceUtil.insertFieldNameList(t));

			}
		}.toString();
	}

	public String insertWithoutId(final T t) {
		return new SQL() {
			{
				INSERT_INTO(PersistenceUtil.getTableName(t.getClass()));
				VALUES(PersistenceUtil.insertColumnNameList(t), PersistenceUtil.insertFieldNameList(t));
			}
		}.toString();
	}

	public String update(final T t) {
		return new SQL() {
			{
				UPDATE(PersistenceUtil.getTableName(t.getClass()));
				SET(PersistenceUtil.updateSql(t));
				WHERE(PersistenceUtil.getIdColumnName(t.getClass()) + " = #{" + PersistenceUtil.getIdFieldName(t.getClass()) + "}");
			}
		}.toString();
	}

	@SuppressWarnings("unchecked")
	public String deleteById(final Map<String, Object> parameter) {
		return new SQL() {
			{
				Class<T> clazz = (Class<T>) parameter.get(BaseDaoNew.CLASS_KEY);
				DELETE_FROM(PersistenceUtil.getTableName(clazz));
				WHERE(PersistenceUtil.getIdColumnName(clazz) + " = '" + parameter.get(BaseDaoNew.ID_KEY) + "'");
			}
		}.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String deleteByCondition(final Map<String, Object> parameter) {
		return new SQL() {
			{
				Class<T> clazz = (Class<T>) parameter.get(BaseDaoNew.CLASS_KEY);
				Map<String, Object> paramMap = Maps.newHashMap();
				if(parameter.containsKey(BaseDaoNew.PARA_KEY)){
					paramMap = (Map<String, Object>) parameter.get(BaseDaoNew.PARA_KEY);
				}
				DELETE_FROM(PersistenceUtil.getTableName(clazz));
				if(paramMap != null) {
					for(String key : paramMap.keySet())  {
						// 用map key查找数据库列名 如果找不到则以key作为列名
						String columnName = PersistenceUtil.getFieldNameByColumnName(clazz, key);
						if(StringUtils.isBlank(columnName)){
							columnName = key;
						}
						if(paramMap.get(key) != null && (StringUtils.isNotBlank(paramMap.get(key).toString()))){
							WHERE(columnName + "= #{ " + BaseDaoNew.PARA_KEY + "." + key + "}");
						}
					}
				}
			}
		}.toString();
	}
}
