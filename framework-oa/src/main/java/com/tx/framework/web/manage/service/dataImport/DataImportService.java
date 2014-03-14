package com.tx.framework.web.manage.service.dataImport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import com.tx.framework.common.excel.ImportExcel;
import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.common.util.DateProvider;
import com.tx.framework.common.util.NumberUtils;
import com.tx.framework.web.common.enums.SeatType;
import com.tx.framework.web.common.persistence.entity.Area;
import com.tx.framework.web.common.persistence.entity.Card;
import com.tx.framework.web.common.persistence.entity.CardType;
import com.tx.framework.web.common.persistence.entity.Customer;
import com.tx.framework.web.common.persistence.entity.ImportCard;
import com.tx.framework.web.common.persistence.entity.ImportMenu;
import com.tx.framework.web.common.persistence.entity.ImportSeat;
import com.tx.framework.web.common.persistence.entity.Menu;
import com.tx.framework.web.common.persistence.entity.MenuType;
import com.tx.framework.web.common.persistence.entity.RemoteMsg;
import com.tx.framework.web.common.persistence.entity.Seat;
import com.tx.framework.web.dao.area.AreaDao;
import com.tx.framework.web.dao.card.CardDao;
import com.tx.framework.web.dao.cardtype.CardTypeDao;
import com.tx.framework.web.dao.customer.CustomerDao;
import com.tx.framework.web.dao.menu.MenuDao;
import com.tx.framework.web.dao.menu.MenuTypeDao;
import com.tx.framework.web.dao.seat.SeatDao;
import com.tx.framework.web.exception.ServiceException;
import com.tx.framework.web.manage.service.card.CardService;

@Service
@Transactional
public class DataImportService {

	private static Logger logger = LoggerFactory
			.getLogger(DataImportService.class);

	@Autowired
	private CardDao cardDao;

	@Autowired
	private CardTypeDao cardTypeDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private MenuTypeDao menuTypeDao;
	
	@Autowired
	private SeatDao seatDao;
	
	@Autowired
	private AreaDao areaDao;

	@Autowired
	private CardService cardService;

	public RemoteMsg uploadProcess(InputStream in, String type) {
		RemoteMsg msg = new RemoteMsg();
		try {
			if (type.equals("card")) {
				List<ImportCard> result = importProcess(in, ImportCard.class);
				if (result != null) {
					msg.setMsg(uploadCard(result));
				} else {
					msg.setMsg("标题行出错，请重新下载excel模板！");
				}
			} else if (type.equals("menu")) {
				List<ImportMenu> result = importProcess(in, ImportMenu.class);
				if (result != null) {
					msg.setMsg(uploadMenu(result));
				} else {
					msg.setMsg("标题行出错，请重新下载excel模板！");
				}
			} else if (type.equals("seat")) {
				List<ImportSeat> result = importProcess(in, ImportSeat.class);
				if (result != null) {
					msg.setMsg(uploadSeat(result));
				} else {
					msg.setMsg("标题行出错，请重新下载excel模板！");
				}
			}
		} catch (Exception e) {
			logger.error("excel导入出错，类型为" + type, e);
			throw new ServiceException("excel导入出错", e);
		}
		return msg;
	}

