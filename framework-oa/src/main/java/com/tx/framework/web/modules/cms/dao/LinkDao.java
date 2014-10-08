package com.tx.framework.web.modules.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.modules.cms.entity.Link;

/**
 * 链接Dao
 * @author tangx
 * @since 2014-09-30
 */
@MyBatisDao
public interface LinkDao extends BaseDao<Link> {
	
	List<Link> findLinkByCategory(@Param(PAGE_KEY) Page<Link> page,
			@Param(PARA_KEY) Map<String, Object> paraMap);
}
