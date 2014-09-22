package com.tx.framework.web.manage.controller.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tx.framework.web.common.base.AjaxPaginationController;
import com.tx.framework.web.common.utils.Servlets;
import com.tx.framework.web.entity.MenuImagemap;
import com.tx.framework.web.entity.MenuLayout;
import com.tx.framework.web.manage.service.menu.MenuLayoutService;

@Controller
@RequestMapping(value = "/menuLayout")
public class MenuLayoutController extends AjaxPaginationController<MenuLayout> {

	private MenuLayoutService menuLayoutService;

	@Autowired
	public void setMenuLayoutService(MenuLayoutService menuLayoutService) {
		super.setService(menuLayoutService);
		this.menuLayoutService = menuLayoutService;
	}
	 
	
	/**
	 * 获取分页table body
	 */
	@RequestMapping(value = "getPageContent")
	@ResponseBody
	public String view(Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		List<MenuLayout> entitys = menuLayoutService.getEntityByParams(searchParams);
		return menuLayoutService.getPageContent(entitys);
	}
	
	/**
	 * 获取排版信息
	 * @param menuLayoutId
	 * @return
	 */
	@RequestMapping(value = "getLayout/{menuLayoutId}")
	@ResponseBody
	public Map<String, Object> getLayout(@PathVariable("menuLayoutId") String menuLayoutId) {
		return menuLayoutService.getLayoutInfo(menuLayoutId);
	}
	
	/**
	 * 获取热区信息
	 * @param menuLayoutId
	 * @return
	 */
	@RequestMapping(value = "getImgMap/{menuLayoutId}/{coord}")
	@ResponseBody
	public MenuImagemap getImgMap(@PathVariable("menuLayoutId") String menuLayoutId,
			@PathVariable("coord") String coord) {
		return menuLayoutService.getImgMapInfo(menuLayoutId, coord);
	}
	
	/**
	 * 保存排版
	 * @param entity 排版对象
	 */
	@RequestMapping(value = "createLayout")
	@ResponseBody
	public Map<String, Object> createLayout(@Valid MenuLayout entity) {
		menuLayoutService.saveEntity(entity);
		MenuLayout newEntity = menuLayoutService.getEntity(entity.getId());
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("layoutId", newEntity.getId());
		ret.put("pageNo", newEntity.getPageNo());
		return ret;
	}
	
	/**
	 * 删除排版
	 * @param id
	 */
	@RequestMapping(value = "deleteLayout/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteLayout(@PathVariable("id") String id) {
		menuLayoutService.deleteLayout(id);
	}
	
	/**
	 * 1、更新排版附件信息
	 * 2、保存热区信息
	 */
	@RequestMapping(value = "createImgMap")
	@ResponseBody
	public void createImgMap(@RequestParam("menuLayoutId") String menuLayoutId, 
			@RequestParam("mapCode") String mapCode, @RequestParam("metaFileInfo") String metaFileInfo,
			@RequestParam(value="menuId", required = false) String[] menuIds, 
			@RequestParam(value="coord", required = false) String[] coords) {
		menuLayoutService.saveImgMap(menuLayoutId, mapCode, metaFileInfo, menuIds, coords);
	}
	
}
