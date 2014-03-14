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
	 * 获取表名 需要POJO定义@Table(name)，如果没有定义则取类名为表名
	 * 
	 * @return
	 */
	public String tablename() {
		Table table = this.getClass().getAnnotation(Table.class);
		if (table != null) {
			if (StringUtils.isNotBlank(table.name())) {
				return table.name();
			} 
		}
		return this.getClass().getSimpleName();
	}

	/**
	 * 获取@Id的属性名
	 * 
	 * @return
	 */
	public String idField() {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class))
				return field.getName();
		}
		// 没有找到id，向上找父类的id
		return getParentId(this.getClass().getSuperclass());
	}

	/**
	 * 查询父类@Id的属性名
	 * 
	 * @return
	 */
	private String getParentId(Class<?> clazz) {
		if (MybatisEntity.class.equals(clazz)) {
			// 如果向上找到MybatisEntity基类则返回默认的'id'
			return "id";
		}
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class))
				return field.getName();
		}
		// 继续向上找父类id
		return getParentId(clazz.getSuperclass());
	}

	/**
	 * 获取主键对应的数据库字段名称
	 * 
	 * @return
	 */
	public String idColumn() {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				if (field.isAnnotationPresent(Column.class)) {
					Column c = field.getAnnotation(Column.class);
					if (StringUtils.isNotBlank(c.name())) {
						return c.name();
					}
				}
				return field.getName();
			}
		}
		return getParentIdColumnName(this.getClass().getSuperclass());
	}

	/**
	 * 查询父类的主键数据库字段名称
	 * 
	 * @return
	 */
	private String getParentIdColumnName(Class<?> clazz) {
		if (MybatisEntity.class.equals(clazz)) {
			// 如果向上找到MybatisEntity基类则返回默认的'id'
			return "id";
		}
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				if (field.isAnnotationPresent(Column.class)) {
					Column c = field.getAnnotation(Column.class);
					if (StringUtils.isNotBlank(c.name())) {
						return c.name();
					}
				}
				return field.getName();
			}
		}
		return getParentIdColumnName(clazz.getSuperclass());
	}

	/**
	 * 用于存放各实体的@Column列表
	 */
	private transient static Map<Class<? extends MybatisEntity>, Map<String, String>> columnMap = new HashMap<Class<? extends MybatisEntity>, Map<String, String>>();

	/**
	 * 计算POJO中标记为@Column的属性，以属性名为key，数据库字段名为value，放到Map中(这里排除@Id字段，即使该字段也有@Column)
	 */
	public void caculationColumnList() {
		if (columnMap.containsKey(this.getClass())) {
			return;
		}
		Field[] fields = this.getClass().getDeclaredFields();
		Map<String, String> columnDefs = new HashMap<String, String>();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Column.class)) {
				if (field.isAnnotationPresent(Id.class)) {
					continue;
				}
				columnDefs.put(field.getName(), getColumnName(field));
			}
		}
		columnMap.put(this.getClass(), columnDefs);
	}

	/**
	 * 获取需要insert的数据库字段列表 (忽略空值字段)
	 * 
	 * @return column1,column2,...
	 */
	public String insertColumnNameList() {
		StringBuilder sb = new StringBuilder();
		Map<String, String> columnDefs = columnMap.get(this.getClass());
		int i = 0;
		for (String fieldName : columnDefs.keySet()) {
			if (isNull(fieldName)) {
				continue;
			}
			if (i++ != 0) {
				sb.append(',');
			}
			sb.append(columnDefs.get(fieldName));
		}
		return sb.toString();
	}

	/**
	 * 获取需要insert的属性列表(忽略空值字段)
	 * 
	 * @return #{field1},#{field2},...
	 */
	public String insertFieldNameList() {
		StringBuilder sb = new StringBuilder();
		Map<String, String> columnDefs = columnMap.get(this.getClass());
		int i = 0;
		for (String fieldName : columnDefs.keySet()) {
			if (isNull(fieldName)) {
				continue;
			}
			if (i++ != 0) {
				sb.append(',');
			}
			sb.append("#{").append(fieldName).append('}');
		}
		return sb.toString();
	}

	/**
	 * 获取update的SQL(忽略空值字段)
	 * 
	 * @return column1=#{field1},column2=#{field2},...
	 */
	public String updateSql() {
		StringBuilder sb = new StringBuilder();
		Map<String, String> columnDefs = columnMap.get(this.getClass());
		int i = 0;
		for (String fieldName : columnDefs.keySet()) {
			if (isNull(fieldName)) {
				continue;
			}
			if (i++ != 0) {
				sb.append(',');
			}
			sb.append(columnDefs.get(fieldName)).append("=#{")
					.append(fieldName).append('}');
		}
		return sb.toString();
	}

	/**
	 * 获取@Column对应的数据库列名
	 * 
	 * @return
	 */
	private String getColumnName(Field field) {
		Column c = field.getAnnotation(Column.class);
		if (StringUtils.isEmpty(c.name())) {
			return field.getName();
		} else {
			return c.name();
		}
	}

	/**
	 * 判断字段值是否为空
	 * 
	 * @return
	 */
	private boolean isNull(String fieldName) {
		try {
			return FieldUtils.readField(this, fieldName, true) == null ? true
					: false;
		} catch (Exception e) {
			throw new RuntimeException("isNull error");
		}
	}
}
