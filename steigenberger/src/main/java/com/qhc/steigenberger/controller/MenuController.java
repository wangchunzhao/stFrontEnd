package com.qhc.steigenberger.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.Order;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.Parameter;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.ParameterService;
import com.qhc.steigenberger.service.RoleService;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.service.UserService;

@RequestMapping("menu")
@Controller
public class MenuController extends BaseController {
	
	private final static String ORDER_OPTION = "order_option";
	private final static String ORDER_DETAIL = "orderDetail";
	//操作订单的类型:1.新增, 2.查看,3修改 
	private final static String ORDER_OPERATION_TYPE = "orderOperationType";
	
	private final static String ERROR_PAGE = "error.html";

	@Autowired
	private UserService userService;
	
	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	UserOperationInfoService userOperationInfoService;
	
	@PostConstruct
	public void init() {
	}
	
	@RequestMapping("/specialApplication")
	public String index() {
		return "special/specialList";
	}
	
	@RequestMapping("/todo")
  	public String todo() {
  		return "todo/mytask";
  	}
	
	@RequestMapping("/newOrder")
  	public String newOrder() {
  		return "dealerOrder/orderForm";
  	}
	
	@RequestMapping("/orderManageList")
  	public String orderManage() {
  		return "orderManage/myOrder";
  	}
	
	@RequestMapping("/contract")
  	public String contract() {
  		return "contract/myContract";
  	}
	
	@RequestMapping("/purchaseAndSale")
  	public String purchaseAndSale() {
  		return "report/purchaseAndSale";
  	}
	
	@RequestMapping("/biddingDetail")
	public ModelAndView biddingDetail() {
		ModelAndView mv = new ModelAndView();
		// TODO 投标客户    
		
//		List<SapSalesOffice> areaList = sapSalesOfficeService.getList();
//		mv.addObject("areaList", areaList);
		mv.setViewName("report/biddingDetail");
		return mv;
	}
	
	@RequestMapping("/orderSummary")
	public ModelAndView orderSummary() {
		ModelAndView mv = new ModelAndView();
		// TODO 销售订单汇总    
		
//		List<SapSalesOffice> areaList = sapSalesOfficeService.getList();
//		mv.addObject("areaList", areaList);
		mv.setViewName("report/orderSummary");
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
		
		return "systemManage/userManage";
	}
	
	@RequestMapping("/permissionApply")
	public String permissionApply() {
		return "permission/permissionApply";
	}
	
	@RequestMapping("/settingIndex")
	public String index(Model model,HttpServletRequest request) {
		List<Parameter> parameters = parameterService.getList();
		model.addAttribute("parameters", parameters);
//		request.getSession().setAttribute("parameterSettings",parameters);
		return "systemManage/parameterSetting";
	}
	
	@RequestMapping("/roleIndex")
	public String index(@RequestParam(defaultValue = "0", name = "page") Integer page,
			@RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize,
			Role entity, 
			Model model,
			HttpServletRequest request) {
		model.addAttribute("role1", entity);
		model.addAttribute("datas", roleService.getPageableList(page, pageSize, entity));
		model.addAttribute("currentPath", "/menu/roleIndex?isActive="+entity.getIsActive());
//		model.addAttribute("operationList", this.getPermissions());
		
		return "systemManage/roleManage";
	}
	
	@RequestMapping("/freight")
  	public String freight() {
  		return "systemManage/freight";
  	}
	
	@RequestMapping("standardDiscount")
	public ModelAndView goDealerOrder() {
		ModelAndView mv = new ModelAndView();
		Result result = this.getOrderOption();
		if(!"ok".equals(result.getStatus())) {
			mv.setViewName(ERROR_PAGE);
			mv.addObject("msg", result.getMsg());
			return mv;
		}
		mv.setViewName(Constants.PAGE_DEALER);
		OrderOption oo = (OrderOption)result.getData();
		Order orderDetail = new Order();
		orderDetail.setCustomerClazz(Order.ORDER_CUSTOMER_DEALER_CODE);
		orderDetail.setOrderType(Order.ORDER_TYPE_DEALER);
		orderDetail.setStOrderType("1");
		mv.addObject(ORDER_OPTION,oo);
		mv.addObject(ORDER_DETAIL, orderDetail);
		mv.addObject(ORDER_OPERATION_TYPE, "1");	
		return mv;
	}
	
