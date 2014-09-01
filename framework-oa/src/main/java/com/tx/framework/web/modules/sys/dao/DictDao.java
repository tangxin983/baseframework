package com.tx.framework.web.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.modules.sys.entity.Dict;

/**
 * 字典Dao
 * @author tangx
 * @since 2014-06-25
 */
@MyBatisDao
public interface DictDao extends BaseDao<Dict, String> {
	
	@Select("select type from sys_dict group by type")
	public List<String> findTypeList();
	
}
