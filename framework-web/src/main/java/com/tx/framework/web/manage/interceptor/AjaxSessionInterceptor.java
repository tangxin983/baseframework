package com.tx.framework.web.manage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tx.framework.web.common.utils.ShiroUtil;



/**
 * ajax session过期拦截器
 * @author tangx
 *
 */
public class AjaxSessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (request.getHeader("x-requested-with") != null
				&& "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			if(ShiroUtil.isSessionTimeOut()){
				response.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
				
				response.setHeader("loginPath", request.getContextPath() + "/login");//在响应头设置登录路径
                return false;  
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
