package com.tx.framework.web.dao.role;

import java.util.Map;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Role;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 */

@MyBatisDao
public interface RoleDao extends BaseDao<Role>{

	void saveRoleUser(Map<String, Object> map);
	
	void saveRoleResource(Map<String, Object> map);
	
	void deleteRoleResource(String id);
	
	void deleteRoleUser(String id);
	
	void deleteUserRole(String id);
	
	int findUserCount(String id);
}
