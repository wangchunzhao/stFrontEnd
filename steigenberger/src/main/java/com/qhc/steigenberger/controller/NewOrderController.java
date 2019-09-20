package com.qhc.steigenberger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.domain.CustomerClazz;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.service.OrderService;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.service.UserService;

@Controller
@RequestMapping("newOrder")
public class NewOrderController {
	
	private final static String CUSTOMER_CLASS_MAP = "customer_classes";
	private final static String SALES_TYPE = "sales_type";
	
	@Autowired
    UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	UserOperationInfoService userOperationInfoService;
	
	String newOrder="下订单";
	
	@RequestMapping("standardDiscount")
	public ModelAndView  goDealerOrder() {
		ModelAndView mv =new ModelAndView("newOrder/newOrder");
		Map<String,String> customerClassMap = orderService.getCustomerClazz();
		Map<String,String> salesTypeMap = orderService.getSalesType();
		//
		mv.addObject(CUSTOMER_CLASS_MAP,customerClassMap);
		mv.addObject(SALES_TYPE,salesTypeMap);
		return mv;
	}
	
	@RequestMapping("customers")
	@ResponseBody
	public JsonResult searchCustomer(String name) {
		System.out.println("newOrder/standardDiscount");
		return null;
	}
	
	@PostMapping("/createOrder")
	@ResponseBody
	public JsonResult permissionApply1(HttpServletRequest request) {
		
		try {
//			String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
			User user = userService.selectUserIdentity("wangch");
			List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
			for(int i = 0; i < userOperationInfoList.size(); i++) {
				String operationName = userOperationInfoList.get(i).getOperationName();
				if(operationName.equals(newOrder)) {
					return JsonResult.build(200, "success", null);
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
		return JsonResult.build(401, "fail", null);
	}

}
