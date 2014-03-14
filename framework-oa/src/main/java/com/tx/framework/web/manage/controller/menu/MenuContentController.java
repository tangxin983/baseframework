package com.tx.framework.web.manage.controller.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;







import com.tx.framework.common.util.Servlets;
import com.tx.framework.web.common.controller.BaseController;
import com.tx.framework.web.common.enums.AttachmentType;
import com.tx.framework.web.common.persistence.entity.Attachment;
import com.tx.framework.web.common.persistence.entity.Menu;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.manage.freemarker.FreeMarkerResolver;
import com.tx.framework.web.manage.service.attachment.AttachmentService;
import com.tx.framework.web.manage.service.menu.MenuService;

@Controller
@RequestMapping(value = "/menuContent")
public class MenuContentController extends BaseController<Menu> {

	private MenuService menuService;

	@Autowired
	public void setMenuService(MenuService menuService) {
		super.setService(menuService);
		this.menuService = menuService;
	}
	 
	@Autowired
	private FreeMarkerResolver freeMarkerResolver;
	
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 展示页面
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String list(Model model) {
		model.addAttribute("module", getControllerContext());
		return getControllerContext() + "/" + getListPageName();
	}
	
	/**
	 * ajax构造thumbnail grid
	 * @param pageNumber 当前页码
	 * @param pageSize 每页记录数
	 * @param sortType 排序
	 * @param request
	 * @return Page对象
	 */
	@RequestMapping(value = "thumbnail")
	@ResponseBody
	public Page<Menu> thumbnail(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = "8") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Menu> entitys = menuService.getEntityByPage(searchParams, pageNumber, pageSize, sortType);
		this.buildThumbnailGrid(entitys);
		return entitys;
	}
	
	/**
	 * 生成thumbnail
	 * @param page 分页对象
	 * @return
	 */
	private void buildThumbnailGrid(Page<Menu> page){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("entitys", page.getResult());
		model.put("ctx", servletContext.getContextPath());
		model.put("module", getControllerContext());
		List<Menu> menus = page.getResult();
		for(Menu menu : menus){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("serviceId", menu.getId());
			paramMap.put("attachmentType", AttachmentType.IMAGE.getValue());
			// 查找已存在的图片附件
			List<Attachment> attachments = attachmentService.getEntityByParams(paramMap);
			if(!attachments.isEmpty()){
				menu.setImagePath(attachments.get(0).getPath());
			}else {
				menu.setImagePath("");
			}
		}
		page.setBody(freeMarkerResolver.mergeModelToTemlate("menuContent/menuContent.htm", model));
	}
	
}
