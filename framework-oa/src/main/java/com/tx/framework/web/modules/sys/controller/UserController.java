package com.tx.framework.web.modules.sys.controller;

import java.util.Map;

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

import com.google.common.collect.Maps;
import com.tx.framework.web.common.controller.BaseController;
import com.tx.framework.web.modules.sys.entity.User;
import com.tx.framework.web.modules.sys.service.RoleService;
import com.tx.framework.web.modules.sys.service.UserService;

@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController<User, String> {
	
	@Autowired
	private RoleService roleService;

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		super.setService(userService);
		this.userService = userService;
	}
	
	/**
	 * 跳转到用户新增页面
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("roles", roleService.select());
		return super.createForm(model);
	}
	
	/**
	 * 新增用户
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid User entity,
			RedirectAttributes redirectAttributes) {
		userService.saveUser(entity);
		addMessage(redirectAttributes, "添加用户" + entity.getName() + "成功");
		return "redirect:/" + getControllerContext() + "/b";
	}
	
	/**
	 * 跳转到用户更新页面
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("roles", roleService.select());
		User user = service.selectById(id);
		user.setRoleIds(userService.findUserRole(id));
		model.addAttribute("entity", user);
		model.addAttribute("action", "update");
		setModelAttr(model);
		return getUpdateFormPage();
	}
	
	/**
	 * 更新用户
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")User entity, RedirectAttributes redirectAttributes) {
		userService.updateUser(entity);
		addMessage(redirectAttributes, "更新用户" + entity.getName() + "成功");
		return "redirect:/" + getControllerContext() + "/b";
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		if(!User.isAdmin(id)){
			userService.deleteUser(id);
			addMessage(redirectAttributes, "删除成功");
		}else{
			addMessage(redirectAttributes, "无法删除管理员账号");
		}
		return "redirect:/" + getControllerContext() + "/b";
	}
	
	/**
	 * 判断登录名是否已经存在
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "checkName")
	@ResponseBody
	public Map<String, String> checkName(@RequestParam("oldName") String oldName,
			@RequestParam("loginName") String name) {
		Map<String, String> msg = Maps.newHashMap();
		if (name != null && name.equals(oldName)) {
			msg.put("success", "true");
		} else if (name != null && userService.findUserByLoginName(name) == null) {
			msg.put("success", "true");
		} else {
			msg.put("success", "false");
			msg.put("msg", "登录名已存在");
		}
		return msg;
	}
}
