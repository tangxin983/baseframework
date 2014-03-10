package com.tx.framework.web.dao.customer;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.Customer;



@MyBatisRepository
public interface CustomerDao extends BaseDao<Customer>{
  
	Customer findByName(String customerName);
}
