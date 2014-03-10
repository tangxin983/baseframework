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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.web.common.base.AjaxPaginationController;
import com.tx.framework.web.common.utils.Constant;
import com.tx.framework.web.entity.Card;
import com.tx.framework.web.manage.service.card.CardService;
import com.tx.framework.web.manage.service.cardtype.CardTypeService;



/**
 * 挂失与解挂
 * @author tangx
 *
 */
@Controller
@RequestMapping(value = "/lostCard")
public class lostCardController extends AjaxPaginationController<Card>{

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
		cardService.lostOrHangCard(entity);
		if(entity.getStatus().equals(Constant.CARD_STATUS_LOST)){
			redirectAttributes.addFlashAttribute("message", "挂失成功");
		}else{
			redirectAttributes.addFlashAttribute("message", "解挂成功");
		}
		return "redirect:/" + getControllerContext();
	}
	 
	
	/**
	 * 设置显示正常和挂失状态的卡
	 */
	@Override
	protected void setExtraSearchParam(Map<String, Object> searchParams){
		searchParams.put("status1", Constant.CARD_STATUS_NORMAL);
		searchParams.put("status2", Constant.CARD_STATUS_LOST);
	}

}
