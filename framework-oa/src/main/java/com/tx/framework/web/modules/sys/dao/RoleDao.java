package com.tx.framework.web.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.modules.sys.entity.Role;

@MyBatisDao
public interface RoleDao extends BaseDao<Role, String> {

	@Select("select * from sys_role LEFT JOIN sys_user_role on sys_role.id = sys_user_role.role_id "
			+ "WHERE sys_user_role.user_id = #{userId}")
	List<Role> findRolesByUserId(String userId);
}
