package com.tx.framework.web.common.persistence.dao;

import org.apache.ibatis.jdbc.SQL;

import com.tx.framework.web.common.persistence.entity.MybatisEntity;

/**
 * 增删改查模板
 * 
 * @author tangx
 * 
 * @param <T>
 */
public class CrudProvider<T extends MybatisEntity> {

	public String select(final Class<T> clazz) {
		try {
			return new SQL() {
				{
					SELECT("*");
					FROM(clazz.newInstance().tablename());
				}
			}.toString();
		} catch (Exception e) {
			throw new RuntimeException("CrudProvider select error");
		} 
	}

	public String insert(final T t) {
		t.caculationColumnList();
		return new SQL() {
			{
				INSERT_INTO(t.tablename());
				VALUES(t.idColumn() + "," + t.insertColumnNameList(),
						"#{" + t.idField() + "}," + t.insertFieldNameList());

			}
		}.toString();
	}

	public String update(final T t) {
		t.caculationColumnList();
		return new SQL() {
			{
				UPDATE(t.tablename());
				SET(t.updateSql());
				WHERE(t.idColumn() + " = #{" + t.idField() + "}");
			}
		}.toString();
	}

	public String delete(final T t) {
		return new SQL() {
			{
				DELETE_FROM(t.tablename());
				WHERE(t.idColumn() + " = #{" + t.idField() + "}");
			}
		}.toString();
	}
}
