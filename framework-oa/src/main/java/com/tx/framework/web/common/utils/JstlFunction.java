package com.tx.framework.web.common.utils;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 自定义jstl函数
 * 
 * @author tangx
 * 
 */
public class JstlFunction {

	public static boolean contains(Collection<?> coll, Object o) {
		if (coll != null && !coll.isEmpty()) {
			return coll.contains(o);
		} else {
			return false;
		}
	}

	public static String join(List<?> list, String separator) {
		return StringUtils.join(list, separator);
	}
}
