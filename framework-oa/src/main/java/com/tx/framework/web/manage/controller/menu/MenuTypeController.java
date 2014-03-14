package com.tx.framework.web.manage.controller.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.common.controller.BaseController;
import com.tx.framework.web.common.persistence.entity.MenuType;
import com.tx.framework.web.common.persistence.entity.RemoteMsg;
import com.tx.framework.web.manage.service.menu.MenuTypeService;




@Controller
@RequestMapping(value = "/menuType")
public class MenuTypeController extends BaseController<MenuType>{

	private MenuTypeService menuTypeService;
	
	@Autowired
	public void setMenuTypeService(MenuTypeService menuTypeService) {
		super.setService(menuTypeService);
		this.menuTypeService = menuTypeService;
	}
	
	/**
	 * 展示分类页面
	 * @return
	 */
	@RequestMapping
	public String view(Model model) {
		model.addAttribute("menuTypeList", menuTypeService.getAllEntity());
		return getControllerContext() + "/" + getListPageName();
	}
	 
	/**
	 * 菜单分类树
	 * @param noRoot 1:无根节点;0:有根节点
	 * @return
	 */
	@RequestMapping("tree")
	@ResponseBody
	public List<Map<String, Object>> treeList(@RequestParam(value = "noRoot", defaultValue = "") String noRoot) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("noRoot", noRoot);
        for(MenuType type : menuTypeService.getEntityByParams(paraMap)){
        	Map<String, Object> node = new HashMap<String, Object>();
        	node.put("id", type.getId());
        	node.put("pId", type.getParentId());
        	node.put("name", type.getMenuTypeName());
        	list.add(node);
        }
        return list;
	}
	
	@RequestMapping(value = "checkName")
	@ResponseBody
	public RemoteMsg checkName(@RequestParam("menuTypeName") String menuTypeName, 
			@RequestParam("id") String id) {
		RemoteMsg msg = new RemoteMsg();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("menuTypeName", menuTypeName);
		searchParams.put("id", id);
		if(menuTypeService.getCountByParams(searchParams) == 0){
			msg.setSuccess("true");
		} else {
			msg.setSuccess("false");
			msg.setMsg("此名称已存在");
		}
		return msg;
	}

	
}
