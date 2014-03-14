package com.tx.framework.web.manage.controller.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tx.framework.web.common.controller.AjaxPaginationController;
import com.tx.framework.web.common.persistence.entity.Customer;
import com.tx.framework.web.manage.service.customer.CustomerService;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends AjaxPaginationController<Customer> {

	private CustomerService customerService;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		super.setService(customerService);
		this.customerService = customerService;
	}
 

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@Override
	public String create(@Valid Customer entity, RedirectAttributes redirectAttributes) {
		customerService.saveCustomer(entity);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/customer";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@Override
	public String update(@Valid @ModelAttribute("entity")Customer entity, RedirectAttributes redirectAttributes) {
		customerService.updateCustomer(entity);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/customer";
	}
	 
}
