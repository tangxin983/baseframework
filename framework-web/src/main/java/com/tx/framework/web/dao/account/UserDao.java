package com.tx.framework.web.dao.account;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.User;


/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 */

@MyBatisRepository
public interface UserDao extends BaseDao<User>{
	
	User findByLoginName(String loginName);
	
}
