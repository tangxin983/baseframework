package com.tx.framework.web.manage.service.card;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;







import com.tx.framework.common.util.DateProvider;
import com.tx.framework.common.util.NumberUtils;
import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.persistence.entity.Card;
import com.tx.framework.web.common.persistence.entity.OperateFlow;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.common.utils.DigestUtil;
import com.tx.framework.web.common.utils.ShiroUtil;
import com.tx.framework.web.dao.card.CardDao;
import com.tx.framework.web.dao.operateflow.OperateFlowDao;

@Service
@Transactional
public class CardService extends BaseService<Card> {

	private CardDao cardDao;

	@Autowired
	private OperateFlowDao operateFlowDao;

	@Autowired
	public void setCardDao(CardDao cardDao) {
		super.setDao(cardDao);
		this.cardDao = cardDao;
	}

	/**
	 * 制卡
	 * @param entity
	 */
	public void makeCard(Card entity) {
		// 设置卡状态
		entity.setStatus((byte) Constant.CARD_STATUS_UNUSE);
		// 设置制卡时间、申卡时间
		entity.setBeginDate(DateProvider.DEFAULT.getDate());
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		// 设置制卡人
		entity.setOperator(ShiroUtil.getCurrentUserName());
		// 积分设为0
		if (entity.getDegree() == null) {
			entity.setDegree(0L);
		}
		// 如果密码非空则设置密码散列值、盐值
		if(StringUtils.isNotEmpty(entity.getPwd())){
			entity.setSalt(DigestUtil.generateSalt());
			entity.setPwd(DigestUtil.generateHash(entity.getPwd(), entity.getSalt()));
		}
		cardDao.save(entity);
		makeCardOperation(entity);
	}
	
	/**
	 * 更新
	 * @param entity
	 */
	public void updateMakeCard(Card entity) {
		// 如果密码非空则设置密码散列值、盐值
		if(StringUtils.isNotEmpty(entity.getUpdatePwd())){
			entity.setSalt(DigestUtil.generateSalt());
			entity.setPwd(DigestUtil.generateHash(entity.getUpdatePwd(), entity.getSalt()));
		}
		// 设置更新时间
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		cardDao.update(entity);
	}

	/**
	 * 发卡
	 * @param entity
	 */
	public void sendCard(Card entity) {
		// 设置卡状态为正常
		if (entity.getCustomerId() != null) {
			entity.setStatus((byte) Constant.CARD_STATUS_NORMAL);
		}
		// 设置更新时间
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		// 设置余额和充值金额
		if(entity.getRealBalance() != null){
			entity.setBalance(entity.getRealBalance());
			entity.setCharge(entity.getRealBalance());
		}
		// 积分设为0
		if (entity.getDegree() == null) {
			entity.setDegree(0L);
		}
		cardDao.update(entity);
		sendCardOperation(entity);
		chargeCardOperation(entity);
	}
	
	/**
	 * 卡充值
	 * @param entity
	 */
	public void chargeCard(Card entity) {
		// 设置更新时间
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		// 现余额 = 旧余额 + 充值金额
		// 现实收金额 = 旧实收金额 + 充值金额
		if(entity.getCharge() != null){
			entity.setBalance(NumberUtils.add(entity.getBalance(), entity.getCharge()));
			entity.setRealBalance(NumberUtils.add(entity.getRealBalance(), entity.getCharge()));
		}
		cardDao.update(entity);
		chargeCardOperation(entity);
	}
	
	/**
	 * 退卡
	 * @param entity
	 */
	public void returnCard(Card entity) {
		// 设置更新时间
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		// 设置卡状态为未使用
		entity.setStatus((byte) Constant.CARD_STATUS_UNUSE);
		// 去除会员关联
		entity.setCustomerId("");
		// 将金额相关的字段初始化、积分初始化
		entity.setBalance(0d);
		entity.setRealBalance(0d);
		entity.setDegree(0l);
		entity.setPayAmount(0d);
		entity.setConsumeAmount(0d);
		entity.setDiscountAmount(0d);
		//entity.setPledge(0d);
		// 将前台传来的charge改为负数
		entity.setCharge(- entity.getCharge());
		cardDao.update(entity);
		returnCardOperation(entity);
		chargeCardOperation(entity);
	}
	
