package com.qhc.steigenberger.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.UserService;

@Controller
@RequestMapping("newOrder")
public class NewOrderController {
	
	@Autowired
    UserService userService;
	
	@RequestMapping("/standardDiscount")
	public String permissionApply() {
		System.out.println("111111111");
		
		return "main";
	}
	
	
	
	@PostMapping("/standardDiscount1")
	@ResponseBody
	public JsonResult permissionApply1(HttpServletRequest request) {
		JsonResult jsonResult = null;
		try {
			String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
			User user = userService.selectUserIdentity(identityName);
			if(user.getRolesName().indexOf("1")>-1) {
				jsonResult.build(200,"success", 1);
			}else {
				jsonResult.build(500,"fail", 1);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonResult;
	}

}
