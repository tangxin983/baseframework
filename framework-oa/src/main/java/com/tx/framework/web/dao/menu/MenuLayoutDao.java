package com.tx.framework.web.dao.menu;

import java.util.List;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.MenuLayout;




@MyBatisDao
public interface MenuLayoutDao extends BaseDao<MenuLayout>{
	
	List<MenuLayout> findNonEmpty(); 
  
}
