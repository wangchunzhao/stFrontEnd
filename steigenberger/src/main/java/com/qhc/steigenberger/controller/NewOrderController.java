package com.qhc.steigenberger.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.service.UserService;

@Controller
@RequestMapping("newOrder")
public class NewOrderController {
	
	@Autowired
    UserService userService;
	
	@Autowired
	UserOperationInfoService userOperationInfoService;
	
	String newOrder="下订单";
	
	@RequestMapping("/standardDiscount")
	public String permissionApply() {
		System.out.println("111111111");
		
		return "main";
	}
	
	
	
	@PostMapping("/createOrder")
	@ResponseBody
	public JsonResult permissionApply1(HttpServletRequest request) {
		JsonResult jsonResult = null;
		try {
			String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
			User user = userService.selectUserIdentity(identityName);
			List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
			for(int i = 0; i < userOperationInfoList.size(); i++) {
				String operationName = userOperationInfoList.get(i).getOperationName();
				if(operationName.equals(newOrder)) {
					jsonResult.build(200,"success", 1);
				}
			}
//			if(user.getRoles()!=null&user.getRoles().size()>0) {
//				jsonResult.build(200,"success", 1);
//			}else {
//				jsonResult.build(500,"fail", 1);
//			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return jsonResult;
	}

}
