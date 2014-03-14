package com.tx.framework.web.manage.controller.operateflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tx.framework.web.common.controller.AjaxPaginationController;
import com.tx.framework.web.common.persistence.entity.OperateFlow;
import com.tx.framework.web.manage.service.operateflow.OperateFlowService;



@Controller
@RequestMapping(value = "/operateflow")
public class OperateFlowController extends
		AjaxPaginationController<OperateFlow> {

	private OperateFlowService operateFlowService;

	@Autowired
	public void setOperateFlowService(OperateFlowService operateFlowService) {
		super.setService(operateFlowService);
		this.operateFlowService = operateFlowService;
	}
 
}
