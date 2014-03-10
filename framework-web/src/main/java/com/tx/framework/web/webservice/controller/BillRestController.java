package com.tx.framework.web.webservice.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.webservice.service.BillRestService;

/**
 * 订单接口
 * 
 * 生成订单：/create
 * 
 * 
 * 
 * @author tangx
 * 
 */
@Controller
@RequestMapping(value = "/api/secure/bill")
public class BillRestController {

	private static Logger logger = LoggerFactory.getLogger(BillRestController.class);

	@Autowired
	private BillRestService billRestService;

	/**
	 * 创建账单
	 * 
	 * @param json
	 */
	@RequestMapping(value = "create")
	@ResponseBody
	public Map<String, Object> create(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return billRestService.createBill(json);
	}

}
