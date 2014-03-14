package com.tx.framework.web.manage.controller.role;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.controller.AjaxPaginationController;
import com.tx.framework.web.common.persistence.entity.Role;
import com.tx.framework.web.manage.service.resource.ResourceService;
import com.tx.framework.web.manage.service.role.RoleService;


@Controller
@RequestMapping(value = "/role")
public class RoleController extends AjaxPaginationController<Role> {

	private RoleService roleService;
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		super.setService(roleService);
		this.roleService = roleService;
	}
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	@Override
	public String createForm(Model model) {
		model.addAttribute("resources", resourceService.getAllEntity());
		model.addAttribute("action", "createRole");
		return "role/roleForm";
	}

	@RequestMapping(value = "createRole", method = RequestMethod.POST)
	public String create(@Valid Role newRole, 
			@RequestParam(value="resourceId",required=false) List<String> resourceIds, 
			RedirectAttributes redirectAttributes) {
		roleService.saveRole(newRole, resourceIds);
		redirectAttributes.addFlashAttribute("message", "创建角色成功");
		return "redirect:/role";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	@Override
	public String updateForm(@PathVariable("id") String id, Model model) {
		Role role = roleService.getEntity(id);
		List<String> selectedResources = CollectionUtils.extractToList(role.getResources(), "id", true);
		model.addAttribute("role", role);
		model.addAttribute("resources", resourceService.getAllEntity());
		model.addAttribute("selectedResources", selectedResources);
		model.addAttribute("action", "updateRole");
		return "role/roleForm";
	}

	@RequestMapping(value = "updateRole", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("role") Role role, 
			@RequestParam(value="resourceId",required=false) List<String> resourceIds,
			RedirectAttributes redirectAttributes) {
		roleService.updateRole(role, resourceIds);;
		redirectAttributes.addFlashAttribute("message", "更新角色成功");
		return "redirect:/role";
	}
	
	@RequestMapping("delete")
	@Override
	public String multiDelete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		if(roleService.isDeleteRole(ids)){
			roleService.deleteRoles(ids);
			redirectAttributes.addFlashAttribute("message", "删除" + ids.size() + "条记录成功");
		}else{
			redirectAttributes.addFlashAttribute("message", "只能删除未关联用户的角色数据");
		}
		return "redirect:/" + getControllerContext();
	}
}
