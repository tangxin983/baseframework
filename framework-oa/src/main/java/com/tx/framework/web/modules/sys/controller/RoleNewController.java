package com.tx.framework.web.modules.sys.controller;

import javax.validation.Valid;

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

import com.tx.framework.web.common.controller.BaseControllerNew;
import com.tx.framework.web.common.persistence.entity.RemoteMsg;
import com.tx.framework.web.modules.sys.entity.Role;
import com.tx.framework.web.modules.sys.service.MenuService;
import com.tx.framework.web.modules.sys.service.RoleNewService;

@Controller
@RequestMapping(value = "/sys/role")
public class RoleNewController extends BaseControllerNew<Role, String> {

	@Autowired
	private MenuService menuService;

	private RoleNewService roleNewService;

	@Autowired
	public void setRoleNewService(RoleNewService roleNewService) {
		super.setService(roleNewService);
		this.roleNewService = roleNewService;
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("menuList", menuService.findAllMenuBySort(null));
		return super.createForm(model);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Role entity,
			RedirectAttributes redirectAttributes) {
		roleNewService.saveRole(entity);
		addMessage(redirectAttributes, "添加角色" + entity.getName() + "成功");
		return "redirect:/" + getControllerContext() + "/b";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("menuList", menuService.findAllMenuBySort(null));
		Role role = service.selectById(id);
		role.setMenuIds(roleNewService.findRoleMenu(id));
		model.addAttribute("entity", role);
		model.addAttribute("action", "update");
		setModelAttr(model);
		return getUpdateFormPage();
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")Role entity, RedirectAttributes redirectAttributes) {
		roleNewService.updateRole(entity);
		addMessage(redirectAttributes, "更新角色" + entity.getName() + "成功");
		return "redirect:/" + getControllerContext() + "/b";
	}

	@RequestMapping(value = "checkName")
	@ResponseBody
	public RemoteMsg checkName(@RequestParam("oldName") String oldName,
			@RequestParam("name") String name) {
		RemoteMsg msg = new RemoteMsg();
		if (name != null && name.equals(oldName)) {
			msg.setSuccess("true");
		} else if (name != null && roleNewService.findRoleByName(name).isEmpty()) {
			msg.setSuccess("true");
		} else {
			msg.setSuccess("false");
			msg.setMsg("角色名称已存在");
		}
		return msg;
	}

}
