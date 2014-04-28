package com.tx.framework.web.modules.sys.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tx.framework.web.common.controller.BaseControllerNew;
import com.tx.framework.web.modules.sys.entity.Menu;
import com.tx.framework.web.modules.sys.service.MenuService;

@Controller
@RequestMapping(value = "/sys/menu")
public class MenuController extends BaseControllerNew<Menu, String> {

	private MenuService menuService;

	@Autowired
	public void setMenuService(MenuService menuService) {
		super.setService(menuService);
		this.menuService = menuService;
	}

	@RequestMapping
	public String view(Model model) {
		List<Menu> sourcelist = menuService.findAllMenuBySort(null);
		List<Menu> list = Lists.newArrayList();
		sortMenuList(list, sourcelist, "1");
		model.addAttribute("entitys", list);
		setModelAttr(model);
		return getListPage();
	}

	/**
	 * 对原始list进行整理以适合treetable要求
	 * 
	 * @param list
	 * @param sourcelist
	 * @param parentId
	 */
	private void sortMenuList(List<Menu> list, List<Menu> sourcelist,
			String parentId) {
		for (int i = 0; i < sourcelist.size(); i++) {
			Menu e = sourcelist.get(i);
			if (e.getParentId().equals(parentId)) {
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j = 0; j < sourcelist.size(); j++) {
					Menu child = sourcelist.get(j);
					if (child.getParentId().equals(e.getId())) {
						sortMenuList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Menu menu, Model model) {
		// 如果没有传入父菜单id，则默认父菜单是顶级菜单
		if (menu == null) {
			menu = new Menu();
		}
		if (StringUtils.isBlank(menu.getParentId())) {
			menu.setParentId("1");
		}
		// 设置父菜单名称
		Menu parent = menuService.selectById(menu.getParentId());
		if (parent != null) {
			menu.setParentName(parent.getName());
		}
		model.addAttribute("entity", menu);
		model.addAttribute("action", "create");
		setModelAttr(model);
		return getCreateFormPage();
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@Override
	public String create(@Valid Menu entity,
			RedirectAttributes redirectAttributes) {
		menuService.saveMenu(entity);
		addMessage(redirectAttributes, "保存菜单'" + entity.getName() + "'成功");
		return "redirect:/" + getControllerContext();
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	@Override
	public String updateForm(@PathVariable("id") String id, Model model) {
		Menu menu = menuService.selectById(id);
		if (menu != null) {
			Menu parent = menuService.selectById(menu.getParentId());
			if (parent != null) {
				menu.setParentName(parent.getName());
			}
		}
		model.addAttribute("entity", menu);
		model.addAttribute("action", "update");
		setModelAttr(model);
		return getUpdateFormPage();
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@Override
	public String update(@Valid @ModelAttribute("entity") Menu entity,
			RedirectAttributes redirectAttributes) {
		menuService.updateMenu(entity);
		addMessage(redirectAttributes, "更新菜单'" + entity.getName() + "'成功");
		return "redirect:/" + getControllerContext();
	}

	@RequestMapping("delete/{id}")
	@Override
	public String delete(@PathVariable("id") String id,
			RedirectAttributes redirectAttributes) {
		menuService.deleteMenu(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:/" + getControllerContext();
	}

	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(
			@RequestParam(required = false) String extId) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = menuService.findAllMenuBySort(null);
		for (Menu e : list) {
			// 排除extId及其子菜单
			if (extId == null || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

}
