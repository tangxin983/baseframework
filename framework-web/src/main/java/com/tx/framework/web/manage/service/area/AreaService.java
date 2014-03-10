package com.tx.framework.web.manage.service.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.base.BaseService;
import com.tx.framework.web.dao.area.AreaDao;
import com.tx.framework.web.entity.Area;

@Service
@Transactional
public class AreaService extends BaseService<Area> {

	private AreaDao areaDao;

	@Autowired
	public void setAreaDao(AreaDao areaDao) {
		super.setDao(areaDao);
		this.areaDao = areaDao;
	}
	
	@Override
	public void saveEntity(Area entity) {
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		areaDao.save(entity);
	}
	
	@Override
	public void updateEntity(Area entity) {
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		areaDao.update(entity);
	}

}
