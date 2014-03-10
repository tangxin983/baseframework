package com.tx.framework.web.dao.menu;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.MenuType;



@MyBatisRepository
public interface MenuTypeDao extends BaseDao<MenuType>{
  
	MenuType findByName(String menuTypeName);
	
	MenuType findRoot();
}
