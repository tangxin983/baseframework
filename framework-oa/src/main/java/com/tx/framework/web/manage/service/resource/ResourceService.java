package com.tx.framework.web.manage.service.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.web.common.persistence.entity.Resource;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.dao.resource.ResourceDao;
import com.tx.framework.web.manage.freemarker.FreeMarkerResolver;
import com.tx.framework.web.manage.service.attachment.AttachmentService;
import com.tx.framework.web.modules.sys.security.ShiroAuthorizingRealm;

@Service
@Transactional
public class ResourceService extends BaseService<Resource> {

	private ResourceDao resourceDao;

	@Autowired
	private ShiroAuthorizingRealm shiroDbRealm;

	@Autowired
	private ShiroAuthorizingRealm shiroAuthorizingRealm;
	
	@Autowired
	private FreeMarkerResolver freeMarkerResolver;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	public void setResourceDao(ResourceDao resourceDao) {
		super.setDao(resourceDao);
		this.resourceDao = resourceDao;
	}

	public void saveResource(Resource entity) {
		dao.save(entity);
		// 保存附件
		attachmentService.saveAttachment(
				StringUtils.defaultString(entity.getMetaInfo()), "resource",
				entity.getId(), false, false);
		shiroAuthorizingRealm.clearCacheAndReloadFilterChain();
	}

	public void updateResource(Resource entity) {
		dao.update(entity);
		// 保存附件
		attachmentService.saveAttachment(
				StringUtils.defaultString(entity.getMetaInfo()), "resource",
				entity.getId(), false, false);
		shiroAuthorizingRealm.clearCacheAndReloadFilterChain();
	}

	public void deleteResource(String id) {
		resourceDao.delete(id);
		resourceDao.deleteResourceRole(id);
		// 删除附件
		attachmentService.saveAttachment("", "resource", id, false, false);
		shiroAuthorizingRealm.clearCacheAndReloadFilterChain();
	}

	public void MultiDeleteResource(List<String> ids) {
		for (String id : ids) {
			resourceDao.delete(id);
			resourceDao.deleteResourceRole(id);
			// 删除附件
			attachmentService.saveAttachment("", "resource", id, false, false);
		}
		shiroAuthorizingRealm.clearCacheAndReloadFilterChain();
	}
	
	/**
	 * 面包屑导航
	 * @param path
	 */
	public String breadcrumb(String path) {
		String breadcrumb = "";
		Map<String, Object> model = new HashMap<String, Object>();
		List<Resource> resources = resourceDao.getBreadcrumb("/" + path);
		if(resources != null && !resources.isEmpty()){
			model.put("resourceName",resources.get(0).getResourceName());
			model.put("parentResourceName",resources.get(0).getParentResourceName());
			model.put("pid",resources.get(0).getParentId());
			breadcrumb = freeMarkerResolver.mergeModelToTemlate("account/breadcrumb.htm", model);
		}
		return breadcrumb;
	}
}
