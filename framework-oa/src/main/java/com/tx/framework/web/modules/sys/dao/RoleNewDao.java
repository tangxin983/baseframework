package com.tx.framework.web.modules.sys.dao;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDaoNew;
import com.tx.framework.web.modules.sys.entity.Role;


@MyBatisDao
public interface RoleNewDao extends BaseDaoNew<Role, String> {

}
