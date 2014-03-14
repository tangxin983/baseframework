package com.tx.framework.web.webservice.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.common.mapper.JsonMapper;
import com.tx.framework.web.common.enums.ServiceCode;
import com.tx.framework.web.common.persistence.entity.User;
import com.tx.framework.web.common.utils.DigestUtil;
import com.tx.framework.web.common.utils.WebServiceUtil;
import com.tx.framework.web.dao.account.UserDao;

@Service
@Transactional
public class UserRestService {

	@Autowired
	private UserDao userDao;

	/**
	 * 服务生登录
	 * 
	 * @param json
	 *            (loginName,pwd)
	 * @return
	 */
	public Map<String, Object> userLogin(String json) {
		Map<String, String> jsonMap = JsonMapper.nonEmptyMapper().fromJson(json, Map.class);
		// 检查json解析是否正确
		if (jsonMap == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// 检查必填参数
		if (StringUtils.isBlank(jsonMap.get("loginName"))
				|| StringUtils.isBlank(jsonMap.get("pwd"))) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		User user = userDao.findByLoginName(jsonMap.get("loginName"));
		// 无此用户
		if (user == null) {
			return WebServiceUtil.generateMap(ServiceCode.USER_NOT_FOUND);
		}
		// 数据库中的密码散列值、盐值
		String password, salt;
		password = user.getPassword();
		// 校验密码错误
		if (StringUtils.isNotEmpty(password)) {
			salt = user.getSalt();
			if (!DigestUtil.isCredentialsMatch(jsonMap.get("pwd"), password,
					salt)) {
				return WebServiceUtil.generateMap(ServiceCode.AUTH_FAIL);
			}
		}
		// 成功登录并返回卡信息
		return WebServiceUtil.successMap(user);
	}
	
	/**
	 * 呼叫服务生
	 * 
	 * @param json
	 * @return
	 */
	public Map<String, Object> call(String json) {
		Map<String, String> jsonMap = JsonMapper.nonEmptyMapper().fromJson(json, Map.class);
		// 检查json解析是否正确
		if (jsonMap == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// 检查必填参数
		if (StringUtils.isBlank(jsonMap.get("seatId"))) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		/*******呼叫逻辑编写*******/
		/*******呼叫逻辑编写*******/
		// 成功呼叫
		return WebServiceUtil.successMap();
	}
}
