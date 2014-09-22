package com.tx.framework.web.modules.cms.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tx.framework.web.common.controller.BaseController;
import com.tx.framework.web.modules.cms.entity.Category;
import com.tx.framework.web.modules.cms.service.CategoryService;
import com.tx.framework.web.modules.sys.service.MenuService;

/**
 * cms栏目Controller
 * 
 * @author tangx
 * @since 2014-09-10
 */
@Controller
@RequestMapping(value = "cms/publish")
public class ArticlePublishController extends BaseController<Category> {

	private CategoryService categoryService;

	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		super.setService(categoryService);
		this.categoryService = categoryService;
	}

	/**
	 * 跳转列表页
	 * <p>
	 * url:cms/category
	 */
	@RequestMapping
	public String index(Model model) {
		return "modules/cms/articlePublish";
	}

}
