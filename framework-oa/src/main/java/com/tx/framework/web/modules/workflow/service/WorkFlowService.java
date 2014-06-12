package com.tx.framework.web.modules.workflow.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
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

	/**
	 * 分页获取流程列表
	 * 
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            每页数量
	 * @return
	 */
	public Page<Object[]> getProcessListByPage(int pageNumber, int pageSize) {
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
	 * 分页获取流程实例列表
	 * 
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            每页数量
	 * @return
	 */
	public Page<ProcessInstance> getInstanceListByPage(int pageNumber,
			int pageSize) {
		ProcessInstanceQuery processInstanceQuery = runtimeService
				.createProcessInstanceQuery();
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
	 * 启动流程实例
	 * @param entity 业务实体（需继承WorkFlowEntity）
	 * @param processDefinitionKey 业务流程key
	 * @param variables 流程变量（可为null）
	 * @return
	 */
	public ProcessInstance startWorkflow(WorkFlowEntity entity,
			String processDefinitionKey, Map<String, Object> variables) {
		String businessKey = ObjectUtils.toString(entity.getId());
		ProcessInstance processInstance = null;
		try {
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(ShiroUtil.getCurrentUserId());
			processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
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
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	public String getCurrentTaskName(String processInstanceId){
		return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult().getName();
	}

}
