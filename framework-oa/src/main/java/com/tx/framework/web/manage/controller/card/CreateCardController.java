package com.tx.framework.web.manage.controller.card;

import java.util.HashMap;
import java.util.List;
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

import com.tx.framework.common.mapper.JsonMapper;
import com.tx.framework.web.common.controller.AjaxPaginationController;
import com.tx.framework.web.common.persistence.entity.Card;
import com.tx.framework.web.common.persistence.entity.CardType;
import com.tx.framework.web.common.persistence.entity.RemoteMsg;
import com.tx.framework.web.manage.service.card.CardService;
import com.tx.framework.web.manage.service.cardtype.CardTypeService;

/**
 * 制卡
 * @author tangx
 *
 */
@Controller
@RequestMapping(value = "/createCard")
public class CreateCardController extends AjaxPaginationController<Card>{

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
		//卡类型下拉列表
		model.addAttribute("cardTypeList", cardTypeService.getValidUpCard(null));
		return super.list(model, request);
	}
	 
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@Override
	public String create(@Valid Card entity, RedirectAttributes redirectAttributes) {
		cardService.makeCard(entity);//制卡
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/" + getControllerContext();
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@Override
	public String update(@Valid @ModelAttribute("entity")Card entity, RedirectAttributes redirectAttributes) {
		cardService.updateMakeCard(entity);//暂时只能重置密码
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/" + getControllerContext();
	}
	
	/**
	 * 判断卡号是否重复
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "checkCode")
	@ResponseBody
	public String checkCode(@RequestParam("code") String code) {
		RemoteMsg msg = new RemoteMsg();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("code", code);
		if (cardService.getCountByParams(searchParams) == 0) {
			msg.setSuccess("true");
		} else {
			msg.setSuccess("false");
			msg.setMsg("卡号已存在");
		}
		return JsonMapper.nonDefaultMapper().toJson(msg);
	}
	
	/**
	 * 判断内部卡号是否重复
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "checkInnerCode")
	@ResponseBody
	public String checkInnerCode(@RequestParam("innerCode") String innerCode) {
		RemoteMsg msg = new RemoteMsg();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("innerCode", innerCode);
		if (cardService.getCountByParams(searchParams) == 0) {
			msg.setSuccess("true");
		} else {
			msg.setSuccess("false");
			msg.setMsg("内部卡号已存在");
		}
		return JsonMapper.nonDefaultMapper().toJson(msg);
	}
	
	/**
	 * 根据卡类型判断密码是否必须输入
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "checkPwd")
	@ResponseBody
	public String checkPwd(@RequestParam("cardTypeId") String cardTypeId) {
		CardType cardType = cardTypeService.getEntity(cardTypeId);
		if (cardType != null && cardType.getNeedpwd() == 1) {
			return "true";
		}else{
			return "false";
		}
	}
	
	/**
	 * 先判断卡是否能删除，如果能删除则删除，否则提示无法删除（status=0才能删）
	 * @param code
	 * @return
	 */
	@RequestMapping("delete")
	@Override
	public String multiDelete(@RequestParam("ids")List<String> ids,RedirectAttributes redirectAttributes) {
		if(cardService.isDeleteCard(ids)){
			cardService.multiDeleteEntity(ids);
			redirectAttributes.addFlashAttribute("message", "删除" + ids.size() + "条记录成功");
		}else{
			redirectAttributes.addFlashAttribute("message", "只能删除未使用的会员卡");
		}
		return "redirect:/" + getControllerContext();
	}
 

}
