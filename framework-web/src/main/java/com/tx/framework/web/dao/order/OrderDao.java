package com.tx.framework.web.dao.order;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.Order;



@MyBatisRepository
public interface OrderDao extends BaseDao<Order>{
  
	int findSerial(String date);
	
}
