package com.tx.framework.web.manage.controller.cardtype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.web.common.base.AjaxPaginationController;
import com.tx.framework.web.entity.CardType;
import com.tx.framework.web.entity.RemoteMsg;
import com.tx.framework.web.manage.service.cardtype.CardTypeService;




@Controller
@RequestMapping(value = "/cardType")
public class CardTypeController extends AjaxPaginationController<CardType>{

	private CardTypeService cardTypeService;
	
	@Autowired
	public void setCardTypeService(CardTypeService cardTypeService) {
		super.setService(cardTypeService);
		this.cardTypeService = cardTypeService;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@Override
	public String create(@Valid CardType entity, RedirectAttributes redirectAttributes) {
		cardTypeService.saveCardType(entity);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/" + getControllerContext();
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@Override
	public String update(@Valid CardType entity, RedirectAttributes redirectAttributes) {
		cardTypeService.updateCardType(entity);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/" + getControllerContext();
	}
	
	/**
	 * 上级卡下拉菜单
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getUpCard")
	@ResponseBody
	public String getUpCard(@RequestParam(value = "id", defaultValue = "-1") String id) {
		StringBuffer select = new StringBuffer("<option value=''>请选择...</option>");
		List<CardType> cardTypes = null;
		if(id.equals("-1")){
			cardTypes = cardTypeService.getValidUpCard(null);
		}else{
			cardTypes = cardTypeService.getValidUpCard(id);
		}
		if(!cardTypes.isEmpty()){
			for(CardType cardType : cardTypes){
				select.append("<option value='" + cardType.getId() + "'>" + cardType.getCardtypename() + "</option>");
			}
		}
		return select.toString();
	}
	
	@RequestMapping(value = "delete/{id}")
	@Override
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		cardTypeService.deleteCardType(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/" + getControllerContext();
	}
	 
	@RequestMapping("delete")
	@Override
	public String multiDelete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		cardTypeService.MultiDeleteCardType(ids);
		redirectAttributes.addFlashAttribute("message", "删除" + ids.size() + "条记录成功");
		return "redirect:/" + getControllerContext();
	}
	
	@RequestMapping(value = "checkName")
	@ResponseBody
	public RemoteMsg checkCode(@RequestParam("cardtypename") String cardTypeName, 
			@RequestParam("cardtypeid") String id) {
		RemoteMsg msg = new RemoteMsg();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("cardTypeName", cardTypeName);
		searchParams.put("id", id);
		if(cardTypeService.getCountByParams(searchParams) == 0){
			msg.setSuccess("true");
		} else {
			msg.setSuccess("false");
			msg.setMsg("此名称已存在");
		}
		return msg;
	}

}
