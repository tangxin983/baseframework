package com.tx.framework.web.modules.sys.dao;

import java.util.List;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.modules.sys.entity.Menu;


@MyBatisDao
public interface MenuDao extends BaseDao<Menu, String> {

	List<Menu> findByUserId(String userId);
}
