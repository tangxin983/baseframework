package com.tx.framework.web.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tx.framework.web.common.persistence.entity.Menu;
import com.tx.framework.web.common.utils.ShiroUtil;

/**
 * 用于定位边栏菜单及面包屑导航的拦截器
 * 
 * @author tangx
 * 
 */
public class NavInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String pMenuId = "", pMenuName = "", menuId = "", menuName = "";
		// 去掉上下文路径后进行匹配
		String path = request.getRequestURI().substring(request.getContextPath().length());
		List<Menu> navList = ShiroUtil.getNavs();
		if (navList != null && !navList.isEmpty()) {
			for (Menu menu : navList) {
				if (menu.getHref().equals(path)) {
					if (menu.getIsShow().equals("1")) {
						// 可见则直接选中
						menuId = menu.getId();
						menuName = menu.getName();
						pMenuId = menu.getParent().getId();
						pMenuName = menu.getParent().getName();
					}else{
						// 隐藏则选择父菜单
						menuId = menu.getParent().getId();
						menuName = menu.getParent().getName();
						pMenuId = menu.getParent().getParent().getId();
						pMenuName = menu.getParent().getParent().getName();
					}
					break;
				}
			}
		}
		modelAndView.addObject("rId", menuId);
		modelAndView.addObject("rName", menuName);
		modelAndView.addObject("prId", pMenuId);
		modelAndView.addObject("prName", pMenuName);
	}

}
