package com.tx.framework.web.dao.resource;

import java.util.List;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Resource;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 */

@MyBatisDao
public interface ResourceDao extends BaseDao<Resource>{

	List<Resource> findByUserId(String userId);
	
	List<Resource> getBreadcrumb(String uri);
	
	void deleteResourceRole(String id);
}
