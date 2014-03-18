package com.tx.framework.web.common.persistence.dao;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.tx.framework.web.common.persistence.entity.MybatisEntity;
import com.tx.framework.web.common.persistence.util.PersistenceUtil;

/**
 * 增删改查模板
 * 
 * @author tangx
 * 
 */
public class CrudProvider<T extends MybatisEntity, PK> {

	public String selectAll(final Class<T> clazz) {
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
				Map<String, Object> paramMap = (Map<String, Object>) parameter.get(BaseDaoNew.PARA_KEY);
				SELECT("*");
				FROM(PersistenceUtil.getTableName(clazz));
				for(String key : paramMap.keySet())  {
					// 用map key查找数据库列名 如果找不到则以key作为列名
					String columnName = PersistenceUtil.getFieldNameByColumnName(clazz, key);
					if(StringUtils.isBlank(columnName)){
						columnName = key;
					}
					if(paramMap.get(key) != null){
						WHERE(columnName + "= #{ " + BaseDaoNew.PARA_KEY + "." + key + "}");
					}
				}
			}
		}.toString();
	}

	public String insert(final T t) {
		return new SQL() {
			{
				INSERT_INTO(t.tablename());
				VALUES(t.idColumn() + "," + PersistenceUtil.insertColumnNameList(t),
						"#{" + t.idField() + "}," + PersistenceUtil.insertFieldNameList(t));

			}
		}.toString();
	}

	public String insertWithoutId(final T t) {
		return new SQL() {
			{
				INSERT_INTO(t.tablename());
				VALUES(PersistenceUtil.insertColumnNameList(t), PersistenceUtil.insertFieldNameList(t));
			}
		}.toString();
	}

	public String update(final T t) {
		return new SQL() {
			{
				UPDATE(t.tablename());
				SET(PersistenceUtil.updateSql(t));
				WHERE(t.idColumn() + " = #{" + t.idField() + "}");
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
