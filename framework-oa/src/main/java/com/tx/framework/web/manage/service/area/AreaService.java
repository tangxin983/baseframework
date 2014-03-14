package com.tx.framework.web.manage.service.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;







import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.persistence.entity.Area;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.dao.area.AreaDao;

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
		areaDao.insert(entity);
		System.out.println("============id==========" + entity.getId());
	}
	
	@Override
	public void updateEntity(Area entity) {
		List<Area> list = areaDao.select(Area.class);
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		areaDao.update(entity);
	}
	
	@Override
	public void multiDeleteEntity(List<String> ids) {
		for(String id : ids){
			Area area = new Area();
			area.setId(id);
			areaDao.delete(area);
		}
	}

}
