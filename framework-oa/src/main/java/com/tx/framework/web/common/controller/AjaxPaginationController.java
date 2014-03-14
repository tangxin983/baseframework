package com.tx.framework.web.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.common.util.Servlets;
import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.manage.freemarker.FreeMarkerResolver;

public abstract class AjaxPaginationController<T> extends BaseController<T> {
	
	
	@Autowired
	private FreeMarkerResolver freeMarkerResolver;
	
	
	/**
	 * 手动设置查询参数
	 * @param searchParams
	 */
	protected void setExtraSearchParam(Map<String, Object> searchParams){
		
	}

	/**
	 * 跳转list页面
	 * URL:/
	 * @param model
	 * @return 列表页
	 */
	@RequestMapping
	public String list(Model model, ServletRequest request) {
//		model.addAttribute("ajaxUrl", getControllerContext() + "/ajaxBody");
		model.addAttribute("templateUrl", getControllerContext() + "/template");
		model.addAttribute("module", getControllerContext());
		return getControllerContext() + "/" + getListPageName();
	}
	
	/**
	 * 使用指定模板构造分页表单体
	 * @param pageNumber 当前页码
	 * @param pageSize 每页记录数
	 * @param sortType 排序
	 * @param templateName 模板名
	 * @param request
	 * @return Page对象
	 */
	@RequestMapping(value = "template/{templateName}")
	@ResponseBody
	public Page<T> template(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, 
			@PathVariable("templateName") String templateName, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		setExtraSearchParam(searchParams);
		Page<T> entitys = service.getEntityByPage(searchParams, pageNumber, pageSize, sortType);
		this.buildTemplate(entitys, templateName);
		return entitys;
	}
	
	/**
	 * 使用默认模板"body"构造分页表单体
	 * @param pageNumber 当前页码
	 * @param pageSize 每页记录数
	 * @param sortType 排序
	 * @param request
	 * @return Page对象
	 */
	@RequestMapping(value = "template")
	@ResponseBody
	public Page<T> template(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, 
			ServletRequest request) {
		return template(pageNumber, pageSize, sortType, "body", request);
	}
	
	/**
	 * 使用模板生成HTML Tbody
	 * @param page Page对象
	 * @param templateName 模板名
	 */
	protected void buildTemplate(Page<T> page, String templateName){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("entitys", page.getResult());
		model.put("ctx", servletContext.getContextPath());
		model.put("module", getControllerContext());
		//要补足的行数
		model.put("supplementSize", Integer.valueOf(Constant.PAGINATION_SIZE) - page.getResult().size());
		page.setBody(freeMarkerResolver.mergeModelToTemlate(getControllerContext() + "/" + templateName + ".htm", model));
	}
	 
}
