package com.tx.framework.web.modules.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.common.persistence.entity.Leave;
import com.tx.framework.web.modules.oa.dao.LeaveDao;

/**
 * 请假Service
 * @author tangx
 * @since 2014-05-28
 */
@Service
@Transactional
public class LeaveService extends BaseService<Leave, String> {

	private LeaveDao leaveDao;

	@Autowired
	public void setLeaveDao(LeaveDao leaveDao) {
		super.setDao(leaveDao);
		this.leaveDao = leaveDao;
	}
	
}
