package com.tx.framework.web.modules.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.controller.BaseController;
import com.tx.framework.web.common.persistence.entity.Area;
import com.tx.framework.web.modules.sys.service.AreaService;

/**
 * 区域Controller
 * @author tangx
 * @version 2014-05-08
 */
@Controller
@RequestMapping(value = "sys/area")
public class AreaController extends BaseController<Area, String> {

	private AreaService areaService;

	@Autowired
	public void setAreaService(AreaService areaService) {
		super.setService(areaService);
		this.areaService = areaService;
	}
	
	// ========== 以下为简单crud示例。注意：一旦修改url，对应生成的视图url也需手动修改 ===========
	/**
	 * 跳转列表页（分页）<br>
	 * url:sys/area
	 */
	@RequestMapping
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			Model model, HttpServletRequest request) {
		return super.list(pageNumber, pageSize, model, request);
	}
	
	/**
	 * 跳转新增页面<br>
	 * url:sys/area/create
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		return super.createForm(model);
	}

	/**
	 * 新增操作<br>
	 * url:sys/area/create
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Area entity,
			RedirectAttributes redirectAttributes) {
		return super.create(entity, redirectAttributes);
	}
	
	/**
	 * 跳转更新页面<br>
	 * URL:sys/area/update/{id}
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		return super.updateForm(id, model);
	}
	
	/**
	 * 更新操作<br>
	 * URL:sys/area/update
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")Area entity, RedirectAttributes redirectAttributes) {
		return super.update(entity, redirectAttributes);
	}
	
	/**
	 * 删除操作<br>
	 * URL:sys/area/delete/{id}
	 */
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		return super.delete(id, redirectAttributes);
	}
	
	/**
	 * 批量删除操作<br>
	 * URL:sys/area/delete
	 */
	@RequestMapping("delete")
	public String multiDelete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		return super.multiDelete(ids, redirectAttributes);
	}
	
	/**
	 * 根据id查找实体（json）<br>
	 * URL:sys/area/get/{id}
	 */
	@RequestMapping("get/{id}")
	@ResponseBody
	public Area get(@PathVariable("id") String id) {
		return super.get(id);
	}

}
