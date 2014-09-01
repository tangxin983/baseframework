package com.tx.framework.web.modules.sys.dao;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.modules.sys.entity.Org;

/**
 * 机构Dao
 * @author tangx
 * @since 2014-05-15
 */
@MyBatisDao
public interface OrgDao extends BaseDao<Org, String> {
	
}
