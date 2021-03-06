package com.tx.framework.web.common.utils;

import java.util.Collection;

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

}
