package com.tx.framework.web.modules.sys.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.web.common.controller.BaseControllerNew;
import com.tx.framework.web.modules.sys.entity.User;
import com.tx.framework.web.modules.sys.service.RoleNewService;
import com.tx.framework.web.modules.sys.service.UserNewService;

@Controller
@RequestMapping(value = "/sys/user")
public class UserNewController extends BaseControllerNew<User, String> {
	
	@Autowired
	private RoleNewService roleNewService;

	private UserNewService userNewService;

	@Autowired
	public void setUserNewService(UserNewService userNewService) {
		super.setService(userNewService);
		this.userNewService = userNewService;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("roles", roleNewService.select());
		return super.createForm(model);
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid User entity,
			RedirectAttributes redirectAttributes) {
		userNewService.saveUser(entity);
		addMessage(redirectAttributes, "添加用户" + entity.getName() + "成功");
		return "redirect:/" + getControllerContext() + "/b";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("roles", roleNewService.select());
		User user = service.selectById(id);
		user.setRoleIds(userNewService.findUserRole(id));
		model.addAttribute("entity", user);
		model.addAttribute("action", "update");
		setModelAttr(model);
		return getUpdateFormPage();
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")User entity, RedirectAttributes redirectAttributes) {
		userNewService.updateUser(entity);
		addMessage(redirectAttributes, "更新用户" + entity.getName() + "成功");
		return "redirect:/" + getControllerContext() + "/b";
	}
	/*
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
*/
}
