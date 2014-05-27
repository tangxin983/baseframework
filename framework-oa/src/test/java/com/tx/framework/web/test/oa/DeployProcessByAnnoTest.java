package com.tx.framework.web.test.oa;

import static org.junit.Assert.assertEquals;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:oa/springTypicalUsageTest-context.xml")
public class DeployProcessByAnnoTest {
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	@Rule
	public ActivitiRule activitiSpringRule;

	@Test
	@Deployment(resources = {"oa/simple.bpmn20.xml"})
	public void simpleProcessTest() {
		runtimeService.startProcessInstanceByKey("simpleProcess");
		Task task = taskService.createTaskQuery().singleResult();
		assertEquals("My Task", task.getName());

		taskService.complete(task.getId());
		assertEquals(0, runtimeService.createProcessInstanceQuery().count());

	}
}
