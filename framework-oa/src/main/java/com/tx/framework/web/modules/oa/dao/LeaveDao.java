package com.tx.framework.web.modules.oa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Leave;
import com.tx.framework.web.common.persistence.entity.Page;

/**
 * 请假Dao
 * @author tangx
 * @since 2014-05-28
 */
@MyBatisDao
public interface LeaveDao extends BaseDao<Leave, String> {
	
	List<Leave> findLeave(@Param(PAGE_KEY)Page<Leave> page, @Param(PARA_KEY)Map<String, Object> paramMap);
}
