package com.tx.framework.web.manage.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.base.BaseService;
import com.tx.framework.web.dao.menu.MenuImagemapDao;
import com.tx.framework.web.entity.MenuImagemap;

@Service
@Transactional
public class MenuImagemapService extends BaseService<MenuImagemap> {

	private MenuImagemapDao menuImagemapDao;

	@Autowired
	public void setMenuImagemapDao(MenuImagemapDao menuImagemapDao) {
		super.setDao(menuImagemapDao);
		this.menuImagemapDao = menuImagemapDao;
	}

	@Override 
	public void saveEntity(MenuImagemap entity) {
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		menuImagemapDao.save(entity);
	}
	
	 
}
