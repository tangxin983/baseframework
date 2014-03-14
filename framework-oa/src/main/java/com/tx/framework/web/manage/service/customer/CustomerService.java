package com.tx.framework.web.manage.service.customer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.persistence.entity.Customer;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.dao.customer.CustomerDao;
import com.tx.framework.web.manage.service.attachment.AttachmentService;

@Service
@Transactional
public class CustomerService extends BaseService<Customer> {

	private CustomerDao customerDao;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	public void setCustomerDao(CustomerDao customerDao) {
		super.setDao(customerDao);
		this.customerDao = customerDao;
	}

	public void saveCustomer(Customer entity) {
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		entity.setValid(Constant.VALID);
		customerDao.save(entity);
		// 保存附件
		attachmentService.saveAttachment(
				StringUtils.defaultString(entity.getMetaInfo()), "customer",
				entity.getId(), false, false);
	}

	public void updateCustomer(Customer entity) {
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		customerDao.update(entity);
		// 保存附件
		attachmentService.saveAttachment(
				StringUtils.defaultString(entity.getMetaInfo()), "customer",
				entity.getId(), false, false);
	}

	@Override
	public void multiDeleteEntity(List<String> ids) {
		super.multiDeleteEntity(ids);
		// 删除附件
		for (String id : ids) {
			attachmentService.saveAttachment("", "customer", id, false, false);
		}
	}

}
