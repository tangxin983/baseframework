package com.tx.framework.web.manage.controller.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tx.framework.web.common.controller.AjaxPaginationController;
import com.tx.framework.web.common.persistence.entity.Area;
import com.tx.framework.web.manage.service.area.AreaService;



@Controller
@RequestMapping(value = "/area")
public class AreaController extends AjaxPaginationController<Area> {

	private AreaService areaService;

	@Autowired
	public void setAreaService(AreaService areaService) {
		super.setService(areaService);
		this.areaService = areaService;
	}
}
