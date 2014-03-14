package com.tx.framework.web.manage.controller.seat;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.common.controller.AjaxPaginationController;
import com.tx.framework.web.common.persistence.entity.RemoteMsg;
import com.tx.framework.web.common.persistence.entity.Seat;
import com.tx.framework.web.manage.service.area.AreaService;
import com.tx.framework.web.manage.service.seat.SeatService;



@Controller
@RequestMapping(value = "/seat")
public class SeatController extends AjaxPaginationController<Seat> {

	private SeatService seatService;

	@Autowired
	public void setSeatService(SeatService seatService) {
		super.setService(seatService);
		this.seatService = seatService;
	}
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping
	@Override
	public String list(Model model, ServletRequest request) {
		model.addAttribute("areaList", areaService.getAllEntity());
		return super.list(model, request);
	}
 
	
	@RequestMapping(value = "checkName")
	@ResponseBody
	public RemoteMsg checkName(@RequestParam("seatName") String seatName, 
			@RequestParam("id") String id) {
		RemoteMsg msg = new RemoteMsg();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("seatName", seatName);
		searchParams.put("id", id);
		if(seatService.getCountByParams(searchParams) == 0){
			msg.setSuccess("true");
		} else {
			msg.setSuccess("false");
			msg.setMsg("此名称已存在");
		}
		return msg;
	}

}
