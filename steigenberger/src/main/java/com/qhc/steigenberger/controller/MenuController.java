package com.qhc.steigenberger.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.Parameter;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.SalesGroup;
import com.qhc.steigenberger.domain.SalesOffice;
import com.qhc.steigenberger.domain.SalesOrder;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.form.Order;
import com.qhc.steigenberger.service.OperationService;
import com.qhc.steigenberger.service.OrderService;
import com.qhc.steigenberger.service.ParameterService;
import com.qhc.steigenberger.service.RoleService;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.service.UserService;
import com.qhc.steigenberger.util.CacheUtil;

@RequestMapping("menu")
@Controller
public class MenuController {
	
	private final static String ORDER_OPTION = "order_option";
	private final static String ORDER_DETAIL = "orderDetail";
	//
	private final static String PAGE_DEALER = "dealerOrder/newOrder";
	private final static String PAGE_DEALER_NON_STANDARD = "nonStandardDealerOrder/newNonStandardOrder";
	
	private final static String PAGE_DEALER_DIRECT_CUSTOMER = "directCustomerOrder/directCustomerOrder";
	//
	private final static String FORM_ORDER_DEALER = "dealerOrder";
	
	private final static String specialApplication="special/specialList";
	private final static String todo="todo/mytask";
	private final static String newOrder="dealerOrder/orderForm";
	private final static String orderManageList="orderManage/myOrder";
	private final static String contract="contract/myContract";
	private final static String purchaseAndSale="report/purchaseAndSale";
	private final static String biddingDetail="report/biddingDetail";
	private final static String userIndex="systemManage/userManage";
	private final static String permissionApply="permission/permissionApply";
	private final static String settingIndex="systemManage/parameterSetting";
	private final static String roleIndex="systemManage/roleManage";
	private final static String freight="systemManage/freight";

	private final static String stockUpOrder = "dealerOrder/stockUpOrder";

	//
	private final static String FORM_SUBMIT = "submit";
	private final static String FORM_SAVE = "save";
	private final static String FORM_MARGIN = "margin";
	private final static String FORM_WTW_MARGIN = "wtw";

	private final static String FORM_GROSS_PROFIT = "grossProfit";
	private final static String FORM_SUBMIT_TYPE_3 = "3";
	private final static String FORM_SUBMIT_TYPE_4 = "4";
	
	
	
	@Autowired
	private OrderService orderService;
	
	private static OrderService staticOrderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private CacheUtil cacheUtil;
	
	@Autowired
	UserOperationInfoService userOperationInfoService;
	
	@PostConstruct
	public void init() {
		staticOrderService = this.orderService;
	}
	
	
	
	@RequestMapping("/specialApplication")
	public String index() {
		return specialApplication;
	}
	
	@RequestMapping("/todo")
  	public String todo() {
  		return todo;
  	}
	
	@RequestMapping("/newOrder")
  	public String newOrder() {
  		return newOrder;
  	}
	
	@RequestMapping("/orderManageList")
  	public String orderManage() {
  		return orderManageList;
  	}
	
	@RequestMapping("/contract")
  	public String contract() {
  		return contract;
  	}
	
	@RequestMapping("/purchaseAndSale")
  	public String purchaseAndSale() {
  		return purchaseAndSale;
  	}
	
	@RequestMapping("/biddingDetail")
	public ModelAndView biddingDetail() {
		ModelAndView mv = new ModelAndView();
		//投标客户    TODO
		
//		List<SapSalesOffice> areaList = sapSalesOfficeService.getList();
//		mv.addObject("areaList", areaList);
		mv.setViewName(biddingDetail);
		return mv;
	}
	
