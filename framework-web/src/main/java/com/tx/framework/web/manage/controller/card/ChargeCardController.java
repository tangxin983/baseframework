package com.tx.framework.web.manage.controller.card;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.web.common.base.AjaxPaginationController;
import com.tx.framework.web.common.utils.Constant;
import com.tx.framework.web.entity.Card;
import com.tx.framework.web.manage.service.card.CardService;
import com.tx.framework.web.manage.service.cardtype.CardTypeService;



/**
 * 卡充值
 * @author tangx
 *
 */
@Controller
@RequestMapping(value = "/chargeCard")
public class ChargeCardController extends AjaxPaginationController<Card>{

	private CardService cardService;
	
	@Autowired
	private CardTypeService cardTypeService;
	
	@Autowired
	public void setCardService(CardService cardService) {
		super.setService(cardService);
		this.cardService = cardService;
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("entity")Card entity, RedirectAttributes redirectAttributes) {
		cardService.chargeCard(entity);//卡充值
		redirectAttributes.addFlashAttribute("message", "充值成功");
		return "redirect:/" + getControllerContext();
	}
	 
	/**
	 * 设置只显示正常卡
	 */
	@Override
	protected void setExtraSearchParam(Map<String, Object> searchParams){
		searchParams.put("status", Constant.CARD_STATUS_NORMAL);
	}

}
