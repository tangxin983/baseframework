package com.tx.framework.web.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.common.service.BaseServiceNew;
import com.tx.framework.web.manage.freemarker.FreeMarkerResolver;

/**
 * 提供基础的crud控制处理
 * @author tangx
 *
 * @param <T>
 * @param <PK>
 */
public abstract class BaseControllerNew<T, PK>  implements ServletContextAware{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected BaseServiceNew<T, PK> service;

	public void setService(BaseServiceNew<T, PK> service) {
		this.service = service;
	}
	
	protected ServletContext servletContext;

    public void setServletContext(ServletContext context){
    	this.servletContext = context;
    }
    
    @Autowired
	private FreeMarkerResolver freeMarkerResolver;
    
    /**
	 * 供子类设置额外的查询参数
	 * @param searchParams
	 */
	protected void setExtraSearchParam(Map<String, Object> searchParams){
		
	}
	
	/**
	 * 模型公共属性设置
	 * @param model
	 */
	protected void setModelAttr(Model model){
		model.addAttribute("module", getControllerContext());
		model.addAttribute("moduleb", getControllerContext() + "/b");
		model.addAttribute("ctxModule", servletContext.getContextPath() + "/" + getControllerContext());
		model.addAttribute("ctxModuleb", servletContext.getContextPath() + "/" + getControllerContext() + "/b");
	}
	
	/**
	 * 通过反射取得requestMapping value（头尾的'/'会被去掉）
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
    		if(context.endsWith("/")){
    			context = context.substring(0,context.length() - 1);
    		}
        }
        return context;
    }
	
	/**
	 * 列表页<br>
	 * 默认为"modules/" + ControllerContext + "List"。比如/a/b对应modules/a/bList
	 * @return
	 */
	protected String getListPage(){
		return "modules/" + getControllerContext() + "List";
	}
	
	/**
	 * 新增表单页<br>
	 * 默认为"modules/" + ControllerContext + "Form"。比如/a/b对应modules/a/bForm
	 * @return
	 */
	protected String getCreateFormPage(){
		return "modules/" + getControllerContext() + "Form";
	}
	
	/**
	 * 更新表单页<br>
	 * 默认为"modules/" + ControllerContext + "Form"。比如/a/b对应modules/a/bForm
	 * @return
	 */
	protected String getUpdateFormPage(){
		return "modules/" + getControllerContext() + "Form";
	}
	
	/**
	 * 添加Flash消息
     * @param messages 消息
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 分页展示
	 * @param pageNumber 当前页码
	 * @param pageSize 每页记录数
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("b")
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		setExtraSearchParam(searchParams);
		Page<T> entitys = service.selectByPage(searchParams, pageNumber, pageSize);
		model.addAttribute("page", entitys);
		// 将搜索条件编码成字符串，用于分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		setModelAttr(model);
		return getListPage();
	}
	
	/**
	 * 不分页展示所有记录
	 * URL:/view
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("b/view")
	public String view(Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		List<T> entitys = service.select(searchParams);
		model.addAttribute("entitys", entitys);
		setModelAttr(model);
		return getListPage();
	}
	
	/**
	 * 跳转新增页面 
	 * URL:/create(method=get)
	 * @param model
	 * @return 新增页面
	 */
	@RequestMapping(value = "b/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("action", "create");
		setModelAttr(model);
		return getCreateFormPage();
	}

	/**
	 * 新增操作
	 * URL:/create(method=post)
	 * @param entity
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	@RequestMapping(value = "b/create", method = RequestMethod.POST)
	public String create(@Valid T entity, RedirectAttributes redirectAttributes) {
		service.insert(entity);
		addMessage(redirectAttributes, "添加成功");
		return "redirect:/" + getControllerContext() + "/b";
	}

	/**
	 * 跳转更新页面
	 * URL:/update/{id}(method=get)
	 * @param id
	 * @param model
	 * @return 更新页面
	 */
	@RequestMapping(value = "b/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") PK id, Model model) {
		model.addAttribute("entity", service.selectById(id));
		model.addAttribute("action", "update");
		setModelAttr(model);
		return getUpdateFormPage();
	}

	/**
	 * 更新操作
	 * URL:/update(method=post)
	 * @param entity
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	@RequestMapping(value = "b/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")T entity, RedirectAttributes redirectAttributes) {
		service.update(entity);
		addMessage(redirectAttributes, "更新成功");
		return "redirect:/" + getControllerContext() + "/b";
	}

	/**
	 * 删除操作
	 * URL:/delete/{id}
	 * @param id
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	@RequestMapping("b/delete/{id}")
	public String delete(@PathVariable("id") PK id, RedirectAttributes redirectAttributes) {
		service.deleteById(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:/" + getControllerContext() + "/b";
	}
	
	/**
	 * 批量删除操作
	 * URL:/delete
	 * @param ids
	 * @param redirectAttributes
	 * @return redirect到列表页
	 */
	@RequestMapping("b/delete")
	public String multiDelete(@RequestParam("ids")List<PK> ids,RedirectAttributes redirectAttributes) {
		service.deleteByIds(ids);
		addMessage(redirectAttributes, "删除" + ids.size() + "条记录 成功");
		return "redirect:/" + getControllerContext() + "/b";
	}
	
	/**
	 * 根据id查找实体
	 * @param id
	 * @return json对象
	 */
	@RequestMapping(value = "b/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public T get(@PathVariable("id") PK id) {
		return service.selectById(id);
	}
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果
	 * 先根据form的id从数据库查出对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 * @param id 主键
	 * @param model
	 */
	@ModelAttribute
	public void bindingEntity(@RequestParam(value = "id", defaultValue = "-1") PK id, Model model) {
		if (!id.equals("-1")) {
			model.addAttribute("entity", service.selectById(id));
		}
	}
}
