package com.qhc.steigenberger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.domain.JsonResult;

@Controller
@RequestMapping("newOrder")
public class NewOrderController {
	
	
	
	@RequestMapping("/standardDiscount")
	public String permissionApply() {
		System.out.println("111111111");
		
		return "main";
	}
	
	
	
	@PostMapping("/standardDiscount1")
	@ResponseBody
	public JsonResult permissionApply1() {
		
		return JsonResult.build(200,"success", 1);
	}

}
