package com.tx.framework.web.manage.controller.resource;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.web.common.base.AjaxPaginationController;
import com.tx.framework.web.entity.Resource;
import com.tx.framework.web.manage.service.resource.ResourceService;




@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends AjaxPaginationController<Resource>{

	private ResourceService resourceService;
	
	@Autowired
	public void setResourceService(ResourceService resourceService) {
		super.setService(resourceService);
		this.resourceService = resourceService;
	}
	
	@RequestMapping
	@Override
	public String list(Model model, ServletRequest request) {
		model.addAttribute("resourceList", resourceService.getAllEntity());
		return super.list(model, request);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@Override
	public String create(@Valid Resource newResource, RedirectAttributes redirectAttributes) {
		resourceService.saveResource(newResource);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/resource";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@Override
	public String update(@Valid Resource resource, RedirectAttributes redirectAttributes) {
		resourceService.updateResource(resource);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/resource";
	}

	@RequestMapping(value = "delete/{id}")
	@Override
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		resourceService.deleteResource(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/resource";
	}
	
	@RequestMapping("delete")
	@Override
	public String multiDelete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		resourceService.MultiDeleteResource(ids);
		redirectAttributes.addFlashAttribute("message", "删除" + ids.size() + "个资源成功");
		return "redirect:/resource";
	}
	
	/**
	 * 获取面包屑导航HTML代码
	 * @param path
	 * @return
	 */
	@RequestMapping("breadcrumb")
	@ResponseBody
	public String breadcrumb(@RequestParam("path")String path) {
		return resourceService.breadcrumb(path);
	}

}
