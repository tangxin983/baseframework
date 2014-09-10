package com.tx.framework.web.modules.sys.dao;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.modules.sys.entity.Area;

/**
 * 区域Dao
 * @author tangx
 * @since 2014-05-12
 */
@MyBatisDao
public interface AreaDao extends BaseDao<Area> {
	
}
