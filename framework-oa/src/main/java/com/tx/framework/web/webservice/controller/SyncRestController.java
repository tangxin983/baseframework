package com.tx.framework.web.webservice.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.framework.web.webservice.service.AttachmentRestService;
import com.tx.framework.web.webservice.service.CardRestService;
import com.tx.framework.web.webservice.service.MenuRestService;
import com.tx.framework.web.webservice.service.SeatRestService;



/**
 * 数据同步接口<br>
 * 
 * 商品分类同步：/menuType
 * 商品同步：/menu
 * @author tangx
 * 
 */
@Controller
@RequestMapping(value = "/api/secure/sync")
public class SyncRestController {
	
	private static Logger logger = LoggerFactory.getLogger(SyncRestController.class);

	@Autowired
	private MenuRestService menuRestService;

	@Autowired
	private AttachmentRestService attachmentRestService;

	@Autowired
	private SeatRestService seatRestService;

	@Autowired
	private CardRestService cardRestService;

	/**
	 * 商品分类同步
	 * 
	 * @return
	 */
	@RequestMapping(value = "menuType")
	@ResponseBody
	public Map<String, Object> syncMenuType() {
		return menuRestService.getMenuTypes();
	}

	/**
	 * 商品同步（包括对应的附件信息列表）<br>
	 * 商品状态同步(在售停售沽清)<br>
	 * 如果参数不为空则代表同步某一个商品否则同步所有商品
	 * @param json(menuId)
	 * @return
	 */
	@RequestMapping(value = "menu")
	@ResponseBody
	public Map<String, Object> syncMenu(@RequestParam(value = "data", defaultValue = "") String json) {
		logger.debug("data={}", json);
		if(StringUtils.isEmpty(json)){
			return menuRestService.getMenus();
		}else{
			return menuRestService.getMenu(json);
		}
	}

	/**
	 * 图片不为空的菜单排版同步（包括对应的图片、对应的热区）
	 * 
	 * @return
	 */
	@RequestMapping(value = "menuLayout")
	@ResponseBody
	public Map<String, Object> syncMenuLayout() {
		return menuRestService.getMenuLayouts();
	}

	/**
	 * 附件下载地址同步<br>
	 * 包括商品图片、商品视频音频、菜单排版图
	 * 
	 * @return
	 */
	@RequestMapping(value = "attachment")
	@ResponseBody
	public Map<String, Object> syncAttachment() {
		return attachmentRestService.getUrls();
	}

	/**
	 * 桌位信息同步
	 * 
	 * @return
	 */
	@RequestMapping(value = "seat")
	@ResponseBody
	public Map<String, Object> syncSeat() {
		return seatRestService.getSeats();
	}

	/**
	 * 卡类型同步
	 * 
	 * @return
	 */
	@RequestMapping(value = "cardType")
	@ResponseBody
	public Map<String, Object> syncCardType() {
		return cardRestService.getCardTypes();
	}

}
