package com.tx.framework.web.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.common.util.Servlets;
import com.tx.framework.web.common.service.BaseService;

/**
 * 提供基础的crud控制处理
 * 
 * @author tangx
 * 
 * @param <T>
 */
public abstract class BaseController<T>  implements ServletContextAware{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected BaseService<T> service;

	public void setService(BaseService<T> service) {
		this.service = service;
	}
	
	protected ServletContext servletContext;

    public void setServletContext(ServletContext context){
    	this.servletContext = context;
    }
	
	/**
	 * 通过反射取得requestMapping value
	 * @return
	 */
	protected String getControllerContext() {
		String context = "";
		Class<?>  c = this.getClass();
        if ( c != null ) {
            RequestMapping mapping = c.getAnnotation(RequestMapping.class);
    		String[] mappingValues = mapping.value();
    		context = mappingValues[0];
    		if(context.startsWith("/")){
    			context = context.substring(1);
    		}
        }
        return context;
    }
	
	/**
	 * 列表页文件名
	 * 默认为ControllerContext + List
	 * @return
	 */
	protected String getListPageName(){
		return getControllerContext() + "List";
	}
	
	/**
	 * 新增表单页文件名
	 * 默认为ControllerContext + Form
	 * @return
	 */
	protected String getCreateFormPageName(){
		return getControllerContext() + "Form";
	}
	
	/**
	 * 更新表单页文件名
	 * 默认为ControllerContext + Form
	 * @return
	 */
	protected String getUpdateFormPageName(){
		return getControllerContext() + "Form";
	}
	
	/**
	 * 不分页展示所有记录
	 * URL:/view
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "view")
	public String view(Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		List<T> entitys = service.getEntityByParams(searchParams);
		model.addAttribute("entitys", entitys);
		return getControllerContext() + "/" + getListPageName();
	}
	
	/**
	 * 跳转新增页面 
	 * URL:/create(method=get)
	 * @param model
	 * @return 新增页面
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("action", "create");
		return getControllerContext() + "/" + getCreateFormPageName();
	}

	/**
	 * 新增操作
	 * URL:/create(method=post)
	 * @param entity
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid T entity, RedirectAttributes redirectAttributes) {
		service.saveEntity(entity);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/" + getControllerContext();
	}

	/**
	 * 跳转更新页面
	 * URL:/update/{id}(method=get)
	 * @param id
	 * @param model
	 * @return 更新页面
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("entity", service.getEntity(id));
		model.addAttribute("action", "update");
		return getControllerContext() + "/" + getUpdateFormPageName();
	}

	/**
	 * 更新操作
	 * URL:/update(method=post)
	 * @param entity
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")T entity, RedirectAttributes redirectAttributes) {
		service.updateEntity(entity);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/" + getControllerContext();
	}

	/**
	 * 删除操作
	 * URL:/delete/{id}
	 * @param id
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		service.deleteEntity(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/" + getControllerContext();
	}
	
	/**
	 * 批量删除操作
	 * URL:/delete
	 * @param ids
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	@RequestMapping("delete")
	public String multiDelete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		service.multiDeleteEntity(ids);
		redirectAttributes.addFlashAttribute("message", "删除" + ids.size() + "个记录 成功");
		return "redirect:/" + getControllerContext();
	}
	
	/**
	 * 根据id查找实体
	 * @param id
	 * @return json对象
	 */
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public T get(@PathVariable("id") String id) {
		return service.getEntity(id);
	}
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果
	 * 先根据form的id从数据库查出对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 * @param id 主键
	 * @param model
	 */
	@ModelAttribute
	public void bindingEntity(@RequestParam(value = "id", defaultValue = "-1") String id, Model model) {
		if (!id.equals("-1")) {
			model.addAttribute("entity", service.getEntity(id));
		}
	}
}
