package com.tx.framework.web.manage.controller.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tx.framework.web.common.base.AjaxPaginationController;
import com.tx.framework.web.entity.Card;
import com.tx.framework.web.manage.service.card.CardService;
import com.tx.framework.web.manage.service.cardtype.CardTypeService;




@Controller
@RequestMapping(value = "/card")
public class CardController extends AjaxPaginationController<Card>{

	private CardService cardService;
	
	@Autowired
	private CardTypeService cardTypeService;
	
	@Autowired
	public void setCardService(CardService cardService) {
		super.setService(cardService);
		this.cardService = cardService;
	}
}
