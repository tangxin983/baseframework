package com.tx.framework.web.modules.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.modules.cms.entity.Article;

/**
 * 文章Dao
 * 
 * @author tangx
 * @since 2014-09-22
 */
@MyBatisDao
public interface ArticleDao extends BaseDao<Article> {

	List<Article> findArticleByCategory(@Param(PAGE_KEY) Page<Article> page,
			@Param(PARA_KEY) Map<String, Object> paraMap);
}
