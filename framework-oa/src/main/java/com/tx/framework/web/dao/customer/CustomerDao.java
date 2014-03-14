package com.tx.framework.web.dao.customer;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Customer;



@MyBatisDao
public interface CustomerDao extends BaseDao<Customer>{
  
	Customer findByName(String customerName);
}
