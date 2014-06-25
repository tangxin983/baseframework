package com.tx.framework.web.common.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.persistence.entity.Dict;
import com.tx.framework.web.common.persistence.entity.User;
import com.tx.framework.web.modules.sys.service.DictService;
import com.tx.framework.web.modules.sys.service.UserService;

/**
 * 自定义jstl函数
 * 
 * @author tangx
 * 
 */
public class JstlFunction {
	
	private static UserService userService = SpringContextHolder.getBean(UserService.class);
	
	private static DictService dictService = SpringContextHolder.getBean(DictService.class);

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
	
	public static List<Dict> getDictList(String type){
		return dictService.findDictListByType(type);
	}
	
	public static String getDictLabel(String type, String value, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
}
