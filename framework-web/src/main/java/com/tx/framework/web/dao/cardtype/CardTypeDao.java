package com.tx.framework.web.dao.cardtype;

import java.util.List;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.CardType;




@MyBatisRepository
public interface CardTypeDao extends BaseDao<CardType>{
 
	List<CardType> findValidUpCard(String id); 
	
	void updateCardTypeStatus(String id);
	
	void updateUpCardInfo(String id);
	
	CardType findByName(String cardTypeName);
}
