package com.tx.framework.web.webservice.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.webservice.service.CardRestService;

/**
 * 卡接口
 * 
 * 卡登录：/login 查找卡：/find
 * 
 * @author tangx
 * 
 */
@Controller
@RequestMapping(value = "/api/secure/card")
public class CardRestController {

	private static Logger logger = LoggerFactory.getLogger(CardRestController.class);

	@Autowired
	private CardRestService cardRestService;

	/**
	 * 查找会员信息
	 * 
	 * @param json
	 *            (cardNo)
	 */
	@RequestMapping(value = "find")
	@ResponseBody
	public Map<String, Object> findCard(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return cardRestService.getByCardNo(json, false);
	}

	/**
	 * 会员卡登录
	 * 
	 * @param json
	 *            (cardNo,pwd)
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public Map<String, Object> login(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return cardRestService.getByCardNo(json, true);
	}

}
