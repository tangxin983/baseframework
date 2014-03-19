package com.tx.framework.web.common.persistence.dao;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.google.common.collect.Maps;
import com.tx.framework.web.common.persistence.util.PersistenceUtil;

/**
 * 增删改查模板
 * 
 * @author tangx
 * 
 */
public class CrudProvider<T, PK> {

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
				PK id = (PK) parameter.get(BaseDaoNew.ID_KEY);
				SELECT("*");
				FROM(PersistenceUtil.getTableName(clazz));
				WHERE(PersistenceUtil.getIdColumnName(clazz) + " = '" + id + "'");
			}
		}.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String selectByCondition(final Map<String, Object> parameter) {
		return new SQL() {
			{
				Class<T> clazz = (Class<T>) parameter.get(BaseDaoNew.CLASS_KEY);
				Map<String, Object> paramMap = Maps.newHashMap();
				if(parameter.containsKey(BaseDaoNew.PARA_KEY)){
					paramMap = (Map<String, Object>) parameter.get(BaseDaoNew.PARA_KEY);
				}
				SELECT("*");
				FROM(PersistenceUtil.getTableName(clazz));
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
				PK id = (PK) parameter.get(BaseDaoNew.ID_KEY);
				DELETE_FROM(PersistenceUtil.getTableName(clazz));
				WHERE(PersistenceUtil.getIdColumnName(clazz) + " = '" + id + "'");
			}
		}.toString();
	}
}
