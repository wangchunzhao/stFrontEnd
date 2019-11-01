package com.qhc.steigenberger.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.domain.Characteristic;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Material;
import com.qhc.steigenberger.domain.MaterialGroups;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.OrderVersion;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.domain.form.AbsOrder;
import com.qhc.steigenberger.domain.form.BaseOrder;
import com.qhc.steigenberger.domain.form.DealerOrder;
import com.qhc.steigenberger.service.OrderService;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.service.UserService;
import com.qhc.steigenberger.util.PageHelper;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("order")
public class OrderController {

//	private final static String CUSTOMER_CLASS_MAP = "customer_classes";
//	private final static String SALES_TYPE_MAP = "sales_type";
//	private final static String CURRENCY_MAP = "currencies";
//	private final static String INCOTERMS_MAP = "incoterms";

	//
//	private final static String PAGE_DEALER = "newOrder/newOrder";
//	//
//	private final static String FORM_ORDER_DEALER = "dealerOrder";

	//
	private final static String FORM_SUBMIT = "submit";
//	private final static String FORM_SAVE = "save";
	private final static String FORM_MARGIN = "margin";
	private final static String FORM_WTW_MARGIN = "wtw";

	private final static String FORM_GROSS_PROFIT = "grossprofit";
	private final static String FORM_SUBMIT_TYPE_3 = "3";
	private final static String FORM_SUBMIT_TYPE_4 = "4";
	
	//权限
	private final static String allOrder = "11";//查看所有订单
	private final static String areaOrder = "12";//查看本区域订单
	private final static String selfOrder = "13";//查看自己的订单

	@Autowired
	UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	UserOperationInfoService userOperationInfoService;

	String newOrder = "下订单";

	@PostMapping("dealer")
	@ResponseBody
	public ModelAndView submitDlealerOrder(@RequestBody DealerOrder orderData, ModelAndView model,
			@RequestParam(value = "action", required = true) String action, HttpServletRequest request,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addObject("error", bindingResult.getFieldError().getDefaultMessage());
			return MenuController.goDealerOrder();
		}
		Object object = request.getSession().getAttribute(userService.SESSION_USERIDENTITY);
		//test data
		String domainId = "wangch";
		String saleOfficeCode = "1234";
		orderData.setCurrentUser(domainId);
		orderData.setUserOfficeCode(saleOfficeCode);

		switch (action) {

			case FORM_WTW_MARGIN:
				orderData.setSubmitType(4);
				break;
			case FORM_MARGIN:
				orderData.setSubmitType(3);
				break;
			case FORM_SUBMIT:
				orderData.setSubmitType(2);
				break;
			default:
				orderData.setSubmitType(1);
		}

		orderData.setOrderType(AbsOrder.ORDER_TYPE_CODE_DEALER);
		//test data
		orderData.setSalesCode(domainId);
		orderData.setSalesName("sales name");
		orderData.setCustomerClazzName("customerClazzName");
		orderData.setCustomerClazzCode("01");
		orderData.setContracterCode("经销商");
		orderData.setContracterName("contracterName");
		orderData.setCreateTime(new Date());
		orderData.setCurrentVersion("v123.13");
		
		orderService.saveOrder(orderData);

