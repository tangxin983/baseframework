package com.tx.framework.web.dao.order;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.OrderDetail;




@MyBatisRepository
public interface OrderDetailDao extends BaseDao<OrderDetail>{
  
	void deleteByOrderId(String id);
	 
}
