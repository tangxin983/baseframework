package com.tx.framework.web.common.controller;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;






import com.tx.framework.common.util.Servlets;
import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.persistence.entity.Page;

public abstract class PaginationController<T> extends BaseController<T> {
	
	private static Logger logger = LoggerFactory.getLogger(PaginationController.class);

	/**
	 * 传统分页 
	 * URL:/
	 * @param pageNumber 当前页码
	 * @param pageSize 每页记录数
	 * @param sortType 排序
	 * @param model
	 * @param request
	 * @return 列表页
	 */
	@RequestMapping
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<T> entitys = service.getEntityByPage(searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("entitys", entitys);
//		model.addAttribute("sortType", sortType);
//		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		// Ajax URL
//		if(paginationType.equals(Constant.PAGINATION_TYPE_AJAX)){
//			model.addAttribute("ajaxUrl", getControllerContext() + "/ajaxList");
//		}
		logger.debug("URL:{};Controller:{}",getControllerContext() + "/" + getListPageName(), this.getClass().getName());
		return getControllerContext() + "/" + getListPageName();
	}
}
