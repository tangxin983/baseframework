package com.tx.framework.web.common.freemarker;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.tx.framework.web.exception.ServiceException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 使用freemarker模板解析输出html片段
 * @author tangx
 *
 */
@Component
public class FreeMarkerResolver {

	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;  
	
	public String mergeModelToTemlate(String templateName, Map<String, Object> model) {
        try {
            Configuration configuration = freemarkerConfigurer.getConfiguration();
            Template t = configuration.getTemplate(templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (Exception e) {
        	throw new ServiceException("模板解析错误:" + e.getMessage(), e);
        }  
    }
}
