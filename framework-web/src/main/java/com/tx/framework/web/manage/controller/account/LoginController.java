package com.tx.framework.web.manage.controller.account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.tx.framework.common.util.CaptchaUtils;
import com.tx.framework.web.common.utils.ShiroUtil;
import com.tx.framework.web.entity.Resource;
import com.tx.framework.web.entity.ShiroEntity;
import com.tx.framework.web.exception.ServiceException;
import com.tx.framework.web.manage.freemarker.FreeMarkerResolver;
import com.tx.framework.web.manage.service.shiro.CaptchaAuthenticationFilter;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由shiroFilter完成
 * 
 */
@Controller
public class LoginController implements ServletContextAware{
	
	@Autowired
	private FreeMarkerResolver freeMarkerResolver;
	
	protected ServletContext servletContext;

    public void setServletContext(ServletContext context){
    	this.servletContext = context;
    }
	
	/**
	 * 判断是否登录，如果未登录则跳转到登录页否则跳转到index页
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login() {
		if(ShiroUtil.isAuthenticated() || ShiroUtil.isRemembered()){
			return "redirect:/index";
		}else{
			return "account/login";
		}
	}

	/**
	 * shiro登录失败时会post到这个方法，为了用户友好性考虑需要把之前输入的用户名重新展示出来
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginFail(@RequestParam("username") String userName, Model model) {
		if(ShiroUtil.isAuthenticated() || ShiroUtil.isRemembered()){
			return "redirect:/index";
		}
		model.addAttribute("username", userName);
		return "account/login";
	}
	
	/**
	 * 生成验证码
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getCaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_GIF_VALUE)
	@ResponseBody
	public byte[] getCaptcha(){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String captcha = CaptchaUtils.getGifCaptcha(80, 32, 4, outputStream, 1000).toLowerCase();
		ShiroUtil.setAttribute(CaptchaAuthenticationFilter.DEFAULT_CAPTCHA_PARAM,captcha);
	    return outputStream.toByteArray();
	}
	
	  
	/**
	 * 跳转功能页
	 * @param pid
	 * @param model
	 * @return
	 */
    @RequestMapping("/index")
    public String index(@RequestParam(value = "pid", required = false) String pid, Model model){
    	// 如果pid为空默认展示第一个功能页
    	if(StringUtils.isBlank(pid)){
    		ShiroEntity entity = (ShiroEntity)ShiroUtil.getAttribute("shiroEntity");
    		if(entity == null || entity.getMenus() == null || entity.getMenus().isEmpty()){
    			throw new ServiceException();
    		}
    		pid = entity.getMenus().get(0).getId(); 
    	}
    	model.addAttribute("pid", pid);
    	return "account/index";
    }
    
    /**
     * 功能页展示子模块
     * @param pid
     * @return
     */
    @RequestMapping("/module/{pid}")
    @ResponseBody
    public String module(@PathVariable("pid") String pid){
    	Map<String, Object> model = new HashMap<String, Object>();
		model.put("ctx", servletContext.getContextPath());
    	ShiroEntity entity = (ShiroEntity)ShiroUtil.getAttribute("shiroEntity");
    	for(Resource resource : entity.getMenus()){
    		if(resource.getId().equals(pid)){
    			model.put("entitys", resource.getChildren());
    			model.put("pModuleName", resource.getResourceName());
    		}
    	}
    	return freeMarkerResolver.mergeModelToTemlate("account/module.htm", model);
    }
}
