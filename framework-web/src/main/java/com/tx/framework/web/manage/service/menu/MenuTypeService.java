package com.tx.framework.web.manage.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.base.BaseService;
import com.tx.framework.web.common.utils.Constant;
import com.tx.framework.web.dao.menu.MenuTypeDao;
import com.tx.framework.web.entity.MenuType;


@Service
@Transactional
public class MenuTypeService extends BaseService<MenuType> {

	private MenuTypeDao menuTypeDao;

	@Autowired
	public void setMenuTypeDao(MenuTypeDao menuTypeDao) {
		super.setDao(menuTypeDao);
		this.menuTypeDao = menuTypeDao;
	}

	@Override 
	public void saveEntity(MenuType entity) {
		entity.setValid(new Byte(Constant.VALID));
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		menuTypeDao.save(entity);
	}
	
	@Override
	public void updateEntity(MenuType entity) {
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		menuTypeDao.update(entity);
	}

}
