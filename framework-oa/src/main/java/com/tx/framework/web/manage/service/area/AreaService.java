package com.tx.framework.web.manage.service.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.persistence.entity.Area;
import com.tx.framework.web.common.service.BaseServiceNew;
import com.tx.framework.web.dao.area.AreaDao;

@Service
@Transactional
public class AreaService extends BaseServiceNew<Area, String> {

	private AreaDao areaDao;

	@Autowired
	public void setAreaDao(AreaDao areaDao) {
		super.setDao(areaDao);
		this.areaDao = areaDao;
	}
	
	@Override
	public void insert(Area entity) {
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		super.insert(entity);
		System.out.println(entity.getId());
	}
	
	@Override
	public void update(Area entity) {
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		super.update(entity);
	}

}
