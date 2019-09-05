package com.qhc.steigenberger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/newOrder")
public class NewOrderController {
	
	
	
	@RequestMapping("/standardDiscount")
	public ModelAndView permissionApply() {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("msg","对不起，您没有下订单权限!");
		modelAndView.setViewName("newOrder/orderForm");
		return modelAndView;
	}

}
