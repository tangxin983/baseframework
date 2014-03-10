package com.tx.framework.web.webservice.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.webservice.service.UserRestService;



/**
 * 用户接口
 * 
 * 服务生登录：/login
 * 
 * @author tangx
 *
 */
@Controller
@RequestMapping(value = "/api/secure/user")
public class UserRestController {
	
	private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	private UserRestService userRestService;
	 
	/**
	 * 服务生登录
	 * @param json (loginName,pwd)
	 * @return
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public Map<String, Object> login(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return userRestService.userLogin(json);
	}
	
	/**
	 * 呼叫服务生
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "call")
	@ResponseBody
	public Map<String, Object> call(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return userRestService.call(json);
	}

}
