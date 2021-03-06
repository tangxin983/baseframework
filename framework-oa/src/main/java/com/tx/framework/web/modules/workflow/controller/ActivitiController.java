package com.tx.framework.web.modules.workflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.modules.workflow.service.ActivitiService;

@Controller
@RequestMapping(value = "act")
public class ActivitiController {

	@Autowired
	private ActivitiService activitiService;
	
	@RequestMapping("sync")
	@ResponseBody
	public String sync() {
		activitiService.syncActiviti();
		return "同步成功";
	}
 
}
