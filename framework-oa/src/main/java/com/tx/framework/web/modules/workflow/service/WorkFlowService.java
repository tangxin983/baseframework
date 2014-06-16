package com.tx.framework.web.modules.workflow.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.common.persistence.entity.WorkFlowEntity;
import com.tx.framework.web.common.utils.ShiroUtil;

/**
 * 工作流Service
 * 
 * @author tangx
 * @since 2014-06-12
 */
@Service
@Transactional
public class WorkFlowService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private HistoryService historyService;

	/**
	 * 分页获取流程列表
	 * 
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            每页数量
	 * @return
	 */
	public Page<Object[]> getPaginationProcess(int pageNumber, int pageSize) {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService
				.createProcessDefinitionQuery().orderByDeploymentId().desc();
		// 设置page对象
		Page<Object[]> page = new Page<Object[]>();
		page.setCurrentPage(pageNumber);
		page.setSize(pageSize);
		page.setTotal((int) processDefinitionQuery.count());
		// 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		List<Object[]> objects = Lists.newArrayList();
		List<ProcessDefinition> processDefinitionList = processDefinitionQuery
				.listPage(page.getCurrentResult(), pageSize);
		for (ProcessDefinition processDefinition : processDefinitionList) {
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery()
					.deploymentId(deploymentId).singleResult();
			objects.add(new Object[] { processDefinition, deployment });
		}
		page.setResult(objects);
		return page;
	}

	/**
	 * 分页获取流程实例
	 * 
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            每页数量
	 * @return
	 */
	public Page<ProcessInstance> getPaginationInstance(int pageNumber,
			int pageSize) {
		ProcessInstanceQuery processInstanceQuery = runtimeService
				.createProcessInstanceQuery().orderByProcessInstanceId().desc();
		// 设置page对象
		Page<ProcessInstance> page = new Page<ProcessInstance>();
		page.setCurrentPage(pageNumber);
		page.setSize(pageSize);
		page.setTotal((int) processInstanceQuery.count());
		page.setResult(processInstanceQuery.listPage(page.getCurrentResult(),
				pageSize));
		return page;
	}

	/**
	 * 根据流程定义key获取流程实例
	 * 
	 * @param processDefinitionKey
	 *            流程定义key
	 * @return
	 */
	public List<ProcessInstance> getInstanceListByDefKey(
			String processDefinitionKey) {
		ProcessInstanceQuery processInstanceQuery = runtimeService
				.createProcessInstanceQuery()
				.processDefinitionKey(processDefinitionKey)
				.orderByProcessInstanceId().desc();
		return processInstanceQuery.list();
	}

	/**
	 * 获取某个业务流程的流程实例
	 * 
	 * @param processDefinitionKey
	 *            流程定义key
	 * @param isActive
	 *            true代表只获取激活的实例；false代表只获取挂起的实例
	 * @return
	 */
	public List<ProcessInstance> getInstanceList(String processDefinitionKey,
			boolean isActive) {
		ProcessInstanceQuery processInstanceQuery = null;
		if (isActive) {
			processInstanceQuery = runtimeService.createProcessInstanceQuery()
					.processDefinitionKey(processDefinitionKey).active()
					.orderByProcessInstanceId().desc();
		} else {
			processInstanceQuery = runtimeService.createProcessInstanceQuery()
					.processDefinitionKey(processDefinitionKey).suspended()
					.orderByProcessInstanceId().desc();
		}
		return processInstanceQuery.list();
	}

	/**
	 * 获取用户某个业务流程下的待办任务
	 * 
	 * @param processDefinitionKey
	 *            流程定义key
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List<Task> getTodoTaskList(String processDefinitionKey, String userId) {
		List<Task> tasks = Lists.newArrayList();
		// 待办任务
		List<Task> todoList = taskService.createTaskQuery()
				.processDefinitionKey(processDefinitionKey)
				.taskAssignee(userId).active().list();
		// 未签收的任务
		List<Task> unsignedTasks = taskService.createTaskQuery()
				.processDefinitionKey(processDefinitionKey)
				.taskCandidateUser(userId).active().list();
		tasks.addAll(todoList);
		tasks.addAll(unsignedTasks);
		return tasks;
	}

	/**
	 * 启动流程实例
	 * 
	 * @param entity
	 *            业务实体（需继承WorkFlowEntity）
	 * @param processDefinitionKey
	 *            业务流程key
	 * @param variables
	 *            流程变量（可为null）
	 * @return
	 */
	public ProcessInstance startWorkflow(WorkFlowEntity entity,
			String processDefinitionKey, Map<String, Object> variables) {
		String businessKey = ObjectUtils.toString(entity.getId());
		ProcessInstance processInstance = null;
		try {
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService
					.setAuthenticatedUserId(ShiroUtil.getCurrentUserId());
			processInstance = runtimeService.startProcessInstanceByKey(
					processDefinitionKey, businessKey, variables);
			logger.debug(
					"start process of {key={}, bkey={}, pid={}, variables={}}",
					new Object[] { processDefinitionKey, businessKey,
							processInstance.getId(), variables });
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return processInstance;
	}

	/**
	 * 获得流程实例当前任务名
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return
	 */
	public String getCurrentTaskName(String processInstanceId) {
		return taskService.createTaskQuery()
				.processInstanceId(processInstanceId).singleResult().getName();
	}

	/**
	 * 获得流程实例
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return
	 */
	public ProcessInstance getProcessInstance(String processInstanceId) {
		return runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
	}

	/**
	 * 获得当前任务
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return
	 */
	public Task getCurrentTask(String processInstanceId) {
		return taskService.createTaskQuery()
				.processInstanceId(processInstanceId).singleResult();
	}

	/**
	 * 设置流程实体的流程相关属性
	 * 
	 * @param entity
	 */
	public void setWorkFlowEntity(WorkFlowEntity entity) {
		String processInstanceId = entity.getProcessInstanceId();
		// 实例信息
		entity.setProcessInstance(runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult());
		// 当前任务信息
		entity.setTask(taskService.createTaskQuery()
				.processInstanceId(processInstanceId).singleResult());
		// 历史任务信息（包括当前任务）
		List<HistoricTaskInstance> historicTaskInstances = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricTaskInstanceEndTime().asc().list();
		entity.setHistoricTaskInstances(historicTaskInstances);
	}

}