	/**
	 * 挂失与解挂
	 * @param entity
	 */
	public void lostOrHangCard(Card entity) {
		// 设置更新时间
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		cardDao.update(entity);
		if(entity.getStatus().equals(Constant.CARD_STATUS_LOST)){
			lostCardOperation(entity);
		}else if(entity.getStatus().equals(Constant.CARD_STATUS_NORMAL)){
			hangCardOperation(entity);
		}
	}
	
	public boolean isDeleteCard(List<String> ids) {
		for(String id : ids){
			Card card = cardDao.findOne(id);
			if(card.getStatus() != null &&
					card.getStatus() != Constant.CARD_STATUS_UNUSE){
				return false;
			}
		}
		return true;
	}
	
	private OperateFlow getOperateFlow(Card entity){
		OperateFlow operateFlow = new OperateFlow();
		operateFlow.setCardid(entity.getId());
		operateFlow.setCustomerid(entity.getCustomerId());
		operateFlow.setOperatorid(ShiroUtil.getCurrentUserId());
		operateFlow.setDisposename(ShiroUtil.getCurrentUserName());
		operateFlow.setOptime(DateProvider.DEFAULT.getDate());
		operateFlow.setValid(new Byte((byte) 1));
		return operateFlow;
	}
	 
	/**
	 * 操作流水 - 制卡 代码1
	 * 
	 * @param entity
	 */
	private void makeCardOperation(Card entity) {
		OperateFlow operateFlow = getOperateFlow(entity);
		operateFlow.setAction(new Byte((byte) Constant.OPERATE_MAKE));
		operateFlowDao.save(operateFlow);
	}

	/**
	 * 操作流水 - 发卡 代码2
	 * 
	 * @param entity
	 */
	private void sendCardOperation(Card entity) {
		OperateFlow operateFlow = getOperateFlow(entity);
		operateFlow.setAction(new Byte((byte) Constant.OPERATE_SEND));
		operateFlowDao.save(operateFlow);
	}
	
	/**
	 * 操作流水 - 退卡 代码3
	 * 
	 * @param entity
	 */
	private void returnCardOperation(Card entity) {
		OperateFlow operateFlow = getOperateFlow(entity);
		operateFlow.setAction(new Byte((byte) Constant.OPERATE_RETURN));
		operateFlowDao.save(operateFlow);
	}
	
	/**
	 * 操作流水 - 挂失 代码4
	 * 
	 * @param entity
	 */
	private void lostCardOperation(Card entity) {
		OperateFlow operateFlow = getOperateFlow(entity);
		operateFlow.setAction(new Byte((byte) Constant.OPERATE_LOST));
		operateFlowDao.save(operateFlow);
	}
	
	/**
	 * 操作流水 - 解挂 代码5
	 * 
	 * @param entity
	 */
	private void hangCardOperation(Card entity) {
		OperateFlow operateFlow = getOperateFlow(entity);
		operateFlow.setAction(new Byte((byte) Constant.OPERATE_HANG));
		operateFlowDao.save(operateFlow);
	}


	/**
	 * 款项流水 - 充值 代码11
	 * 
	 * @param entity
	 */
	private void chargeCardOperation(Card entity) {
		if (entity.getCustomerId() != null
				&& entity.getCharge() != null
				&& entity.getCharge() != 0) {
			OperateFlow operateFlow = getOperateFlow(entity);
			operateFlow.setAction(new Byte((byte) Constant.OPERATE_CHARGE));
			operateFlow.setPay(entity.getCharge());
			operateFlowDao.save(operateFlow);
		}
	}

}
