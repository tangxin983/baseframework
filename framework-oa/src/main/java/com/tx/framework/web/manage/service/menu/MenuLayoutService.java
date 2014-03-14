package com.tx.framework.web.manage.service.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;






import com.tx.framework.common.util.DateProvider;
import com.tx.framework.web.common.persistence.entity.MenuImagemap;
import com.tx.framework.web.common.persistence.entity.MenuLayout;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.dao.attachment.AttachmentDao;
import com.tx.framework.web.dao.menu.MenuImagemapDao;
import com.tx.framework.web.dao.menu.MenuLayoutDao;
import com.tx.framework.web.manage.service.attachment.AttachmentService;

@Service
@Transactional
public class MenuLayoutService extends BaseService<MenuLayout> {

	private MenuLayoutDao menuLayoutDao;

	@Autowired
	public void setMenuLayoutDao(MenuLayoutDao menuLayoutDao) {
		super.setDao(menuLayoutDao);
		this.menuLayoutDao = menuLayoutDao;
	}
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private MenuImagemapDao menuImagemapDao;
	
	@Autowired
	private AttachmentDao attachmentDao;

	@Override 
	public void saveEntity(MenuLayout entity) {
		entity.setCreateTime(DateProvider.DEFAULT.getDate());
		menuLayoutDao.save(entity);
	}
	
	/**
	 * 删除排版、相关热区信息、排版附件
	 * @param id 排版id
	 */
	public void deleteLayout(String id) {
		menuLayoutDao.delete(id);
		menuImagemapDao.deleteByLayout(id);
		attachmentDao.deleteByServiceId(id);
	}
	
	/**
	 * 分页table body
	 * @param entitys
	 * @return
	 */
	public String getPageContent(List<MenuLayout> entitys){
		StringBuilder sb = new StringBuilder("");
		int i = 1;
		if(!entitys.isEmpty()){
			for(MenuLayout menuLayout : entitys){
				sb.append("<tr>");
				sb.append("<td>第" + i + "页</td>");
				sb.append("<td><input name='layoutId' type='hidden' value='" + menuLayout.getId() + "'>");
				sb.append("<input name='pageNo' type='hidden' value='" + menuLayout.getPageNo() + "'>");
				sb.append("<a class='btn btn-danger' name='pageDel'><span class='glyphicon glyphicon-remove'></span></a></td>");
				sb.append("</tr>");
				i++;
			}
		}  
		return sb.toString();
	}
	
	/**
	 * 1、保存排版图片附件信息
	 * 2、更新排版表附件id和imgMap代码
	 * 3、批量保存热区坐标
	 * @param menuLayoutId
	 * @param mapCode
	 * @param metaFileInfo
	 * @param menuIds
	 * @param coords
	 */
	public void saveImgMap(String menuLayoutId, String mapCode, String metaFileInfo,
			String[] menuIds, String[] coords){
		// 保存附件信息
		List<String> metaInfos = new ArrayList<String>();
		metaInfos.add(metaFileInfo);
		String attachmentId = attachmentService.saveAttachment(metaInfos, "layout", menuLayoutId, true, true);
		// 更新排版附件信息、热区代码
		MenuLayout menuLayout = new MenuLayout();
		menuLayout.setId(menuLayoutId);
		if(StringUtils.isNotEmpty(attachmentId)){
			menuLayout.setAttachmentId(attachmentId);
		}
		menuLayout.setMapCode(mapCode);
		this.updateEntity(menuLayout);
		// 删除排版下所有旧热区信息
		menuImagemapDao.deleteByLayout(menuLayoutId);
		// 保存热区信息
		if(coords != null){
			for(int i=0; i<coords.length; i++){
				MenuImagemap menuImagemap = new MenuImagemap();
				menuImagemap.setMenuLayoutId(menuLayoutId);
				if(menuIds.length > 0 && StringUtils.isNotEmpty(menuIds[i])){
					menuImagemap.setMenuId(menuIds[i]);
				}
				String[] infos = coords[i].split(";");
				menuImagemap.setTopx(Integer.valueOf(infos[0]));
				menuImagemap.setTopy(Integer.valueOf(infos[1]));
				menuImagemap.setBottomx(Integer.valueOf(infos[2]));
				menuImagemap.setBottomy(Integer.valueOf(infos[3]));
				menuImagemap.setWidth(Integer.valueOf(infos[2]) - Integer.valueOf(infos[0]));
				menuImagemap.setHeight(Integer.valueOf(infos[3]) - Integer.valueOf(infos[1]));
				menuImagemap.setCreateTime(DateProvider.DEFAULT.getDate());
				menuImagemap.setCoord(coords[i]);
				menuImagemapDao.save(menuImagemap);
			}
		}
	}
	
	/**
	 * 获取排版信息
	 * @param menuLayoutId
	 * @return
	 */
	public Map<String, Object> getLayoutInfo(String menuLayoutId) {
		MenuLayout entity = menuLayoutDao.findOne(menuLayoutId);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("path", "");
		// 附件路径
		if(StringUtils.isNotEmpty(entity.getAttachmentId())){
			retMap.put("path", attachmentService.getEntity(entity.getAttachmentId()).getPath());
		}
		// imgMap代码
		retMap.put("mapCode", (entity.getMapCode() == null ? "" : entity.getMapCode()));
		return retMap;
	}
	
	/**
	 * 获取热区信息
	 * @param menuLayoutId
	 * @param coord
	 * @return
	 */
	public MenuImagemap getImgMapInfo(String menuLayoutId, String coord){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("menuLayoutId", menuLayoutId);
		paraMap.put("coord", coord);
		List<MenuImagemap> imgMapList = menuImagemapDao.findByParams(paraMap);
		MenuImagemap menuImagemap = new MenuImagemap();
		if(!imgMapList.isEmpty()){
			menuImagemap = imgMapList.get(0);
		}
		return menuImagemap;
	}
}
