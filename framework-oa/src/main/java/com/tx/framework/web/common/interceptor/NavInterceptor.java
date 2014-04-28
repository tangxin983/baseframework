package com.tx.framework.web.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tx.framework.web.common.utils.ShiroUtil;
import com.tx.framework.web.modules.sys.entity.Menu;

public class NavInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String pId = "";
		// 去掉上下文路径后进行匹配
		String path = request.getRequestURI().substring(request.getContextPath().length());
		List<Menu> menuList = ShiroUtil.getMenus();
		// 找到对应路径的菜单id
		if (menuList != null) {
			for (Menu menu : menuList) {
				if (menu.getHref().equals(path)) {
					pId = menu.getParentId();
					modelAndView.addObject("rId", menu.getId());
					System.out.println(menu.getId());
				}
			}
			// 找到对应的父菜单id
			if (!pId.equals("")) {
				for (Menu menu : menuList) {
					if (menu.getId().equals(pId)) {
						modelAndView.addObject("prId", menu.getId());
						System.out.println(menu.getId());
					}
				}
			}
		}

	}

}
