package com.tx.framework.web.manage.controller.menu;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.common.controller.AjaxPaginationController;
import com.tx.framework.web.common.persistence.entity.Menu;
import com.tx.framework.web.common.persistence.entity.RemoteMsg;
import com.tx.framework.web.manage.service.menu.MenuService;
import com.tx.framework.web.manage.service.menu.MenuTypeService;




@Controller
@RequestMapping(value = "/menu")
public class MenuController extends AjaxPaginationController<Menu>{

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
 
	@RequestMapping(value = "checkName")
	@ResponseBody
	public RemoteMsg checkCode(@RequestParam("menuName") String menuName, 
			@RequestParam("id") String id) {
		RemoteMsg msg = new RemoteMsg();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("menuName", menuName);
		searchParams.put("id", id);
		if(menuService.getCountByParams(searchParams) == 0){
			msg.setSuccess("true");
		} else {
			msg.setSuccess("false");
			msg.setMsg("此名称已存在");
		}
		return msg;
	}
	
}
