package com.tx.framework.web.webservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.common.mapper.JsonMapper;
import com.tx.framework.common.util.JodaTimeUtil;
import com.tx.framework.web.common.enums.MenuStatus;
import com.tx.framework.web.common.enums.OrderItemStatus;
import com.tx.framework.web.common.enums.OrderItemType;
import com.tx.framework.web.common.enums.OrderStatus;
import com.tx.framework.web.common.enums.ServiceCode;
import com.tx.framework.web.common.utils.WebServiceUtil;
import com.tx.framework.web.dao.menu.MenuDao;
import com.tx.framework.web.dao.order.OrderDao;
import com.tx.framework.web.dao.order.OrderDetailDao;
import com.tx.framework.web.entity.Menu;
import com.tx.framework.web.entity.Order;
import com.tx.framework.web.entity.OrderDetail;

@Service
@Transactional
public class OrderRestService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private OrderDetailDao orderDetailDao;

	/**
	 * 新增订单或更新订单对应商品列表<br>
	 * 1、没有orderId是新单<br>
	 * 2、新单有workerId说明是服务员开单<br>
	 * 3、是旧单则增量订单项列表
	 * 
	 * @return
	 */
	public Map<String, Object> createOrder(String json) {
		Order order = JsonMapper.nonEmptyMapper().fromJson(json, Order.class);
		// 检查json解析是否正确
		if (order == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// 桌位id不能为空
		if (StringUtils.isBlank(order.getSeatId())) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		// 如果有订单号则检查订单是否存在、订单状态是否正常
		if (StringUtils.isNotBlank(order.getId())) {
			Order checkOrder = orderDao.findOne(order.getId());
			if (checkOrder == null) {
				return WebServiceUtil.generateMap(ServiceCode.ORDER_NOT_FOUND);
			}
			if (checkOrder.getStatus() != OrderStatus.NORMAL.getValue()) {
				return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_ORDER_STATUS);
			}
		}
		List<OrderDetail> items = order.getItems();
		List<String> notFoundMenus = new ArrayList<String>();
		List<String> illegalStatusMenuIds = new ArrayList<String>();
		if(items != null){
			for (OrderDetail orderDetail : items) {
				Menu menu = menuDao.findOne(orderDetail.getMenuId());
				if (menu == null) {
					notFoundMenus.add(orderDetail.getMenuId());
				}else if (menu.getStatus().intValue() != MenuStatus.NORMAL.getValue()) {
					illegalStatusMenuIds.add(orderDetail.getMenuId());
				}
			}
		}
		// 检查商品是否存在
		if (!notFoundMenus.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.MENU_NOT_FOUND,notFoundMenus);
		}
		// 检查商品状态是否为在售
		if (!illegalStatusMenuIds.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_MENU_STATUS,illegalStatusMenuIds);
		}
		// 如果参数中没有订单号表明这是新单
		if (StringUtils.isBlank(order.getId())) {
			long timeMill = System.currentTimeMillis();
			order.setCreateTime(timeMill);
			String currtime = JodaTimeUtil.convertTimeMillToString(timeMill, "yyyyMMddHHmmss");
			order.setCode(getOrderCode(currtime));
			order.setInnerCode(getOrderInnerCode(currtime));
			orderDao.save(order);
		}
		// 保存商品列表
		if(items != null){
			for (OrderDetail orderDetail : items) {
				orderDetail.setOrderId(order.getId());
				orderDetail.setCreateTime(System.currentTimeMillis());
				orderDetailDao.save(orderDetail);
			}
		}
		// 重新查出此单（如果直接用json对象则状态等默认字段为空）
		order = orderDao.findOne(order.getId());
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("orderId", order.getId());
		order.setItems(orderDetailDao.findByParams(pMap));
		return WebServiceUtil.successMap(order);
	}

	/**
	 * 更新订单项状态为退菜
	 * 
	 * @param json
	 * @return
	 */
	public Map<String, Object> deleteOrderItem(String json) {
		Order order = JsonMapper.nonEmptyMapper().fromJson(json, Order.class);
		// 检查json解析是否正确
		if (order == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// 订单id不能为空
		if (StringUtils.isBlank(order.getId())) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		// 订单项列表不能为空
		List<OrderDetail> items = order.getItems();
		if (items == null || items.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		// 检查订单是否存在、订单状态是否正常
		order = orderDao.findOne(order.getId());
		if (order == null) {
			return WebServiceUtil.generateMap(ServiceCode.ORDER_NOT_FOUND);
		}
		if (order.getStatus() != OrderStatus.NORMAL.getValue()) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_ORDER_STATUS);
		}
		List<String> illegalStatusItems = new ArrayList<String>();
		List<String> notFoundItems = new ArrayList<String>();
		for (OrderDetail orderDetail : items) {
			OrderDetail checkOrderDetail = orderDetailDao.findOne(orderDetail.getId());
			if(checkOrderDetail == null){
				notFoundItems.add(orderDetail.getId());
			} else if(checkOrderDetail.getStatus() == OrderItemStatus.DONE.getValue()){
				illegalStatusItems.add(orderDetail.getId());
			}
		}
		// 检查订单项是否存在
		if (!notFoundItems.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.ITEM_NOT_FOUND, notFoundItems);
		}
		// 检查订单项状态是否为出菜 如果是出菜状态则不能退菜
		if (!illegalStatusItems.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_ITEM_STATUS, illegalStatusItems);
		}
		// 更新订单项类型为退菜
		for (OrderDetail orderDetail : items) {
			orderDetail.setType(OrderItemType.RETURN.getValue());
			orderDetailDao.update(orderDetail);
		}
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("orderId", order.getId());
		order.setItems(orderDetailDao.findByParams(pMap));
		return WebServiceUtil.successMap(order);
	}
	
	/**
	 * 根据各种条件组合查找订单<br>
	 * 如：订单id、订单编号、桌位id、订单状态、订单项状态
	 * @param json
	 * @return
	 */
	public Map<String, Object> find(String json) {
		Order order = JsonMapper.nonEmptyMapper().fromJson(json, Order.class);
		// 检查json解析是否正确
		if (order == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// 订单id、订单编号、桌位id、查询日期、菜品名称不能都为空
		if (StringUtils.isBlank(order.getId()) && StringUtils.isBlank(order.getCode()) 
				 && StringUtils.isBlank(order.getSeatId()) && StringUtils.isBlank(order.getQueryDate())
						 && StringUtils.isBlank(order.getMenuName())) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		// 根据条件查订单
		Map<String, Object> pMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(order.getId())){
			pMap.put("id", order.getId());
		}
		if(StringUtils.isNotBlank(order.getCode())){
			pMap.put("code", order.getCode());
		}
		if(StringUtils.isNotBlank(order.getSeatId())){
			pMap.put("seatId", order.getSeatId());
		}
		if(StringUtils.isNotBlank(order.getQueryDate())){
			pMap.put("queryDate", order.getQueryDate());
		}
		if(StringUtils.isNotBlank(order.getMenuName())){
			pMap.put("menuName", order.getMenuName());
		}
		if(order.getStatus() != null){
			pMap.put("status", order.getStatus());
		}
		List<Order> orders = orderDao.findByParams(pMap);
		// 订单是否为空
		if(orders.isEmpty()){
			return WebServiceUtil.generateMap(ServiceCode.ORDER_NOT_FOUND);
		}
		pMap = new HashMap<String, Object>();
		// 设置订单项状态、类型
		if(order.getItemStatus() != null){
			pMap.put("status", order.getItemStatus());
		}
		if(order.getItemType() != null){
			pMap.put("type", order.getItemType());
		}
		for(Order o : orders){
			pMap.put("orderId", o.getId());
			o.setItems(orderDetailDao.findByParams(pMap));
		}
		return WebServiceUtil.successMap(orders);
	}
	
	/**
	 * 更改订单属性（换桌、撤单、下单到厨房、修改备注等）
	 * 
	 * @param json
	 * @return
	 */
	public Map<String, Object> updateOrder(String json) {
		Order order = JsonMapper.nonEmptyMapper().fromJson(json, Order.class);
		// 检查json解析是否正确
		if (order == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// 订单id不能为空
		if (StringUtils.isBlank(order.getId())) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		Order checkOrder = orderDao.findOne(order.getId());
		// 订单是否为空
		if(checkOrder == null){
			return WebServiceUtil.generateMap(ServiceCode.ORDER_NOT_FOUND);
		}
		// 更新订单属性
		orderDao.update(order);
		// 重新查出此单
		order = orderDao.findOne(order.getId());
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("orderId", order.getId());
		order.setItems(orderDetailDao.findByParams(pMap));
		return WebServiceUtil.successMap(order);
	}
	
	/**
	 * 配菜/待配/出菜(其实就是更改订单项状态)
	 * 
	 * @param json
	 * @return
	 */
	public Map<String, Object> updateItemStatus(String json) {
		Order order = JsonMapper.nonEmptyMapper().fromJson(json, Order.class);
		// 检查json解析是否正确
		if (order == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// 订单项列表不能为空
		List<OrderDetail> items = order.getItems();
		if (items == null || items.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		// 检查订单项是否存在，订单项对应的订单是否存在、状态是否正常
		List<String> notFoundItems = new ArrayList<String>();// 找不到的订单项
		List<String> notFoundOrderItems = new ArrayList<String>();// 找不到对应订单的订单项
		List<String> illegalOrderItems = new ArrayList<String>();// 对应订单状态非法的订单项
		for (OrderDetail orderDetail : items) {
			OrderDetail checkOrderDetail = orderDetailDao.findOne(orderDetail.getId());
			if(checkOrderDetail == null){
				notFoundItems.add(orderDetail.getId());
			}else{
				Order checkOrder = orderDao.findOne(checkOrderDetail.getOrderId());
				if (checkOrder == null) {
					notFoundOrderItems.add(orderDetail.getId());
				}
				if (checkOrder.getStatus() != OrderStatus.NORMAL.getValue()) {
					illegalOrderItems.add(orderDetail.getId());
				}
			}
		}
		if (!notFoundItems.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.ITEM_NOT_FOUND, notFoundItems);
		}
		if (!notFoundOrderItems.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.ORDER_NOT_FOUND, notFoundOrderItems);
		}
		if (!illegalOrderItems.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_ORDER_STATUS, illegalOrderItems);
		}
		// 更新订单项状态
		for (OrderDetail orderDetail : items) {
			// 出菜则更新出菜时间
			if(orderDetail.getStatus() != null && orderDetail.getStatus() == OrderItemStatus.DONE.getValue()){
				orderDetail.setDoneTime(System.currentTimeMillis());
			}
			orderDetailDao.update(orderDetail);
		}
		return WebServiceUtil.successMap();
	}

	/**
	 * 订单编号<br>
	 * 格式：yyMMddHHmmss + 4位随机数字
	 * 
	 * @return
	 */
	private String getOrderCode(String currtime) {
		Random random = new Random();
		return currtime.substring(2)
				+ String.format("%04d", random.nextInt(10000));
	}

	/**
	 * 订单内部编号<br>
	 * 格式：yyyyMMdd + 4位流水号
	 * 
	 * @return
	 */
	private String getOrderInnerCode(String currtime) {
		String date = currtime.substring(0, 8);
		return date + String.format("%04d", orderDao.findSerial(date));
	}
}
