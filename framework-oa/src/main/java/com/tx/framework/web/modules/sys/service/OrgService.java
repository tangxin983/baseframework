package com.tx.framework.web.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.common.persistence.entity.Menu;
import com.tx.framework.web.common.persistence.entity.Org;
import com.tx.framework.web.modules.sys.dao.OrgDao;

/**
 * 机构Service
 * @author tangx
 * @since 2014-05-15
 */
@Service
@Transactional
public class OrgService extends BaseService<Org, String> {

	private OrgDao orgDao;

	@Autowired
	public void setOrgDao(OrgDao orgDao) {
		super.setDao(orgDao);
		this.orgDao = orgDao;
	}
	
	/**
	 * 按照编码排序获取机构列表
	 * 
	 * @param searchParams
	 * @return
	 */
	public List<Org> findOrgOrderByCode() {
		List<String> orders = Lists.newArrayList();
		orders.add("code");
		return select(orders);
	}
	
}
