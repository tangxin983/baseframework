package com.tx.framework.web.common.persistence.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.tx.framework.web.common.persistence.util.PersistenceUtil;

/**
 * 通用Mybatis实体，其他实体需继承此类
 * 
 * @author tangx
 * 
 */
public class MybatisEntity implements Serializable {
	
	public static final String nameId = "id";

	private static final long serialVersionUID = 1L;

	protected String id;// 如果子类没定义@Id字段，用这个作为默认主键

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取表名
	 * 
	 * @return
	 */
	public String tablename() {
		return PersistenceUtil.getTableName(this.getClass());
	}

	/**
	 * 获取@Id的属性名
	 * 
	 * @return
	 */
	public String idField() {
		return PersistenceUtil.getIdFieldName(this.getClass());
	}


	/**
	 * 获取主键对应的数据库字段名称
	 * 
	 * @return
	 */
	public String idColumn() {
		return PersistenceUtil.getIdColumnName(this.getClass());
	}
	 
}
