package com.tx.framework.web.dao.menu;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.MenuImagemap;


@MyBatisRepository
public interface MenuImagemapDao extends BaseDao<MenuImagemap> {

	void deleteByLayout(String menuLayoutId);
}
