package com.tx.framework.web.modules.oa.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.common.util.Servlets;
import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.controller.BaseWorkFlowController;
import com.tx.framework.web.common.persistence.entity.Leave;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.common.utils.ShiroUtil;
import com.tx.framework.web.modules.oa.service.LeaveService;

/**
 * 请假Controller
 * @author tangx
 * @since 2014-05-28
 */
@Controller
@RequestMapping(value = "oa/leave")
public class LeaveController extends BaseWorkFlowController<Leave, String> {

	private LeaveService leaveService;

	@Autowired
	public void setLeaveService(LeaveService leaveService) {
		super.setService(leaveService);
		this.leaveService = leaveService;
	}
	
	/**
	 * 流程实例列表
	 */
	@RequestMapping
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			Model model, HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		setExtraSearchParam(searchParams);
		Page<Leave> entitys = leaveService.findLeaveInstanceByPage(searchParams, pageNumber, pageSize, false);
		model.addAttribute("page", entitys);
		// 将搜索条件编码成字符串，用于分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return getListPage();
	}
	
	/**
	 * 待办列表
	 */
	@RequestMapping("task")
	public String todoTask(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			Model model, HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		setExtraSearchParam(searchParams);
		Page<Leave> entitys = leaveService.findTodoTaskByPage(searchParams, pageNumber, pageSize);
		model.addAttribute("page", entitys);
		// 将搜索条件编码成字符串，用于分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return "modules/oa/taskList";
	}
	
	/**
     * 签收任务
     */
    @RequestMapping("task/claim/{id}")
    public String claim(@PathVariable("id") String taskId, RedirectAttributes redirectAttributes) {
        super.claim(taskId, ShiroUtil.getCurrentUserId(), redirectAttributes);
        return "redirect:/oa/leave/task";
    }
    
    /**
     * 流程详情页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") String id, Model model) {
    	model.addAttribute("entity", leaveService.getLeaveDetail(id));
		return "modules/oa/leaveDetail";
	}
    
    /**
     * 部门领导审核
     * @param leave
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "deptLeaderAudit", method = RequestMethod.POST)
	public String deptLeaderAudit(@Valid @ModelAttribute("entity")Leave leave, RedirectAttributes redirectAttributes) {
		leaveService.deptLeaderAudit(leave);
		addMessage(redirectAttributes, "审批成功");
		return "redirect:/oa/leave/task";
	}
    
    /**
     * 人事审核
     * @param leave
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "hrAudit", method = RequestMethod.POST)
	public String hrAudit(@Valid @ModelAttribute("entity")Leave leave, RedirectAttributes redirectAttributes) {
		leaveService.hrAudit(leave);
		addMessage(redirectAttributes, "审批成功");
		return "redirect:/oa/leave/task";
	}
    
    /**
     * 销假
     * @param leave
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "reportBack")
	public String reportBack(Leave leave, RedirectAttributes redirectAttributes) {
		leaveService.reportBack(leave);
		addMessage(redirectAttributes, "销假成功");
		return "redirect:/oa/leave/task";
	}
	
	/**
	 * 跳转新增页面<br>
	 * url:oa/leave/create
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		return super.createForm(model);
	}

	/**
	 * 新增操作<br>
	 * url:oa/leave/create
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Leave entity,
			RedirectAttributes redirectAttributes) {
		ProcessInstance processInstance = leaveService.saveLeave(entity);
		addMessage(redirectAttributes, "流程已启动，流程ID：" + processInstance.getId());
		return "redirect:/" + getControllerContext();
	}
	
	/**
	 * 跳转更新页面<br>
	 * URL:oa/leave/update/{id}
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		return super.updateForm(id, model);
	}
	
	/**
	 * 更新操作<br>
	 * URL:oa/leave/update
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")Leave entity, RedirectAttributes redirectAttributes) {
		return super.update(entity, redirectAttributes);
	}
	
	/**
	 * 删除操作<br>
	 * URL:oa/leave/delete/{id}
	 */
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		return super.delete(id, redirectAttributes);
	}
	
	/**
	 * 批量删除操作<br>
	 * URL:oa/leave/delete
	 */
	@RequestMapping("delete")
	public String multiDelete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		return super.multiDelete(ids, redirectAttributes);
	}
	
	/**
	 * 根据id查找实体（json）<br>
	 * URL:oa/leave/get/{id}
	 */
	@RequestMapping("get/{id}")
	@ResponseBody
	public Leave get(@PathVariable("id") String id) {
		return super.get(id);
	}

}
