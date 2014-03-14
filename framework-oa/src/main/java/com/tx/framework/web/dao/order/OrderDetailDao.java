package com.tx.framework.web.dao.order;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.OrderDetail;




@MyBatisDao
public interface OrderDetailDao extends BaseDao<OrderDetail>{
  
	void deleteByOrderId(String id);
	 
}
