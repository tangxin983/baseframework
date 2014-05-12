package com.tx.framework.web.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.common.persistence.entity.User;


@MyBatisDao
public interface UserDao extends BaseDao<User, String> {
	
	List<User> findUserByPageAndLikeCondition(@Param(PAGE_KEY)Page<User> page, @Param(PARA_KEY)Map<String, Object> paramMap);

	User findUserById(String id);
	
	User findUserByLoginName(String loginName);
}
