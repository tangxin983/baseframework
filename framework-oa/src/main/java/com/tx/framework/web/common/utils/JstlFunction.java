package com.tx.framework.web.common.utils;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tx.framework.common.util.CollectionUtils;

/**
 * 自定义jstl函数
 * 
 * @author tangx
 * 
 */
public class JstlFunction {

	public static <T> boolean contains(Collection<T> coll, Object o) {
		if (coll != null && !coll.isEmpty()) {
			return coll.contains(o);
		} else {
			return false;
		}
	}

	public static <T> String join(List<T> list, String separator) {
		return StringUtils.join(list, separator);
	}
	
	public static <T> String extractProperty(Collection<T> collection, String propertyName, boolean ignoreEmptyValue) {
		return StringUtils.join(CollectionUtils.extractToList(collection, propertyName, ignoreEmptyValue), ",");
	}
}
