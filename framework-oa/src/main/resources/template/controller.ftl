package ${packageName}.${moduleName}.controller${subModuleName};

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tx.framework.web.common.controller.BaseController;
import com.tx.framework.web.common.config.Constant;
import ${entityPackageName}.${ClassName};
import ${packageName}.${moduleName}.service${subModuleName}.${ClassName}Service;

/**
 * ${functionName}Controller
 * @author ${classAuthor}
 * @version ${classVersion}
 */
@Controller
@RequestMapping(value = "${urlPrefix}")
public class ${ClassName}Controller extends BaseController<${ClassName}, String> {

	private ${ClassName}Service ${className}Service;

	@Autowired
	public void set${ClassName}Service(${ClassName}Service ${className}Service) {
		super.setService(${className}Service);
		this.${className}Service = ${className}Service;
	}
	
	// ========== 以下为简单crud示例。注意：一旦修改url，对应生成的视图url也需手动修改 ===========
	<#if isPagination>
	/**
	 * 跳转列表页（分页）<br>
	 * url:${urlPrefix}
	 */
	@RequestMapping
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", defaultValue = Constant.PAGINATION_SIZE) int pageSize,
			Model model, HttpServletRequest request) {
		return super.list(pageNumber, pageSize, model, request);
	}
	<#else>
	/**
	 * 跳转列表页（不分页）<br>
	 * url:${urlPrefix}
	 */
	@RequestMapping
	public String view(Model model, HttpServletRequest request) {
		return super.view(model, request);
	}
	</#if>
	
	/**
	 * 跳转新增页面<br>
	 * url:${urlPrefix}/create
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		return super.createForm(model);
	}

	/**
	 * 新增操作<br>
	 * url:${urlPrefix}/create
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid ${ClassName} entity,
			RedirectAttributes redirectAttributes) {
		return super.create(entity, redirectAttributes);
	}
	
	/**
	 * 跳转更新页面<br>
	 * URL:${urlPrefix}/update/{id}
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		return super.updateForm(id, model);
	}
	
	/**
	 * 更新操作<br>
	 * URL:${urlPrefix}/update
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")${ClassName} entity, RedirectAttributes redirectAttributes) {
		return super.update(entity, redirectAttributes);
	}
	
	/**
	 * 删除操作<br>
	 * URL:${urlPrefix}/delete/{id}
	 */
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		return super.delete(id, redirectAttributes);
	}
	
	/**
	 * 批量删除操作<br>
	 * URL:${urlPrefix}/delete
	 */
	@RequestMapping("delete")
	public String multiDelete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		return super.multiDelete(ids, redirectAttributes);
	}
	
	/**
	 * 根据id查找实体（json）<br>
	 * URL:${urlPrefix}/get/{id}
	 */
	@RequestMapping("get/{id}")
	@ResponseBody
	public ${ClassName} get(@PathVariable("id") String id) {
		return super.get(id);
	}

}
