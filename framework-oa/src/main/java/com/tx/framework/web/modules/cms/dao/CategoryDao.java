package com.tx.framework.web.modules.cms.dao;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.modules.cms.entity.Category;

/**
 * cms栏目Dao
 * @author tangx
 * @since 2014-09-10
 */
@MyBatisDao
public interface CategoryDao extends BaseDao<Category> {
	
}
