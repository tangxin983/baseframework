package com.tx.framework.common.util;

import java.util.Collection;

/**
 * 自定义jstl函数
 * @author tangx
 *
 */
public class JstlFunction {

	public static boolean contains(Collection<?> coll, Object o) {
		if(coll !=null && !coll.isEmpty()){
			return coll.contains(o);
		}else{
			return false;
		}
	}
}