	/**
	 * 将输入流转化为list
	 * 
	 * @param in
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private <T> List<T> importProcess(InputStream in, Class<T> clazz)
			throws Exception {
		ImportExcel<T> importExcel = new ImportExcel<T>(clazz);
		List<T> result = (ArrayList<T>) importExcel.importExcel(in, true);
		return result;
	}

	/**
	 * 批量导入菜单
	 * 
	 * @param list
	 * @return 导入结果提示
	 */
	private String uploadMenu(List<ImportMenu> list) {
		// 商品名或商品分类为空的行忽略
		for (Iterator<ImportMenu> it = list.iterator(); it.hasNext();) {
			ImportMenu menu = it.next();
			if (StringUtils.isEmpty(menu.getMenuName())
					|| StringUtils.isEmpty(menu.getMenuTypeName())) {
				it.remove();
			}
			// 价格如果为空默认0
			if (StringUtils.isEmpty(menu.getPrice())) {
				menu.setPrice("0");
			}
			// 结账单位如果为空默认"份"
			if (StringUtils.isEmpty(menu.getPayAccount())) {
				menu.setPayAccount("份");
			}
			// 是否打折
			if (!StringUtils.isEmpty(menu.getRatioName())
					&& menu.getRatioName().equals("是")) {
				menu.setRatio(new Byte((byte) 1));
			} else {
				menu.setRatio(new Byte((byte) 0));
			}

			// 退货验证
			if (!StringUtils.isEmpty(menu.getBackAuthName())
					&& menu.getBackAuthName().equals("是")) {
				menu.setBackAuth(new Byte((byte) 1));
			} else {
				menu.setBackAuth(new Byte((byte) 0));
			}

			// 收银改价
			if (!StringUtils.isEmpty(menu.getChangePriceName())
					&& menu.getChangePriceName().equals("是")) {
				menu.setChangePrice(new Byte((byte) 1));
			} else {
				menu.setChangePrice(new Byte((byte) 0));
			}
		}
		// 判断导入的list是否为空
		if (CollectionUtils.isEmpty(list)) {
			return "找不到有效的数据行";
		}
		// 判断价格是否为数字
		List<String> prices = CollectionUtils
				.extractToList(list, "price", true);
		Pattern pattern = Pattern.compile("^\\d*(\\.\\d+)?$");
		for (String price : prices) {
			if (!pattern.matcher(price).matches()) {
				return "价格必须为合法的数字";
			}
		}
		// 获取数据库中所有商品名称
		List<String> menuNames = new ArrayList<String>();
		List<Menu> dbMenus = menuDao.findAll();
		if (CollectionUtils.isNotEmpty(dbMenus)) {
			menuNames = CollectionUtils
					.extractToList(dbMenus, "menuName", true);
		}
		// 获取list中所有商品名称
		List<String> importMenuNames = CollectionUtils.extractToList(list,
				"menuName", true);
		// 合并
		CollectionUtils.addAll(menuNames, importMenuNames.iterator());
		// 判断商品名称是否重复
		String firstDuplicateMenuName = CollectionUtils
				.getFirstDuplicateValue(menuNames);
		if (firstDuplicateMenuName != null) {
			return "商品名称" + firstDuplicateMenuName + "重复";
		}
		// 按商品类型查找，如果找不到则新建并将id放入map，否则直接将id放入map
		Map<String, String> menuTypeMap = new HashMap<String, String>();
		List<String> menuTypeNames = CollectionUtils.extractToList(list,
				"menuTypeName", true);
		Set<String> menuTypeNameSet = new HashSet<String>(menuTypeNames);// 排重
		MenuType rootMenuType = menuTypeDao.findRoot();
		for (String menuTypeName : menuTypeNameSet) {
			MenuType entity = menuTypeDao.findByName(menuTypeName);
			if (entity == null) {
				entity = new MenuType();
				entity.setMenuTypeName(menuTypeName);
				entity.setParentId(rootMenuType.getId());// 父id默认为根分类
				entity.setCreateTime(DateProvider.DEFAULT.getDate());
				menuTypeDao.save(entity);
			}
			menuTypeMap.put(menuTypeName, entity.getId());
		}
		// 开始导入
		for (ImportMenu importMenu : list) {
			Menu entity = new Menu();
			entity.setMenuTypeId(menuTypeMap.get(importMenu.getMenuTypeName()));
			entity.setMenuName(importMenu.getMenuName());
			entity.setCode(importMenu.getCode());
			entity.setPrice(NumberUtils.round(
					Double.valueOf(importMenu.getPrice()), 2));
			entity.setPayAccount(importMenu.getPayAccount());
			// 点菜单位和结账单位一致（先不考虑双单位 ）
			entity.setBuyAccount(importMenu.getPayAccount());
			entity.setRatio(importMenu.getRatio());
			entity.setBackAuth(importMenu.getBackAuth());
			entity.setChangePrice(importMenu.getChangePrice());
			entity.setCreateTime(DateProvider.DEFAULT.getDate());
			entity.setMemo(importMenu.getMemo());
			menuDao.save(entity);
		}
		return "上传成功";
	}

