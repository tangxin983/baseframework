package com.tx.framework.web.manage.controller.user;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.common.mapper.JsonMapper;
import com.tx.framework.web.common.controller.AjaxPaginationController;
import com.tx.framework.web.common.persistence.entity.RemoteMsg;
import com.tx.framework.web.common.persistence.entity.User;
import com.tx.framework.web.manage.service.role.RoleService;
import com.tx.framework.web.manage.service.user.UserService;

 
@Controller
@RequestMapping(value = "/user")
public class UserController extends AjaxPaginationController<User> {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	 
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		super.setService(userService);
		this.userService = userService;
	}
	
	@Autowired
	private RoleService roleService;
 
	
	/**
	 * 用户列表页
	 */
	@RequestMapping
	@Override
	public String list(Model model, ServletRequest request) {
		model.addAttribute("roles", roleService.getAllEntity());
		return super.list(model, request);
	}
	
	/**
	 * 创建用户
	 * @param entity
	 * @param roleIds
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "createUser", method = RequestMethod.POST)
	public String createUser(@Valid User entity, 
			@RequestParam(value="roleIds", defaultValue="") List<String> roleIds,
			RedirectAttributes redirectAttributes) {
		userService.saveUser(entity, roleIds);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/" + getControllerContext();
	}
	
	/**
	 * 更新用户
	 * @param entity
	 * @param roleIds
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	public String updateUser(@Valid @ModelAttribute("entity")User entity, 
			@RequestParam(value="roleIds", defaultValue="") List<String> roleIds,
			RedirectAttributes redirectAttributes) {
		userService.updateUser(entity, roleIds);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/" + getControllerContext();
	}
	
	@RequestMapping("delete")
	@Override
	public String multiDelete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		userService.MultiDeleteUser(ids);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/" + getControllerContext();
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("loginName") String loginName) {
		RemoteMsg msg = new RemoteMsg();
		if (userService.findUserByLoginName(loginName) == null) {
			msg.setSuccess("true");
		} else {
			msg.setSuccess("false");
			msg.setMsg("此账号已存在");
		}
		return JsonMapper.nonDefaultMapper().toJson(msg);
	}
	 
}
