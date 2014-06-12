package com.tx.framework.web.modules.oa.service;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.web.common.persistence.entity.Leave;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.modules.oa.dao.LeaveDao;
import com.tx.framework.web.modules.workflow.service.WorkFlowService;

/**
 * 请假Service
 * @author tangx
 * @since 2014-05-28
 */
@Service
@Transactional
public class LeaveService extends BaseService<Leave, String> {
	
	private static final String PROCESS_DEF_KEY = "leave";
	
	@Autowired
	private WorkFlowService workFlowService;

	private LeaveDao leaveDao;

	@Autowired
	public void setLeaveDao(LeaveDao leaveDao) {
		super.setDao(leaveDao);
		this.leaveDao = leaveDao;
	}
	
	/**
	 * 启动请假流程
	 * @param entity
	 */
	public ProcessInstance saveLeave(Leave entity) {
		leaveDao.insert(entity);
		ProcessInstance processInstance = workFlowService.startWorkflow(entity, PROCESS_DEF_KEY, null);
		entity.setProcessInstanceId(processInstance.getId());
		entity.setProcessStatus(workFlowService.getCurrentTaskName(processInstance.getId()));
		leaveDao.update(entity);
		return processInstance;
	}
	
}
