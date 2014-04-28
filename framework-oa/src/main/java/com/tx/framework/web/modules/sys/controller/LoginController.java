package com.tx.framework.web.modules.sys.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.common.util.CaptchaUtils;
import com.tx.framework.web.common.utils.ShiroUtil;
import com.tx.framework.web.modules.sys.security.CaptchaAuthenticationFilter;

/**
 * 登录管理
 * 
 */
@Controller
public class LoginController {

	/**
	 * 判断是否登录，如果未登录则跳转到登录页否则跳转到index页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login() {
		if (ShiroUtil.isAuthenticated() || ShiroUtil.isRemembered()) {
			return "redirect:/index";
		} else {
			return "account/login";
		}
	}

	/**
	 * shiro登录失败时会post到这个方法，为了用户友好性考虑需要把之前输入的用户名重新展示出来
	 * 
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginFail(@RequestParam("username") String userName,
			Model model) {
		if (ShiroUtil.isAuthenticated() || ShiroUtil.isRemembered()) {
			return "redirect:/index";
		}
		model.addAttribute("username", userName);
		return "account/login";
	}

	/**
	 * 生成验证码
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getCaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_GIF_VALUE)
	@ResponseBody
	public byte[] getCaptcha() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String captcha = CaptchaUtils.getGifCaptcha(80, 32, 4, outputStream, 1000).toLowerCase();
		ShiroUtil.setAttribute(CaptchaAuthenticationFilter.DEFAULT_CAPTCHA_PARAM, captcha);
		return outputStream.toByteArray();
	}

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "modules/sys/index";
	}

}
