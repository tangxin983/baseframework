package com.tx.framework.web.manage.controller.account;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.common.mapper.JsonMapper;
import com.tx.framework.web.entity.RemoteMsg;
import com.tx.framework.web.entity.User;
import com.tx.framework.web.manage.service.user.UserService;

/**
 * 用户注册的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String registerForm() { 
		return "account/register";
	}

	/**
	 * 虽然表单里有多余的confirmPassword字段(User实体没有)，但不会导致错误
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid User user, RedirectAttributes redirectAttributes) {
		//accountService.registerUser(user);
		redirectAttributes.addFlashAttribute("username", user.getLoginName());
		return "redirect:/login";
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
