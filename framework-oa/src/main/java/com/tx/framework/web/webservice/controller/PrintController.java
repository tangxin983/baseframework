package com.tx.framework.web.webservice.controller;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/print")
public class PrintController {

	@RequestMapping
	public String list(Model model, ServletRequest request) {
		return "print/test";
	}
}
