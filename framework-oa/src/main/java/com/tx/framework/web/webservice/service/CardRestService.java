package com.tx.framework.web.webservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.common.mapper.JsonMapper;
import com.tx.framework.web.common.config.Constant;
import com.tx.framework.web.common.enums.ServiceCode;
import com.tx.framework.web.common.persistence.entity.Card;
import com.tx.framework.web.common.utils.DigestUtil;
import com.tx.framework.web.common.utils.WebServiceUtil;
import com.tx.framework.web.dao.card.CardDao;
import com.tx.framework.web.dao.cardtype.CardTypeDao;

@Service
@Transactional
public class CardRestService {

	@Autowired
	private CardTypeDao cardTypeDao;

	@Autowired
	private CardDao cardDao;

	/**
	 * 获取所有卡类型
	 * 
	 * @return
	 */
	public Map<String, Object> getCardTypes() {
		return WebServiceUtil.successMap(cardTypeDao.findAll());
	}

	/**
	 * 根据cardNo查找会员信息<br>
	 * 用于卡登录和查找会员信息接口
	 * 
	 * @param json
	 *            (cardNo、pwd(可选))
	 * @return
	 */
	public Map<String, Object> getByCardNo(String json, boolean isCheckPwd) {
		Map<String, String> jsonMap = JsonMapper.nonEmptyMapper().fromJson(json, Map.class);
		// 检查json解析是否正确
		if (jsonMap == null) {
			return WebServiceUtil.generateMap(ServiceCode.PARSE_ERROR);
		}
		// 检查必填参数
		if (StringUtils.isBlank(jsonMap.get("cardNo"))) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_PARAM);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("code", jsonMap.get("cardNo"));
		List<Card> cards = cardDao.findByParams(paraMap);
		// 无此卡号
		if (cards == null || cards.isEmpty()) {
			return WebServiceUtil.generateMap(ServiceCode.CARD_NOT_FOUND);
		}
		Card card = cards.get(0);
		// 校验卡状态
		if (!card.getStatus().equals(Constant.CARD_STATUS_NORMAL)) {
			return WebServiceUtil.generateMap(ServiceCode.ILLEGAL_CARD_STATUS);
		}
		// 卡未绑定客户
		if (StringUtils.isBlank(card.getCustomerId())) {
			return WebServiceUtil.generateMap(ServiceCode.NOT_BIND_CUSTOMER);
		}
		// 密码散列值、盐值
		String password, salt;
		password = card.getPwd();
		// 校验密码错误
		if (StringUtils.isNotBlank(password) && isCheckPwd) {
			salt = card.getSalt();
			String pwd = StringUtils.defaultString(jsonMap.get("pwd"));
			if (!DigestUtil.isCredentialsMatch(pwd, password, salt)) {
				return WebServiceUtil.generateMap(ServiceCode.AUTH_FAIL);
			}
		}
		// 成功返回卡信息
		return WebServiceUtil.successMap(card);
	}

}
