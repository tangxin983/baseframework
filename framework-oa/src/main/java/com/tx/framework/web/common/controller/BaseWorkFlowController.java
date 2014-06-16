package com.tx.framework.web.common.controller;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 提供基础的crud和工作流控制
 * @author tangx
 *
 * @param <T>
 * @param <PK>
 */
public abstract class BaseWorkFlowController<T, PK>  extends BaseController<T, PK>{
	
	@Autowired
	protected TaskService taskService;
	
	/**
	 * 签收任务
	 * @param taskId 任务ID
	 * @param userId 用户ID
	 * @param redirectAttributes
	 */
    public void claim(String taskId, String userId, RedirectAttributes redirectAttributes) {
        taskService.claim(taskId, userId);
        addMessage(redirectAttributes, "任务已签收");
    }
}
