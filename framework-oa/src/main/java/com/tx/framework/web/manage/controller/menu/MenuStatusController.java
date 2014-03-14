package com.tx.framework.web.manage.controller.menu;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tx.framework.web.common.controller.AjaxPaginationController;
import com.tx.framework.web.common.persistence.entity.Menu;
import com.tx.framework.web.manage.service.menu.MenuService;
import com.tx.framework.web.manage.service.menu.MenuTypeService;




@Controller
@RequestMapping(value = "/menuStatus")
public class MenuStatusController extends AjaxPaginationController<Menu>{

	private MenuService menuService;
	
	@Autowired
	public void setMenuService(MenuService menuService) {
		super.setService(menuService);
		this.menuService = menuService;
	}
	
	@Autowired
	private MenuTypeService menuTypeService;
	
	@RequestMapping
	@Override
	public String list(Model model, ServletRequest request) {
		model.addAttribute("menuTypeList", menuTypeService.getAllEntity());
		return super.list(model, request);
	}
	
}
