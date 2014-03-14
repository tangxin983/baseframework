package com.tx.framework.web.manage.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





import com.tx.framework.common.util.DateProvider;
import com.tx.framework.common.util.NumberUtils;
import com.tx.framework.web.common.persistence.entity.Menu;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.dao.menu.MenuDao;

@Service
@Transactional
public class MenuService extends BaseService<Menu> {

	private MenuDao menuDao;

	@Autowired
	public void setMenuDao(MenuDao menuDao) {
		super.setDao(menuDao);
		this.menuDao = menuDao;
	}

	@Override 
	public void saveEntity(Menu entity) {
		// 保留小数点后2位
		entity.setPrice(NumberUtils.round(entity.getPrice(), 2));
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		// 点菜单位和结账单位一致（先不考虑双单位 ）
		entity.setBuyAccount(entity.getPayAccount());
		menuDao.save(entity);
	}
	
	@Override
	public void updateEntity(Menu entity) {
		entity.setPrice(NumberUtils.round(entity.getPrice(), 2));
		entity.setUpdateTime(DateProvider.DEFAULT.getDate());
		menuDao.update(entity);
	}

}
