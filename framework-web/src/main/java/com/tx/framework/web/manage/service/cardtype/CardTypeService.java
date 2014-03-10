package com.tx.framework.web.manage.service.cardtype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.base.BaseService;
import com.tx.framework.web.common.utils.Constant;
import com.tx.framework.web.dao.cardtype.CardTypeDao;
import com.tx.framework.web.entity.CardType;


@Service
@Transactional
public class CardTypeService extends BaseService<CardType> {

	private CardTypeDao cardTypeDao;

	@Autowired
	public void setCardTypeDao(CardTypeDao cardTypeDao) {
		super.setDao(cardTypeDao);
		this.cardTypeDao = cardTypeDao;
	}
 
	/**
	 * 上级卡类型
	 * @param id
	 * @return
	 */
	public List<CardType> getValidUpCard(String id) {
		return cardTypeDao.findValidUpCard(id); 
	}
	
	/**
	 * 新增卡类型
	 * @param entity
	 */
	public void saveCardType(CardType entity) {
		if(entity.getPrecharge() == null){
			entity.setPrecharge(0d);
		}
		entity.setValid(Constant.VALID);
		entity.setCreatetime(DateProvider.DEFAULT.getDate());
		cardTypeDao.save(entity);
	}
	
	/**
	 * 更新卡类型
	 * @param entity
	 */
	public void updateCardType(CardType entity) {
		if(entity.getPrecharge() == null){
			entity.setPrecharge(0d);
		}
		entity.setUpdatetime(DateProvider.DEFAULT.getDate());
		cardTypeDao.update(entity);
	}
	
	/**
	 * 删除。卡类型变为不可用
	 * @param id
	 */
	public void deleteCardType(String id){
		cardTypeDao.updateCardTypeStatus(id);
		cardTypeDao.updateUpCardInfo(id);
	}

	/**
	 * 
	 * @param id
	 */
	public void MultiDeleteCardType(List<String> ids){
		for(String id : ids){
			cardTypeDao.updateCardTypeStatus(id);
			cardTypeDao.updateUpCardInfo(id);
		}
	}
}
