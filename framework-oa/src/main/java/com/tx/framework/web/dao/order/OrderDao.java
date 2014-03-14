package com.tx.framework.web.dao.order;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Order;



@MyBatisDao
public interface OrderDao extends BaseDao<Order>{
  
	int findSerial(String date);
	
}