	@RequestMapping("/userIndex")
	public String index(@RequestParam(defaultValue = "0", name = "page") Integer page,
			@RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize,
			User entity, 
			Model model,
			HttpServletRequest request) {
		
		model.addAttribute("user1", entity);
		//result list
		model.addAttribute("datas", userService.selectAndPage(page, pageSize, entity));
		String userMail = entity.getUserMail()==null?"":entity.getUserMail();
		String userIdentity = entity.getUserIdentity()==null?"":entity.getUserIdentity();
		String pp = "/user/index?isActive="+entity.getIsActive()+"&userMail="+userMail+"&userIdentity="+userIdentity;
		model.addAttribute("currentPath", pp);
		
		return userIndex;
	}
	
	@RequestMapping("/permissionApply")
	public String permissionApply() {
		return permissionApply;
	}
	
	@RequestMapping("/settingIndex")
	public String index(Model model,HttpServletRequest request) {
		List<Parameter> parameters = parameterService.getList();
		model.addAttribute("parameters", parameters);
//		request.getSession().setAttribute("parameterSettings",parameters);
		return settingIndex;
	}
	
	@RequestMapping("/roleIndex")
	public String index(@RequestParam(defaultValue = "0", name = "page") Integer page,
			@RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize,
			Role entity, 
			Model model,
			HttpServletRequest request) {
		model.addAttribute("role1", entity);
		model.addAttribute("datas", roleService.getPageableList(page, pageSize, entity));
		model.addAttribute("currentPath", "/role/index?isActive="+entity.getIsActive());
		model.addAttribute("operationList", operationService.getList());
		
		return roleIndex;
	}
	
	@RequestMapping("/freight")
  	public String freight() {
  		return freight;
  	}
	
	@RequestMapping("standardDiscount")
	public static ModelAndView goDealerOrder() {
		ModelAndView mv = new ModelAndView(PAGE_DEALER);
		OrderOption oo = staticOrderService.getOrderOption();
		Order orderDetail = new Order();
		orderDetail.setCustomerClazz(Order.ORDER_CUSTOMER_DEALER_CODE);
		orderDetail.setOrderType(Order.ORDER_TYPE_DEALER);
		mv.addObject(ORDER_OPTION,oo);
		mv.addObject(ORDER_DETAIL, orderDetail);
		return mv;
	}
	
	@RequestMapping("nonStandardDiscount")
	public static ModelAndView goNonStandardDealerOrder() {
		ModelAndView mv = new ModelAndView(PAGE_DEALER_NON_STANDARD);
		OrderOption oo = staticOrderService.getOrderOption();
		Order orderDetail = new Order();
		orderDetail.setCustomerClazz(Order.ORDER_CUSTOMER_DEALER_CODE);
		orderDetail.setOrderType(Order.ORDER_TYPE_DEALER);
		mv.addObject(ORDER_OPTION,oo);	
		mv.addObject(ORDER_DETAIL, orderDetail);
		return mv;
	}
	
	@RequestMapping("directCustomerOrder")
	public static ModelAndView goDirectCustomerOrder() {
		ModelAndView mv = new ModelAndView(PAGE_DEALER_DIRECT_CUSTOMER);
		OrderOption oo = staticOrderService.getOrderOption();
		Order orderDetail = new Order();
		orderDetail.setCustomerClazz(Order.ORDER_CUSTOMER_KEY_ACCOUNT_CODE);
		orderDetail.setOrderType(Order.ORDER_TYPE_KEYACCOUNT);
		mv.addObject(ORDER_OPTION,oo);	
		mv.addObject(ORDER_DETAIL, orderDetail);
		return mv;
	}
	
	@RequestMapping("/stockUpOrder")
	public static ModelAndView goStockUpOrder() {
		ModelAndView mv = new ModelAndView(stockUpOrder);
		OrderOption oo = staticOrderService.getOrderOption();
		Order orderDetail = new Order();
		orderDetail.setOrderType(Order.ORDER_TYPE_BULK);
		mv.addObject(ORDER_OPTION,oo);
		mv.addObject(ORDER_DETAIL, orderDetail);
		return mv;
	}
	
	
	@RequestMapping("/nologin")
  	public String nologin() {
  		return "noLogin";
  	}
}
