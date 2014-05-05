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
import com.tx.framework.web.common.persistence.entity.Role;
import com.tx.framework.web.modules.sys.service.MenuService;
import com.tx.framework.web.modules.sys.service.RoleService;

@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController<Role, String> {

	@Autowired
	private MenuService menuService;

	private RoleService roleService;

	@Autowired
	public void setRoleService(RoleService roleService) {
		super.setService(roleService);
		this.roleService = roleService;
	}

	/**
	 * 跳转角色新增页面
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("menuList", menuService.findAllMenuBySort(null));
		return super.createForm(model);
	}

	/**
	 * 新增角色
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Role entity,
			RedirectAttributes redirectAttributes) {
		roleService.saveRole(entity);
		addMessage(redirectAttributes, "添加角色" + entity.getName() + "成功");
		return "redirect:/" + getControllerContext() + "/b";
	}
	
	/**
	 * 跳转角色更新页面
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("menuList", menuService.findAllMenuBySort(null));
		Role role = service.selectById(id);
		role.setMenuIds(roleService.findRoleMenu(id));
		model.addAttribute("entity", role);
		model.addAttribute("action", "update");
		setModelAttr(model);
		return getUpdateFormPage();
	}
	
	/**
	 * 更新角色
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")Role entity, RedirectAttributes redirectAttributes) {
		roleService.updateRole(entity);
		addMessage(redirectAttributes, "更新角色" + entity.getName() + "成功");
		return "redirect:/" + getControllerContext() + "/b";
	}
	
	/**
	 * 删除角色
	 */
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		if(roleService.isDelete(id)){
			roleService.deleteRole(id);
			addMessage(redirectAttributes, "删除成功");
		}else{
			addMessage(redirectAttributes, "无法删除已关联用户的角色");
		}
		return "redirect:/" + getControllerContext() + "/b";
	}

	/**
	 * 检查角色名称是否已经存在
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "checkName")
	@ResponseBody
	public Map<String, String> checkName(@RequestParam("oldName") String oldName,
			@RequestParam("name") String name) {
		Map<String, String> msg = Maps.newHashMap();
		if (name != null && name.equals(oldName)) {
			msg.put("success", "true");
		} else if (name != null && roleService.findRoleByName(name).isEmpty()) {
			msg.put("success", "true");
		} else {
			msg.put("success", "false");
			msg.put("msg", "角色名称已存在");
		}
		return msg;
	}

}
