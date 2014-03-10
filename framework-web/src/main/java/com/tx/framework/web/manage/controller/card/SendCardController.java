package com.tx.framework.web.manage.controller.card;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.web.common.base.AjaxPaginationController;
import com.tx.framework.web.common.utils.Constant;
import com.tx.framework.web.entity.Card;
import com.tx.framework.web.entity.RemoteMsg;
import com.tx.framework.web.manage.service.card.CardService;
import com.tx.framework.web.manage.service.cardtype.CardTypeService;



/**
 * 发卡
 * @author tangx
 *
 */
@Controller
@RequestMapping(value = "/sendCard")
public class SendCardController extends AjaxPaginationController<Card>{

	private CardService cardService;
	
	@Autowired
	private CardTypeService cardTypeService;
	
	@Autowired
	public void setCardService(CardService cardService) {
		super.setService(cardService);
		this.cardService = cardService;
	}
	
	@RequestMapping
	@Override
	public String list(Model model, ServletRequest request) {
		return super.list(model, request);
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@Override
	public String update(@Valid @ModelAttribute("entity")Card entity, RedirectAttributes redirectAttributes) {
		cardService.sendCard(entity);//发卡
		redirectAttributes.addFlashAttribute("message", "发卡成功");
		return "redirect:/" + getControllerContext();
	}
	
	/**
	 * 判断实收金额是否大于押金要求
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "checkPledge")
	@ResponseBody
	public RemoteMsg checkPledge(@RequestParam("cardId") String id, @RequestParam("realBalance") Double realBalance) {
		RemoteMsg msg = new RemoteMsg();
		Card card = cardService.getEntity(id);
		if (realBalance >= card.getPrecharge()) {
			msg.setSuccess("true");
		} else {
			msg.setSuccess("false");
			msg.setMsg("实收金额必须大等于押金");
		}
		return msg;
	}
 
	/**
	 * 设置只显示未使用卡
	 */
	@Override
	protected void setExtraSearchParam(Map<String, Object> searchParams){
		searchParams.put("status", Constant.CARD_STATUS_UNUSE);
	}


}
