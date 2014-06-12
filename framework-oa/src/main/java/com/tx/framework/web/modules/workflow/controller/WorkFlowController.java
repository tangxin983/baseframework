package com.tx.framework.web.modules.workflow.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.exception.ServiceException;
import com.tx.framework.web.modules.workflow.service.WorkFlowService;

/**
 * 工作流Controller
 * 
 * @author tangx
 * @since 2014-06-12
 */
@Controller
@RequestMapping(value = "workflow")
public class WorkFlowController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WorkFlowService workFlowListService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	/**
	 * 流程定义列表
	 * 
	 * @param pageNumber
	 *            当前页码
	 * @param pageSize
	 *            每页记录数
	 * @param model
	 * @return
	 */
	@RequestMapping("process/list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			Model model) {
		model.addAttribute("page",
				workFlowListService.getProcessListByPage(pageNumber, pageSize));
		return "modules/workflow/processList";
	}

	/**
	 * 读取部署资源(xml和png图片)
	 * 
	 * @param processDefinitionId
	 *            流程定义
	 * @param resourceType
	 *            资源类型(xml|image)
	 * @throws Exception
	 */
	@RequestMapping(value = "resource/read")
	public void readResource(
			@RequestParam("processId") String processDefinitionId,
			@RequestParam("type") String resourceType,
			HttpServletResponse response) {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		String resourceName = "";
		if (resourceType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}
		InputStream resourceAsStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		try {
			while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 挂起流程定义（挂起后不能再发起此流程定义的实例；所有流程实例也将挂起无法继续执行）
	 */
	@RequestMapping("process/suspend/{processId}")
	public String suspendProcess(
			@PathVariable("processId") String processDefinitionId,
			RedirectAttributes redirectAttributes) {
		repositoryService.suspendProcessDefinitionById(processDefinitionId,
				true, null);
		redirectAttributes.addFlashAttribute("message", "已挂起ID为["
				+ processDefinitionId + "]的流程定义。");
		return "redirect:/workflow/process/list";
	}

	/**
	 * 激活流程定义（同时激活所有流程实例）
	 */
	@RequestMapping("process/active/{processId}")
	public String activeProcess(
			@PathVariable("processId") String processDefinitionId,
			RedirectAttributes redirectAttributes) {
		repositoryService.activateProcessDefinitionById(processDefinitionId,
				true, null);
		redirectAttributes.addFlashAttribute("message", "已激活ID为["
				+ processDefinitionId + "]的流程定义。");
		return "redirect:/workflow/process/list";
	}

	/**
	 * 删除部署的流程，级联删除流程实例与历史数据
	 * 
	 * @param deploymentId
	 *            流程部署ID
	 */
	@RequestMapping(value = "process/delete/{deploymentId}")
	public String delete(@PathVariable("deploymentId") String deploymentId,
			RedirectAttributes redirectAttributes) {
		repositoryService.deleteDeployment(deploymentId, true);
		redirectAttributes.addFlashAttribute("message", "已删除部署ID为"
				+ deploymentId + "的流程。");
		return "redirect:/workflow/process/list";
	}

	/**
	 * 运行中的流程实例列表
	 * @param pageNumber 当前页码
	 * @param pageSize 每页记录数
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "instance/running")
	public String running(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			Model model) {
		model.addAttribute("page",
				workFlowListService.getInstanceListByPage(pageNumber, pageSize));
		return "modules/workflow/runInstanceList";
	}
}
