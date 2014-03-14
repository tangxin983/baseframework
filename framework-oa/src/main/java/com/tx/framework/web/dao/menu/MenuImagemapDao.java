package com.tx.framework.web.dao.menu;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.MenuImagemap;


@MyBatisDao
public interface MenuImagemapDao extends BaseDao<MenuImagemap> {

	void deleteByLayout(String menuLayoutId);
}
