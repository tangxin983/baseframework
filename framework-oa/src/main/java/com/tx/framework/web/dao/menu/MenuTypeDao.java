package com.tx.framework.web.dao.menu;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.MenuType;



@MyBatisDao
public interface MenuTypeDao extends BaseDao<MenuType>{
  
	MenuType findByName(String menuTypeName);
	
	MenuType findRoot();
}
