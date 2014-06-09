package com.tx.framework.web.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.modules.sys.service.ActivitiService;

@Controller
@RequestMapping(value = "sys/act")
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
