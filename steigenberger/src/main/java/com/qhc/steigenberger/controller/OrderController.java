package com.qhc.steigenberger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.CustomerClazz;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.SalesGroup;
import com.qhc.steigenberger.domain.SalesOrder;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.domain.form.AbsOrder;
import com.qhc.steigenberger.domain.form.DealerOrder;
import com.qhc.steigenberger.service.OrderService;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.service.UserService;
import com.qhc.steigenberger.util.PageHelper;

@Controller
@RequestMapping("order")
public class OrderController {

	private final static String CUSTOMER_CLASS_MAP = "customer_classes";
	private final static String SALES_TYPE_MAP = "sales_type";
	private final static String CURRENCY_MAP = "currencies";
	private final static String INCOTERMS_MAP = "incoterms";

	//
	private final static String PAGE_DEALER = "newOrder/newOrder";
	//
	private final static String FORM_ORDER_DEALER = "dealerOrder";

	//
	private final static String FORM_SUBMIT = "submit";
	private final static String FORM_SAVE = "save";
	private final static String FORM_MARGIN = "margin";
	private final static String FORM_WTW_MARGIN = "wtw";
	
	
	private final static String FORM_GROSS_PROFIT = "grossProfit";
	private final static String FORM_SUBMIT_TYPE_3 = "3";
	private final static String FORM_SUBMIT_TYPE_4 = "4";

	@Autowired
	UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	UserOperationInfoService userOperationInfoService;

	String newOrder = "下订单";

	@RequestMapping("standardDiscount")
	public ModelAndView goDealerOrder() {
		ModelAndView mv = new ModelAndView(PAGE_DEALER);
//		Map<String, String> customerClassMap = orderService.getCustomerClazz();
//		Map<String, String> salesTypeMap = orderService.getSalesType();
//		Map<String, String> currencyMap = orderService.getCurrency();
//		Map<String, String> incotermMap = orderService.getIncoterms();
//		//
//		mv.addObject(CUSTOMER_CLASS_MAP, customerClassMap);
//		mv.addObject(SALES_TYPE_MAP, salesTypeMap);
//		mv.addObject(CURRENCY_MAP, currencyMap);
//		mv.addObject(INCOTERMS_MAP, incotermMap);
//		//
//		String s = this.searchCustomer("he",0);
//		//
//		mv.addObject(FORM_ORDER_DEALER, new DealerOrder());
		
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setSubmitType(Integer.valueOf(FORM_SUBMIT_TYPE_3));
		List<SalesGroup> list = orderService.getGrossProfitList(salesOrder);
		mv.addObject(FORM_GROSS_PROFIT, list);
		
		return mv;
	}

	@PostMapping("dealer")
	@ResponseBody
	public ModelAndView submitDlealerOrder(@Valid @ModelAttribute DealerOrder form, ModelAndView model,
			@RequestParam(value = "action", required = true) String action, HttpServletRequest request,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addObject("error", bindingResult.getFieldError().getDefaultMessage());
			return MenuController.goDealerOrder();
		}
		Object object = request.getSession().getAttribute(userService.SESSION_USERIDENTITY);
		// if(object!=null && object instanceof String) {
		// String domainId = (String)object;
		String domainId = "wangch";

		form.setCurrentUser(domainId);
		//
		switch (action) {
		case FORM_WTW_MARGIN:
			form.setSubmitType(4);
			break;
		case FORM_MARGIN:
			form.setSubmitType(3);
			break;
		case FORM_SUBMIT:
			form.setSubmitType(2);
			break;
		default:
			form.setSubmitType(1);
		}

		form.setOrderType(AbsOrder.ORDER_TYPE_CODE_DEALER);
		orderService.saveOrder(form);

		return MenuController.goDealerOrder();
	}

	@RequestMapping("customers")
	@ResponseBody
	public String searchCustomer(String customerName,int pageNo) {
		PageHelper<Customer> cus = orderService.findCustomer(customerName,pageNo);
		return cus.toString();
	}

	@PostMapping("/createOrder")
	@ResponseBody
	public JsonResult permissionApply1(HttpServletRequest request) {

		try {
//			String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
			User user = userService.selectUserIdentity("wangch");
			List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
			for (int i = 0; i < userOperationInfoList.size(); i++) {
				String operationName = userOperationInfoList.get(i).getOperationName();
				if (operationName.equals(newOrder)) {
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