	@RequestMapping("nonStandardDiscount")
	public ModelAndView goNonStandardDealerOrder() {
		ModelAndView mv = new ModelAndView();
		Result result = this.getOrderOption();
		if(!"ok".equals(result.getStatus())) {
			mv.setViewName(ERROR_PAGE);
			mv.addObject("msg", result.getMsg());
			return mv;
		}
		mv.setViewName(Constants.PAGE_DEALER_NON_STANDARD);
		OrderOption oo = (OrderOption)result.getData();
		Order orderDetail = new Order();
		orderDetail.setCustomerClazz(Order.ORDER_CUSTOMER_DEALER_CODE);
		orderDetail.setOrderType(Order.ORDER_TYPE_DEALER);
		orderDetail.setStOrderType("2");
		mv.addObject(ORDER_OPTION,oo);	
		mv.addObject(ORDER_DETAIL, orderDetail);
		mv.addObject(ORDER_OPERATION_TYPE, "1");
		return mv;
	}
	
	@RequestMapping("directCustomerTenderOff")
	public ModelAndView goDirectCustomerTenderOff() {
		ModelAndView mv = new ModelAndView();
		Result result = this.getOrderOption();
		if(!"ok".equals(result.getStatus())) {
			mv.setViewName(ERROR_PAGE);
			mv.addObject("msg", result.getMsg());
			return mv;
		}
		mv.setViewName(Constants.PAGE_DIRECT_CUSTOMER_TENDER_OFFER);
		OrderOption oo = (OrderOption)result.getData();
		Order orderDetail = new Order();
		orderDetail.setCustomerClazz(Order.ORDER_CUSTOMER_KEY_ACCOUNT_CODE);
		orderDetail.setOrderType(Order.ORDER_TYPE_KEYACCOUNT);
		orderDetail.setStOrderType("3");
		mv.addObject(ORDER_OPTION,oo);	
		mv.addObject(ORDER_DETAIL, orderDetail);
		mv.addObject(ORDER_OPERATION_TYPE, "1");
		return mv;
	}
	
	@RequestMapping("directCustomerCreateOrder")
	public ModelAndView goDirectCustomerCreateOrder() {
		ModelAndView mv = new ModelAndView();
		Result result = this.getOrderOption();
		if(!"ok".equals(result.getStatus())) {
			mv.setViewName(ERROR_PAGE);
			mv.addObject("msg", result.getMsg());
			return mv;
		}
		mv.setViewName(Constants.PAGE_DIRECT_CUSTOMER_CREATE_ORDER);
		OrderOption oo = (OrderOption)result.getData();
		Order orderDetail = new Order();
		orderDetail.setCustomerClazz(Order.ORDER_CUSTOMER_KEY_ACCOUNT_CODE);
		orderDetail.setOrderType(Order.ORDER_TYPE_KEYACCOUNT);
		orderDetail.setStOrderType("4");
		mv.addObject(ORDER_OPTION,oo);	
		mv.addObject(ORDER_DETAIL, orderDetail);
		mv.addObject(ORDER_OPERATION_TYPE, "1");
		return mv;
	}
	
	@RequestMapping("stockUpOrder")
	public ModelAndView goStockUpOrder() {
		ModelAndView mv = new ModelAndView();
		Result result = this.getOrderOption();
		if(!"ok".equals(result.getStatus())) {
			mv.setViewName(ERROR_PAGE);
			mv.addObject("msg", result.getMsg());
			return mv;
		}
		mv.setViewName(Constants.PAGE_STOCK_UP);
		OrderOption oo = (OrderOption)result.getData();
		Order orderDetail = new Order();
		orderDetail.setOrderType(Order.ORDER_TYPE_BULK);
		orderDetail.setStOrderType("5");
		mv.addObject(ORDER_OPTION,oo);
		mv.addObject(ORDER_DETAIL, orderDetail);
		mv.addObject(ORDER_OPERATION_TYPE, "1");
		return mv;
	}
	
	
	@RequestMapping("/nologin")
  	public String nologin() {
  		return "noLogin";
  	}
}
