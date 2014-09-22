package com.tx.framework.web.modules.cms.dao;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.modules.cms.entity.Article;

/**
 * 文章Dao
 * @author tangx
 * @since 2014-09-22
 */
@MyBatisDao
public interface ArticleDao extends BaseDao<Article> {
	
}
