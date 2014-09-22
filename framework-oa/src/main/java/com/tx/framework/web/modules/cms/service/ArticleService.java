package com.tx.framework.web.modules.cms.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.modules.cms.entity.Article;
import com.tx.framework.web.modules.cms.dao.ArticleDao;

/**
 * 文章Service
 * @author tangx
 * @since 2014-09-22
 */
@Service
@Transactional
public class ArticleService extends BaseService<Article> {

	private ArticleDao articleDao;

	@Autowired
	public void setArticleDao(ArticleDao articleDao) {
		super.setDao(articleDao);
		this.articleDao = articleDao;
	}
	
	// ========== 以下为简单增删改示例。 修改以适应实际需求===========
	@Override
	public void insert(Article entity) {
		super.insert(entity);
	}
	
	@Override
	public void update(Article entity) {
		super.update(entity);
	}

	@Override
	public void delete(String id) {
		super.delete(id);
	}
	 
	@Override
	public void delete(List<String> ids) {
		super.delete(ids);
	}
	
}
