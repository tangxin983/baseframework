package com.tx.framework.web.modules.sys.dao;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.modules.sys.entity.Role;


@MyBatisDao
public interface RoleDao extends BaseDao<Role, String> {

}
