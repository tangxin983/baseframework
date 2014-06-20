package com.tx.framework.web.common.controller;

import javax.validation.Valid;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.web.common.persistence.entity.Leave;


/**
 * 提供基础的crud和工作流控制
 * @author tangx
 *
 * @param <T>
 * @param <PK>
 */
public abstract class OaController<T, PK>  extends BaseController<T, PK>{
	
	
	
}
