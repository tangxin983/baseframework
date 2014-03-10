package com.tx.framework.web.webservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.tx.framework.common.mapper.JsonMapper;
import com.tx.framework.web.common.enums.ServiceCode;
import com.tx.framework.web.common.utils.WebServiceUtil;
import com.tx.framework.web.dao.attachment.AttachmentDao;
import com.tx.framework.web.dao.menu.MenuDao;
import com.tx.framework.web.dao.menu.MenuImagemapDao;
import com.tx.framework.web.dao.menu.MenuLayoutDao;
import com.tx.framework.web.dao.menu.MenuTypeDao;
import com.tx.framework.web.entity.Menu;
import com.tx.framework.web.entity.MenuLayout;

@Service
@Transactional
public class MenuRestService {

	@Autowired
	private MenuTypeDao menuTypeDao;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private MenuLayoutDao menuLayoutDao;

	@Autowired
	private AttachmentDao attachmentDao;

	@Autowired
	private MenuImagemapDao menuImagemapDao;

	/**
	 * 获取所有商品分类(不包括根节点)
	 * 
	 * @return
	 */
	public Map<String, Object> getMenuTypes() {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("noRoot", "1");
		return WebServiceUtil.successMap(menuTypeDao.findByParams(paraMap));
	}

	/**
	 * 获取所有商品
	 * 
	 * @return
	 */
	public Map<String, Object> getMenus() {
		List<Menu> menus = menuDao.findAll();
		for (Menu menu : menus) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("serviceId", menu.getId());
			// 绑定对应的附件
			menu.setAttachments(attachmentDao.findByParams(paramMap));
		}
		return WebServiceUtil.successMap(menus);
	}

	/**
	 * 获取所有附件图片不为空的排版
	 * 
	 * @return
	 */
	public Map<String, Object> getMenuLayouts() {
		List<MenuLayout> menuLayouts = menuLayoutDao.findNonEmpty();
		for (MenuLayout menuLayout : menuLayouts) {
			menuLayout.setPath(attachmentDao.findOne(
					menuLayout.getAttachmentId()).getPath());
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("menuLayoutId", menuLayout.getId());
			menuLayout.setImgmaps(menuImagemapDao.findByParams(paraMap));
		}
		return WebServiceUtil.successMap(menuLayouts);
	}

	/**
	 * 获取某个商品
	 * @param json
	 * @return
	 */
	public Map<String, Object> getMenu(String json) {
		Map<String, String> jsonMap = JsonMapper.nonEmptyMapper().fromJson(json, Map.class);
		// 检查json解析是否正确
		if (jsonMap == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// menuId不能为空
		if (StringUtils.isBlank(jsonMap.get("menuId"))) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		Menu menu = menuDao.findOne(jsonMap.get("menuId"));
		// 无此商品
		if (menu == null) {
			return WebServiceUtil.generateMap(ServiceCode.MENU_NOT_FOUND);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("serviceId", menu.getId());
		// 绑定对应的附件
		menu.setAttachments(attachmentDao.findByParams(paramMap));
		// 成功获取
		return WebServiceUtil.successMap(menu);
	}
}
