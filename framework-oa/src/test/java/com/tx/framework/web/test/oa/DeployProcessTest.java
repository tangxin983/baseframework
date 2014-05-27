package com.tx.framework.web.test.oa;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:oa/springTypicalUsageTest-context.xml")
public class DeployProcessTest {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Test
	public void simpleProcessTest() {
		// 发布流程定义
		repositoryService.createDeployment()
				.addClasspathResource("oa/VacationRequest.bpmn20.xml").deploy();
		System.out.println("Number of process definitions: "
				+ repositoryService.createProcessDefinitionQuery().count());
		// 启动流程实例
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");
		runtimeService.startProcessInstanceByKey("vacationRequest", variables);
		System.out.println("Number of process instances: "
				+ runtimeService.createProcessInstanceQuery().count());
		// 获取management用户组任务列表
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateGroup("management").list();
		for (Task task : tasks) {
			System.out.println("Task available: " + task.getName());
		}

		// 用户完成任务
		Task task = tasks.get(0);
		Map<String, Object> taskVariables = Maps.newHashMap();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("managerMotivation", "We have a tight deadline!");
		taskService.complete(task.getId(), taskVariables);
	}
}
