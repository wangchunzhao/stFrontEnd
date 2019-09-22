package com.qhc.steigenberger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.domain.CustomerClazz;
import com.qhc.steigenberger.domain.DealerOrder;
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
	private final static String SALES_TYPE_MAP = "sales_type";
	private final static String CURRENCY_MAP = "currencies";
	private final static String INCOTERMS_MAP = "incoterms";
	
	
	//
	private final static String PAGE_DEALER = "newOrder/newOrder";
	//
	private final static String FORM_ORDER_DEALER = "dealer";
	
	@Autowired
    UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	UserOperationInfoService userOperationInfoService;
	
	String newOrder="下订单";
	
	@RequestMapping("standardDiscount")
	public ModelAndView  goDealerOrder() {
		ModelAndView mv =new ModelAndView(PAGE_DEALER);
		Map<String,String> customerClassMap = orderService.getCustomerClazz();
		Map<String,String> salesTypeMap = orderService.getSalesType();
		Map<String,String> currencyMap = orderService.getCurrency();
		Map<String,String> incotermMap = orderService.getIncoterms();
		//
		mv.addObject(CUSTOMER_CLASS_MAP,customerClassMap);
		mv.addObject(SALES_TYPE_MAP,salesTypeMap);
		mv.addObject(CURRENCY_MAP,currencyMap);
		mv.addObject(INCOTERMS_MAP,incotermMap);
		//
		mv.addObject(FORM_ORDER_DEALER,new DealerOrder());
		return mv;
	}
	@PostMapping("dealer")
	@ResponseBody
	public String  submitDlealerOrder(@ModelAttribute DealerOrder order) {
		System.out.println("dealer");
		return PAGE_DEALER;
	}
	
	@RequestMapping("customers")
	@ResponseBody
	public JsonResult searchCustomer(String customerName) {
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
