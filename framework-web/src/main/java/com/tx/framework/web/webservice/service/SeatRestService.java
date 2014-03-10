package com.tx.framework.web.webservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.web.common.utils.WebServiceUtil;
import com.tx.framework.web.dao.seat.SeatDao;



@Service
@Transactional
public class SeatRestService {

	@Autowired
	private SeatDao seatDao;
	
 
	/**
	 * 获取桌位信息
	 * @return
	 */
	public Map<String, Object> getSeats() {
		return WebServiceUtil.successMap(seatDao.findAll());
	}
	 
}
