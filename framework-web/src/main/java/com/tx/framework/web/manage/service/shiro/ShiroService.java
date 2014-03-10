package com.tx.framework.web.manage.service.shiro;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tx.framework.web.common.utils.Constant;
import com.tx.framework.web.dao.resource.ResourceDao;
import com.tx.framework.web.entity.Resource;
import com.tx.framework.web.exception.ServiceException;



@Service
public class ShiroService {
	
	private static Logger logger = LoggerFactory.getLogger(ShiroService.class);

	@Value("${shiro.default}")
	private String defaultFilterChainDefinitions;

	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;

	private static final String CRLF = "\r\n";

	public String loadFilterChainDefinitions() {
		StringBuffer sb = new StringBuffer("");
		// 加载默认filter定义
		for (String def : StringUtils.split(defaultFilterChainDefinitions, ",")) {
			sb.append(def.trim() + CRLF);
		}
		// 加载uri=perm规则
		List<Resource> resourceList = resourceDao.findAll();
		for (Resource resource : resourceList) {
			if (StringUtils.isNotEmpty(resource.getUri()) && StringUtils.isNotEmpty(resource.getPermission())) {
				String uri = resource.getUri().startsWith("/") ? resource.getUri() : "/" + resource.getUri();
				sb.append(uri + "=" + MessageFormat.format(Constant.PREMISSION_STRING, resource.getPermission()) + CRLF);
			}
		}
		// 加载uri=role规则
//		List<Resource> roleList = roleDao.findAll();
//		for (Resource role : roleList) {
//			if (StringUtils.isNotEmpty(role.getUri())
//					&& StringUtils.isNotEmpty(role.getPermission())) {
//				sb.append(resource.getUri()
//						+ "="
//						+ MessageFormat.format(Constant.ROLE_STRING,
//								resource.getPermission()) + CRLF);
//			}
//		}
		return sb.toString();
	}
	
	/**
	 * 重新加载filterChain，此方法需加同步锁
	 * @param chains
	 * @throws Exception
	 */
	public synchronized void reloadShiroFilterChains() {
		try {
			AbstractShiroFilter shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
			PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
			DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
			//清空老的权限控制
			manager.getFilterChains().clear();
			shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
			//加载新的权限控制
		    shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
			for(Map.Entry<String, String> entry : shiroFilterFactoryBean.getFilterChainDefinitionMap().entrySet()) {
	            manager.createChain(entry.getKey(), entry.getValue().trim());
	        }
		} catch (Exception e) {
			logger.error("reloadShiroFilterChains error:{}", e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		
	}
}
