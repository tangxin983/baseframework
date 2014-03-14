package com.tx.framework.web.manage.service.seat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.persistence.entity.Seat;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.dao.seat.SeatDao;

@Service
@Transactional
public class SeatService extends BaseService<Seat> {

	private SeatDao seatDao;

	@Autowired
	public void setSeatDao(SeatDao seatDao) {
		super.setDao(seatDao);
		this.seatDao = seatDao;
	}
	
	@Override
	public void saveEntity(Seat entity) {
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		seatDao.save(entity);
	}
	
	@Override
	public void updateEntity(Seat entity) {
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		seatDao.update(entity);
	}

}
