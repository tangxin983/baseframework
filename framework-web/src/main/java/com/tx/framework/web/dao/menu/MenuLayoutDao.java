package com.tx.framework.web.dao.menu;

import java.util.List;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.MenuLayout;




@MyBatisRepository
public interface MenuLayoutDao extends BaseDao<MenuLayout>{
	
	List<MenuLayout> findNonEmpty(); 
  
}
