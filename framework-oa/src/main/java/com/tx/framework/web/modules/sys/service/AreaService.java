package com.tx.framework.web.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.common.persistence.entity.Area;
import com.tx.framework.web.modules.sys.dao.AreaDao;

/**
 * 区域Service
 * @author tangx
 * @since 2014-05-06
 */
@Service
@Transactional
public class AreaService extends BaseService<Area, String> {

	private AreaDao areaDao;

	@Autowired
	public void setAreaDao(AreaDao areaDao) {
		super.setDao(areaDao);
		this.areaDao = areaDao;
	}
	
}