	/**
	 * 批量导入卡信息
	 * 
	 * @param list
	 * @return 导入结果提示
	 */
	private String uploadCard(List<ImportCard> list) {
		// 1、卡号或者卡类型为空的行忽略
		// 2、如果内部卡号为空则默认设置为卡号
		for (Iterator<ImportCard> it = list.iterator(); it.hasNext();) {
			ImportCard card = it.next();
			if (StringUtils.isEmpty(card.getCardTypeName())
					|| StringUtils.isEmpty(card.getCode())) {
				it.remove();
			}
			if (StringUtils.isEmpty(card.getInnerCode())) {
				card.setInnerCode(card.getCode());
			}
		}
		// 判断导入的list是否为空
		if (CollectionUtils.isEmpty(list)) {
			return "找不到有效的数据行";
		}
		// 获取数据库中所有卡号、内部卡号
		List<String> codes = new ArrayList<String>();
		List<String> innerCodes = new ArrayList<String>();
		List<Card> dbCards = cardDao.findAll();
		if (CollectionUtils.isNotEmpty(dbCards)) {
			codes = CollectionUtils.extractToList(dbCards, "code", true);
			innerCodes = CollectionUtils.extractToList(dbCards, "innerCode",
					true);
		}
		// 获取list中所有卡号、内部卡号
		List<String> importCodes = CollectionUtils.extractToList(list, "code",
				true);
		List<String> importInnerCodes = CollectionUtils.extractToList(list,
				"innerCode", true);
		// 合并
		CollectionUtils.addAll(codes, importCodes.iterator());
		CollectionUtils.addAll(innerCodes, importInnerCodes.iterator());
		// 判断卡号是否重复
		String firstDuplicateCode = CollectionUtils
				.getFirstDuplicateValue(codes);
		if (firstDuplicateCode != null) {
			return "卡号" + firstDuplicateCode + "重复";
		}
		// 判断内部卡号是否重复
		String firstDuplicateInnerCode = CollectionUtils
				.getFirstDuplicateValue(innerCodes);
		if (firstDuplicateInnerCode != null) {
			return "内部卡号" + firstDuplicateInnerCode + "重复";
		}
		// 判断卡密码是否符合6位纯数字要求
		List<String> pwds = CollectionUtils.extractToList(list, "pwd", true);
		Pattern pattern = Pattern.compile("^\\d{6}$");
		for (String pwd : pwds) {
			if (!pattern.matcher(pwd).matches()) {
				return "卡密码必须是6位纯数字";
			}
		}
		// 按卡类型名查找，如果找不到则新建并将id放入map，否则直接将id放入map
		Map<String, String> cardTypeMap = new HashMap<String, String>();
		List<String> cardTypeNames = CollectionUtils.extractToList(list,
				"cardTypeName", true);
		Set<String> cardTypeNameSet = new HashSet<String>(cardTypeNames);// 排重
		for (String cardTypeName : cardTypeNameSet) {
			CardType entity = cardTypeDao.findByName(cardTypeName);
			if (entity == null) {
				entity = new CardType();
				entity.setCardtypename(cardTypeName);
				entity.setCreatetime(DateProvider.DEFAULT.getDate());
				cardTypeDao.save(entity);
			}
			cardTypeMap.put(cardTypeName, entity.getId());
		}
		// 按用户名查找，如果找不到则新建并将id放入map，否则直接将id放入map
		Map<String, String> customerMap = new HashMap<String, String>();
		List<String> customerNames = CollectionUtils.extractToList(list,
				"customerName", true);
		Set<String> customerNameSet = new HashSet<String>(customerNames);// 排重
		for (String customerName : customerNameSet) {
			Customer entity = customerDao.findByName(customerName);
			if (entity == null) {
				entity = new Customer();
				entity.setCustomerName(customerName);
				entity.setCreateTime(DateProvider.DEFAULT.getDate());
				customerDao.save(entity);
			}
			customerMap.put(customerName, entity.getId());
		}
		// 开始导入
		for (ImportCard importCard : list) {
			Card entity = new Card();
			entity.setCardTypeId(cardTypeMap.get(importCard.getCardTypeName()));
			entity.setInnerCode(importCard.getInnerCode());
			entity.setCode(importCard.getCode());
			entity.setPwd(importCard.getPwd());
			// 制卡
			cardService.makeCard(entity);
			// 如果会员不为空则发卡
			if (StringUtils.isNotEmpty(importCard.getCustomerName())) {
				entity.setCustomerId(customerMap.get(importCard
						.getCustomerName()));
				cardService.sendCard(entity);
			}
		}
		return "上传成功";
	}

