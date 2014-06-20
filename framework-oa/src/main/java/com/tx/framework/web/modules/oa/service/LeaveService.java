package com.tx.framework.web.modules.oa.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tx.framework.web.common.persistence.entity.Leave;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.common.utils.SysUtil;
import com.tx.framework.web.modules.oa.dao.LeaveDao;
import com.tx.framework.web.modules.workflow.service.WorkFlowService;

/**
 * 请假Service
 * 
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
	 * 
	 * @param entity
	 */
	public ProcessInstance saveLeave(Leave entity) {
		// 设置流程发起人和发起时间
		entity.setApplyUser(SysUtil.getCurrentUserId());
		entity.setApplyTime(new Date());
		leaveDao.insert(entity);
		// 启动流程
		ProcessInstance processInstance = workFlowService.startWorkflow(entity,
				PROCESS_DEF_KEY, null);
		// 设置流程实例ID、当前节点名
		entity.setProcessInstanceId(processInstance.getId());
		entity.setProcessStatus(workFlowService
				.getCurrentTaskName(processInstance.getId()));
		leaveDao.update(entity);
		return processInstance;
	}

	/**
	 * 分页获取激活中的请假流程列表
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param isAll
	 *            true所有人的请假单；false当前用户请假单
	 * @return
	 */
	public Page<Leave> findLeaveInstanceByPage(
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			boolean isAll) {
		if (!isAll) {
			// 设置只获取当前用户的请假单
			searchParams.put("applyUser", SysUtil.getCurrentUserId());
		}
		// 根据条件获取请假列表
		List<Leave> list = select(searchParams);
		// 获取当前和已完结的流程
		List<HistoricProcessInstance> historicInstances = workFlowService
				.getHistoricInstanceList(PROCESS_DEF_KEY);

		List<Leave> result = Lists.newArrayList();
		if (list.size() > 0) {
			Set<String> processInstanceIds = Sets.newHashSet();
			for (HistoricProcessInstance historicInstance : historicInstances) {
				processInstanceIds.add(historicInstance.getId());
			}
			for (Leave leave : list) {
				// 当流程实例里包含此单据时才加入结果集
				if (processInstanceIds.contains(leave.getProcessInstanceId())) {
					workFlowService.setWorkFlowEntity(leave);
					result.add(leave);
				}
			}
		}
		// 设置page对象
		Page<Leave> page = new Page<Leave>();
		page.setCurrentPage(pageNumber);
		page.setSize(pageSize);
		page.setTotal(result.size());
		page.setResult(result.subList(page.getCurrentResult(),
				page.getCurrentEndResult()));
		return page;
	}


	/**
	 * 获取请假流程详情
	 * 
	 * @param id
	 *            业务ID
	 * @return
	 */
	public Leave getLeaveDetail(String id) {
		Leave leave = selectById(id);
		workFlowService.setWorkFlowEntity(leave);
		return leave;
	}

	/**
	 * 完成任务
	 * 
	 * @param leave
	 *            业务实体
	 */
	public void completeTask(Leave leave) {
		Task task = null;
		if(StringUtils.isNotBlank(leave.getComment())){
			task = workFlowService.completeCurrentTask(
					leave.getProcessInstanceId(), leave.getComment(), leave.getVariable());
		}else{
			task = workFlowService.completeCurrentTask(
					leave.getProcessInstanceId(), leave.getVariable());
		}
		if (task == null) {
			leave.setProcessStatus("已完成");
		} else {
			leave.setProcessStatus(task.getName());
		}
		leaveDao.update(leave);
	}
}
