package com.tx.framework.web.webservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.common.mapper.JsonMapper;
import com.tx.framework.common.util.JodaTimeUtil;
import com.tx.framework.common.util.NumberUtils;
import com.tx.framework.web.common.enums.OrderItemType;
import com.tx.framework.web.common.enums.OrderStatus;
import com.tx.framework.web.common.enums.PayType;
import com.tx.framework.web.common.enums.ServiceCode;
import com.tx.framework.web.common.utils.Constant;
import com.tx.framework.web.common.utils.WebServiceUtil;
import com.tx.framework.web.dao.bill.BillDao;
import com.tx.framework.web.dao.card.CardDao;
import com.tx.framework.web.dao.menu.MenuDao;
import com.tx.framework.web.dao.order.OrderDao;
import com.tx.framework.web.dao.order.OrderDetailDao;
import com.tx.framework.web.entity.Bill;
import com.tx.framework.web.entity.Card;
import com.tx.framework.web.entity.Order;
import com.tx.framework.web.entity.OrderDetail;

@Service
@Transactional
public class BillRestService {

	@Autowired
	private BillDao billDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private CardDao cardDao;
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Autowired
	private MenuDao menuDao;

	/**
	 * 新增账单<br>
	 * 
	 * @return
	 */
	public Map<String, Object> createBill(String json) {
		Bill bill = JsonMapper.nonEmptyMapper().fromJson(json, Bill.class);
		// 检查json解析是否正确
		if (bill == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// 检查订单id、服务员id、实收金额、付款类型
		if (StringUtils.isBlank(bill.getOrderId())
				|| StringUtils.isBlank(bill.getOperator())
				|| bill.getRecieveAmount() == null || bill.getType() == null) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		// 检查订单是否存在、订单状态是否正常
		Order order = orderDao.findOne(bill.getOrderId());
		if (order == null) {
			return WebServiceUtil.generateMap(ServiceCode.ORDER_NOT_FOUND);
		}
		if (order.getStatus() != OrderStatus.NORMAL.getValue()) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_ORDER_STATUS);
		}
		Card card = null;
		// 有卡号时需检查卡
		if(StringUtils.isNotBlank(bill.getCardCode())){
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("code", bill.getCardCode());
			List<Card> cards = cardDao.findByParams(paraMap);
			// 无此卡号
			if (cards == null || cards.isEmpty()) {
				return WebServiceUtil.generateMap(ServiceCode.CARD_NOT_FOUND);
			}
			card = cards.get(0);
			// 校验卡状态
			if (!card.getStatus().equals(Constant.CARD_STATUS_NORMAL)) {
				return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_CARD_STATUS);
			}
			// 卡未绑定客户
			if (StringUtils.isBlank(card.getCustomerId())) {
				return WebServiceUtil.generateMap(ServiceCode.NOT_BIND_CUSTOMER);
			}
			// 如果付款类型是卡消费的话，检查卡余额是否小于实收金额
			if (bill.getType() == PayType.CARD.getValue()){
				if(card.getBalance().doubleValue() < bill.getRecieveAmount().doubleValue()){
					return WebServiceUtil.generateMap(ServiceCode.NOT_ENOUGH_BALANCE, card.getBalance());
				}
			}
		}
		// 自定义折扣率(注意实际算时要除以100)
		Double customDiscount = bill.getCustomDiscount() == null?100d:bill.getCustomDiscount();
		// 折扣率(注意实际算时要除以100) 卡号空默认为100
		Double discount = card != null?card.getDiscount():100d;
		// 找出所有状态正常的订单项并计算原始消费金额
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("orderId", bill.getOrderId());
		pMap.put("type", OrderItemType.NORMAL.getValue());
		List<OrderDetail> items = orderDetailDao.findByParams(pMap);
		Double consumeAmount = 0d;
		for(OrderDetail detail : items){
			consumeAmount = NumberUtils.add(consumeAmount, 
					NumberUtils.multiply(menuDao.findOne(detail.getMenuId()).getPrice(), detail.getNum()));
		}
		// 计算应付金额=原始消费金额X折扣率X自定义折扣率
		Double resultAmount = NumberUtils.multiply(consumeAmount, NumberUtils.divide(discount, 100, 2));
		resultAmount = NumberUtils.multiply(resultAmount, NumberUtils.divide(customDiscount, 100, 2), 2);
		// 检查应付金额与实收金额是否相等，不等返回应付金额与错误码
		if (resultAmount.doubleValue() != bill.getRecieveAmount().doubleValue()){
			return WebServiceUtil.generateMap(ServiceCode.AMOUNT_NOT_EQUAL, resultAmount);
		}
		// 计算折扣金额=原始消费金额-应付金额
		Double discountAmount = NumberUtils.subtract(consumeAmount, resultAmount);
		// 设置账单属性
		bill.setConsumeAmount(consumeAmount);
		bill.setResultAmount(resultAmount);
		bill.setDiscountAmount(discountAmount);
		bill.setCurrDate(JodaTimeUtil.getCurrentTime("yyyyMMdd"));
		bill.setOperateTime(System.currentTimeMillis());
		if(card != null){
			bill.setCardId(card.getId());
		}
		bill.setRatio(discount);
		// 创建账单
		billDao.save(bill);
		// 设置订单的账单属性、状态、结单时间
		order.setBillId(bill.getId());
		order.setStatus(OrderStatus.CHECKOUT.getValue());
		order.setEndTime(System.currentTimeMillis());
		// 更新订单属性
		orderDao.update(order);
		// 如果有卡号且付款类型是卡消费，更新卡余额、累计消费额、累计支付额、累计折扣
		if (card != null && bill.getType() == PayType.CARD.getValue()){
			// 余额=原余额-实收金额
			card.setBalance(NumberUtils.subtract(card.getBalance(), bill.getRecieveAmount()));
			// 累计消费=原累计消费+原始消费
			card.setConsumeAmount(NumberUtils.add(card.getConsumeAmount(), bill.getConsumeAmount()));
			// 累计支付=原累计支付+应付
			card.setPayAmount(NumberUtils.add(card.getPayAmount(), bill.getResultAmount()));
			// 累计折扣=原累计折扣+折扣
			card.setDiscountAmount(NumberUtils.add(card.getDiscountAmount(), bill.getDiscountAmount()));
			cardDao.update(card);
		}
		return WebServiceUtil.successMap(bill);
	}
}
