package com.tx.framework.web.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.tx.framework.web.common.enums.ServiceCode;


public class WebServiceUtil {

	/**
	 * 错误Map
	 * 
	 * @param code
	 *            错误码
	 * @param data
	 *            数据
	 * @return
	 */
	public static Map<String, Object> generateMap(ServiceCode code, Object data) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", code.getValue());
		ret.put("data", data);
		return ret;
	}

	/**
	 * 错误Map
	 * 
	 * @param code
	 *            错误码
	 * @return
	 */
	public static Map<String, Object> generateMap(ServiceCode code) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", code.getValue());
		return ret;
	}

	/**
	 * 成功Map
	 * 
	 * @param data
	 *            数据
	 * @return
	 */
	public static Map<String, Object> successMap(Object data) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", ServiceCode.SUCCESS.getValue());
		ret.put("data", data);
		return ret;
	}
	
	/**
	 * 成功Map
	 * 
	 * @return
	 */
	public static Map<String, Object> successMap() {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", ServiceCode.SUCCESS.getValue());
		return ret;
	}
}
