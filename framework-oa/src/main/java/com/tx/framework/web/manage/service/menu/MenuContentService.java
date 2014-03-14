package com.tx.framework.web.manage.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.persistence.entity.MenuContent;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.dao.menu.MenuContentDao;

@Service
@Transactional
public class MenuContentService extends BaseService<MenuContent> {

	private MenuContentDao menuContentDao;

	@Autowired
	public void setMenuTypeDao(MenuContentDao menuContentDao) {
		super.setDao(menuContentDao);
		this.menuContentDao = menuContentDao;
	}

	@Override 
	public void saveEntity(MenuContent entity) {
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		menuContentDao.save(entity);
	}
	
	 
}