	private String uploadSeat(List<ImportSeat> list) {
		for (Iterator<ImportSeat> it = list.iterator(); it.hasNext();) {
			ImportSeat seat = it.next();
			// 区域或桌位名称为空的行忽略
			if (StringUtils.isEmpty(seat.getAreaName())
					|| StringUtils.isEmpty(seat.getSeatName())) {
				it.remove();
			}
			// 建议人数如果为空默认1
			if (StringUtils.isEmpty(seat.getAdviseNum())) {
				seat.setAdviseNum("1");
			}
			// 桌位类型
			if (!StringUtils.isEmpty(seat.getSeatKindName())
					&& seat.getSeatKindName().equals(SeatType.BOX.getName())) {
				seat.setSeatKind(2);
			} else {
				seat.setSeatKind(1);
			}
		}
		// 判断导入的list是否为空
		if (CollectionUtils.isEmpty(list)) {
			return "找不到有效的数据行";
		}
		// 判断建议人数是否为数字
		List<String> adviseNums = CollectionUtils.extractToList(list, "adviseNum", true);
		Pattern pattern = Pattern.compile("^\\d{1,4}$");
		for (String adviseNum : adviseNums) {
			if (!pattern.matcher(adviseNum).matches()) {
				return "建议人数需为不超过4位的整数";
			}
		}
		// 获取数据库中所有桌位名称
		List<String> seatNames = new ArrayList<String>();
		List<Seat> dbSeats = seatDao.findAll();
		if (CollectionUtils.isNotEmpty(dbSeats)) {
			seatNames = CollectionUtils.extractToList(dbSeats, "seatName", true);
		}
		// 获取list中所有桌位名称
		List<String> imporSeatNames = CollectionUtils.extractToList(list, "seatName", true);
		// 合并
		CollectionUtils.addAll(seatNames, imporSeatNames.iterator());
		// 判断桌位名称是否重复
		String firstDuplicateSeatName = CollectionUtils.getFirstDuplicateValue(seatNames);
		if (firstDuplicateSeatName != null) {
			return "桌位名称" + firstDuplicateSeatName + "重复";
		}
		// 按区域名称查找，如果找不到则新建区域并将id放入map，否则直接将id放入map
		Map<String, String> areaMap = new HashMap<String, String>();
		List<String> areaNames = CollectionUtils.extractToList(list, "areaName", true);
		Set<String> areaNameSet = new HashSet<String>(areaNames);// 排重
		for (String areaName : areaNameSet) {
			Area entity = areaDao.findByName(areaName);
			if (entity == null) {
				entity = new Area();
				entity.setAreaName(areaName);
				entity.setCreateTime(DateProvider.DEFAULT.getDate());
				areaDao.save(entity);
			}
			areaMap.put(areaName, entity.getId());
		}
		// 开始导入
		for (ImportSeat importSeat : list) {
			Seat entity = new Seat();
			entity.setAreaId(areaMap.get(importSeat.getAreaName()));
			entity.setSeatName(importSeat.getSeatName());
			entity.setCode(importSeat.getCode());
			entity.setAdviseNum(Integer.parseInt(importSeat.getAdviseNum()));
			entity.setSeatKind(importSeat.getSeatKind());
			entity.setCreateTime(DateProvider.DEFAULT.getDate());
			seatDao.save(entity);
		}
		return "上传成功";
	}

}
