package com.tx.framework.web.common.utils;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.persistence.entity.User;
import com.tx.framework.web.modules.sys.service.UserService;

/**
 * 自定义jstl函数
 * 
 * @author tangx
 * 
 */
public class JstlFunction {
	
	private static UserService userService = SpringContextHolder.getBean(UserService.class);

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
	
	public static User getUserById(String userId) {
		if(StringUtils.isNotBlank(userId)) {
			return userService.selectById(userId);
		} else {
			return null;
		}
	}
}
