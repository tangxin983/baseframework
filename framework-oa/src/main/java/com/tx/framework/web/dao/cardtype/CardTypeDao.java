package com.tx.framework.web.dao.cardtype;

import java.util.List;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.CardType;




@MyBatisDao
public interface CardTypeDao extends BaseDao<CardType>{
 
	List<CardType> findValidUpCard(String id); 
	
	void updateCardTypeStatus(String id);
	
	void updateUpCardInfo(String id);
	
	CardType findByName(String cardTypeName);
}
