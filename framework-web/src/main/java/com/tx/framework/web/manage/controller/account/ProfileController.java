package com.tx.framework.web.manage.controller.account;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.framework.web.common.utils.ShiroUtil;
import com.tx.framework.web.entity.User;
import com.tx.framework.web.manage.service.user.UserService;



/**
 * 用户修改自己资料的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String updateForm(Model model) {
		String id = ShiroUtil.getCurrentUserId();
		model.addAttribute("user", userService.getEntity(id));
		return "account/profile";
	}

	/**
	 * 必须使用@ModelAttribute来绑定领域对象
	 * @param user
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("user") User user) {
		userService.updateUser(user, null);
		ShiroUtil.updateCurrentUserName(user.getUserName());
		return "redirect:/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 * 下面2种写法都可以
	 */
//	@ModelAttribute
//	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
//		if (id != -1) {
//			model.addAttribute("user", accountService.getUser(id));
//		}
//	}
	
	@ModelAttribute("user")
	public User getUser(@RequestParam(value = "id", defaultValue = "-1") String id) {
		if (!id.equals("-1")) {
			return userService.getEntity(id);
		}else{
			return null;
		}
	}
}
