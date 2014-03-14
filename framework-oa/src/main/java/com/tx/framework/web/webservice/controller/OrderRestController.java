package com.tx.framework.web.webservice.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.webservice.service.OrderRestService;

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
@RequestMapping(value = "/api/secure/order")
public class OrderRestController {

	private static Logger logger = LoggerFactory.getLogger(OrderRestController.class);

	@Autowired
	private OrderRestService orderRestService;

	/**
	 * 下单/加菜
	 * 
	 * @param json
	 */
	@RequestMapping(value = "create")
	@ResponseBody
	public Map<String, Object> create(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return orderRestService.createOrder(json);
	}

	/**
	 * 退菜
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return orderRestService.deleteOrderItem(json);
	}

	/**
	 * 更改订单属性
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> update(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return orderRestService.updateOrder(json);
	}
	
	/**
	 * 根据各种条件组合查找订单<br>
	 * 如：订单id、订单编号、桌位id、日期、订单状态、订单项状态、订单项类型
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "find")
	@ResponseBody
	public Map<String, Object> find(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return orderRestService.find(json);
	}
	
	
	/**
	 * 配菜/待配/出菜
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "updateItemStatus")
	@ResponseBody
	public Map<String, Object> updateItemStatus(@RequestParam("data") String json) {
		logger.debug("data={}", json);
		return orderRestService.updateItemStatus(json);
	}

}
