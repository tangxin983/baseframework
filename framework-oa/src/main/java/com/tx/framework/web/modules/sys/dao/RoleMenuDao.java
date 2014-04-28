package com.tx.framework.web.modules.sys.dao;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDaoNew;
import com.tx.framework.web.modules.sys.entity.RoleMenu;


@MyBatisDao
public interface RoleMenuDao extends BaseDaoNew<RoleMenu, String> {

}