		return MenuController.goDealerOrder();
	}

	@RequestMapping("customers")
	@ResponseBody
	public PageHelper<Customer> searchCustomer(String clazzCode, String customerName, int pageNo) {
		PageHelper<Customer> cus = orderService.findCustomer(clazzCode, customerName, pageNo);
		return cus;
	}

	@RequestMapping("materials")
	@ResponseBody
	public PageHelper<Material> searchMateril(String materialName, int pageNo) {
		PageHelper<Material> cms = orderService.findMaterialsByName(materialName, pageNo);
		return cms;
	}

	@RequestMapping("material")
	@ResponseBody
	public Material getMaterilById(String code) {
		Material m = orderService.getMaterial(code);
		return m;
	}

	@PostMapping("createOrder")
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

	/**
	 * 查询订单版本历史
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "根据sequenceNumber查询订单版本历史", notes = "根据sequenceNumber查询订单版本历史")
	@GetMapping(value = "{sequenceNumber}/version")
	@ResponseBody
	public List<OrderVersion> orderVersions(@PathVariable String sequenceNumber) throws Exception {
		return orderService.findOrderVersions(sequenceNumber);
	}

	/**
	 * 查询订单版本历史
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "订单管理列表", notes = "订单管理列表")
	@PostMapping(value = "query")
	@ResponseBody
	public PageHelper<BaseOrder> searchOrder(@RequestBody OrderQuery query,HttpServletRequest request) throws Exception {
		String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
		User user = userService.selectUserIdentity(identityName);//identityName
		List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
		for(int i = 0; i < userOperationInfoList.size(); i++) {
			String operationId = userOperationInfoList.get(i).getOperationId();
			if(operationId.equals(allOrder)) {
				query.setSalesCode("");
				query.setOfficeCode("");
				break;
			}else if(operationId.equals(areaOrder)) {
				query.setSalesCode("");
				query.setOfficeCode(userOperationInfoList.get(i).getAttachedCode());
				break;
			}else {
				query.setSalesCode(String.valueOf(user.id));
			}
			
		}
		System.out.println(identityName+"======================");
		// 只查询最新的版本
		query.setLast(true);
		PageHelper<BaseOrder> order = orderService.findOrders(query);
		return order;
	}

	/**
	 * 查询订单
	 * 
	 * @param sequenceNumber
	 * @param version
	 * @param orderType
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询订单详情", notes = "查询订单详情")
	@GetMapping(value = "detail")
	@ResponseBody
	public AbsOrder getOrderDetail(@RequestParam String sequenceNumber, String version, String orderType)
			throws Exception {
		if (orderType == null || orderType.trim().length() == 0) {
			// get Order type with sequenceNumber
			orderType = orderService.getOrderType(sequenceNumber);
		}
		return orderService.findOrderDetail(sequenceNumber, version, orderType);
	}

	/**
	 * 推送订单到SAP
	 * 
	 * @param sequenceNumber
	 * @param version
	 * @param orderType
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "将订单推送到sap", notes = "将订单推送到sap")
	@PostMapping(value = "sap")
	@ResponseBody
	public String toSap(@RequestParam String sequenceNumber, String currentVersion, String orderType) throws Exception {
		return orderService.toSap(sequenceNumber, currentVersion);
	}

	/**
	 * 查询物料配置
	 * 
	 * @param clazzCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "material/configurations")
	@ResponseBody
	public List<Characteristic> findCharacteristic(String clazzCode, String materialCode) {
		return orderService.getCharactersByClazzCode(clazzCode, materialCode);

	}
	
	@RequestMapping(value = "user")
	@ResponseBody
	public User findUsetDetail(HttpServletRequest request) {
		String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
		User user = userService.selectUserIdentity(identityName);//identityName
		/*if(user!=null){
			user.setUserName("test");
			user.setTel("1111111");
			user.setUserIdentity("3121");
		}*/
		return user;

	}

	@ApiOperation(value = "计算毛利", notes = "计算毛利")
	@PostMapping(value = "grossprofit")
	@ResponseBody
	public List<MaterialGroups> calcGrossProfit(@RequestBody BaseOrder order) throws Exception {
		return orderService.calcGrossProfit(order);
	}

	@ApiOperation(value = "计算毛利", notes = "计算毛利")
	@PostMapping(value = "{sequenceNumber}/{version}/grossprofit")
	@ResponseBody
	public List<MaterialGroups> calcGrossProfit(String sequenceNumber, String version) throws Exception {
		return orderService.calcGrossProfit(sequenceNumber, version);
	}


}
