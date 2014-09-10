package com.tx.framework.web.modules.oa.dao;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.modules.oa.entity.Leave;

/**
 * 请假Dao
 * @author tangx
 * @since 2014-05-28
 */
@MyBatisDao
public interface LeaveDao extends BaseDao<Leave> {
	
}
