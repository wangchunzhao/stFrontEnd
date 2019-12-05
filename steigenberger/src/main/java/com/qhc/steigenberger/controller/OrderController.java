package com.qhc.steigenberger.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

import com.qhc.steigenberger.domain.BomExplosion;
import com.qhc.steigenberger.domain.Characteristic;
import com.qhc.steigenberger.domain.Configuration;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Material;
import com.qhc.steigenberger.domain.MaterialGroups;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.OrderVersion;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.domain.form.AbsOrder;
import com.qhc.steigenberger.domain.form.BaseOrder;
import com.qhc.steigenberger.domain.form.BomQueryModel;
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
	private final static String allOrder = "1011";//查看所有订单
	private final static String areaOrder = "1012";//查看本区域订单
	private final static String B2C_Order = "1014";//B2C审核订单
	private final static String ENGINEER_Order = "1015";//工程人员审批订单
	private final static String SUPPORT_Order = "1016";//支持经理审批订单
	private final static String CREATE_Order = "1017";//下订单
	private final static String CREATE_BEIHUO_Order = "1018";//下备货订单
	
	//订单状态
	private final static String orderStatus0000="0000";//订单新建保存
	private final static String orderStatus0100="0100";//客户经理提交待支持经理审核
	private final static String orderStatus0110="0110";//客户经理提交待B2C审核
	private final static String orderStatus0111="0111";//客户经理提交待B2C和工程审核
	private final static String orderStatus0112="0112";//工程人员提交待B2C审核
	private final static String orderStatus0101="0101";//客户经理提交待工程审核
	private final static String orderStatus0102="0102";//工程提交待支持经理审核
	private final static String orderStatus0120="0120";//B2C提交待待支持经理审核
	private final static String orderStatus0121="0121";//B2C提交待工程审核
	private final static String orderStatus0122="0122";//待支持经理审核
	
	private final static String orderStatus9="9";//已下推SAP
	private final static String orderStatus10="10";//BPM驳回
	private final static String orderStatus11="11";//Selling Tool驳回
	private final static String orderStatus12="12";//待支持经理审批
	
	//订单类型
	private final static String orderType1="ZH0D";//经销商订单
	private final static String orderType2="ZH0M";//备货订单
	private final static String orderType3= "ZH0T";//大客户订单


	@Autowired
	UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	UserOperationInfoService userOperationInfoService;

	@PostMapping("dealer")
	@ResponseBody
	public ModelAndView submitDlealerOrder(@RequestBody DealerOrder orderData, ModelAndView model,
			@RequestParam(value = "action", required = true) String action, HttpServletRequest request,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addObject("error", bindingResult.getFieldError().getDefaultMessage());
			return MenuController.goDealerOrder();
		}
		String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
		User user = userService.selectUserIdentity(identityName);
		orderData.setCurrentUser(user.getUserIdentity());
		if(user.getRegion()!=null){
			orderData.setUserOfficeCode(user.getRegion().getCode());
		}
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
	//判断有没有新建经销商和直签订单的权限
	@PostMapping("createOrder")
	@ResponseBody
	public JsonResult permissionApply1(HttpServletRequest request) {

		try {
			String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
			User user = userService.selectUserIdentity(identityName);
			List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
			for (int i = 0; i < userOperationInfoList.size(); i++) {
				String operationId = userOperationInfoList.get(i).getOperationId();
				if (operationId.equals(CREATE_Order)) {
					return JsonResult.build(200, "success", null);
				}
			}
//			if(user.getRoles()!=null&user.getRoles().size()>0) {
//				JsonResult.build(200,"success", 1);
//			}else {
//				JsonResult.build(500,"fail", 1);
//			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return JsonResult.build(401,"success", 1);
	}
	//判断有没有下备货订单的权限
	@PostMapping("createBeiOrder")
	@ResponseBody
	public JsonResult permissionApply(HttpServletRequest request) {
		try {
			String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
			User user = userService.selectUserIdentity(identityName);
			List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
			for (int i = 0; i < userOperationInfoList.size(); i++) {
				String operationId = userOperationInfoList.get(i).getOperationId();
				if (operationId.equals(CREATE_BEIHUO_Order)) {
					return JsonResult.build(200, "success", null);
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return JsonResult.build(401,"success", 1);
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
	 * 查询订单管理列表
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
				query.setSalesCode(user.getUserIdentity());
			}
			
		}
		System.out.println(identityName+"======================");
		// 只查询最新的版本
		query.setLast(true);
		PageHelper<BaseOrder> order = orderService.findOrders(query);
		if(!"".equals(query.getOfficeCode())) {
			List<BaseOrder> list = order.getRows();
			for(int i = 0; i < list.size(); i++) {
				((Map)list.get(i)).put("buttonControl", "0");
			}
		}
		return order;
	}
	
	/**
	 * 查询代办订单列表
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "代办列表", notes = "代办订单列表")
	@PostMapping(value = "queryTodo")
	@ResponseBody
	public PageHelper<BaseOrder> searchTodoOrder(@RequestBody OrderQuery query,HttpServletRequest request) throws Exception {
		//取得session中的登陆用户域账号，查询权限
		String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
		User user = userService.selectUserIdentity(identityName);//identityName
		List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
		for(int i = 0; i < userOperationInfoList.size(); i++) {
			//查询权限id
			String operationId = userOperationInfoList.get(i).getOperationId();
			if(operationId.equals(SUPPORT_Order)) {
				//支持经理
				List list = new ArrayList();
				list.add(orderStatus0120);
				list.add(orderStatus0122);
				list.add(orderStatus0100);
				list.add(orderStatus0102);
				query.setStatusList(list);
				//本人保存的
				List list2 = new ArrayList();
				list2.add(orderStatus0000);
				query.setDominStatusList(list2);
				query.setDominSalesCode(user.getUserIdentity());
				query.setSalesCode("");
				break;
			}else if(operationId.equals(ENGINEER_Order)) {
				//工程人员
				List list = new ArrayList();
				list.add(orderStatus0111);
				list.add(orderStatus0101);
				list.add(orderStatus0121);
				query.setStatusList(list);
//				query.setOrderType(orderType1);
				//本人保存的
				List list2 = new ArrayList();
				list2.add(orderStatus0000);
				query.setDominStatusList(list2);
				query.setDominSalesCode(user.getUserIdentity());
				query.setSalesCode("");
				break;
			}else if(operationId.equals(B2C_Order)) {
				//B2C
				List list = new ArrayList();
				list.add(orderStatus0110);
				list.add(orderStatus0111);
				list.add(orderStatus0112);
				query.setStatusList(list);
//				query.setB2c("1");
				//本人保存的
				List list2 = new ArrayList();
				list2.add(orderStatus0000);
				query.setDominStatusList(list2);
				query.setDominSalesCode(user.getUserIdentity());
				query.setSalesCode("");
				break;
			}else {
				//客户经理
				List list = new ArrayList();
				list.add(orderStatus0000);
				list.add(orderStatus11);
				query.setStatusList(list);
				query.setSalesCode(user.getUserIdentity());
			}
			
		}
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
	public AbsOrder getOrderDetail(@RequestParam String sequenceNumber, @RequestParam String version, @RequestParam(required = false) String orderType)
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
	public String toSap(@RequestParam String sequenceNumber, @RequestParam String currentVersion, String orderType) throws Exception {
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
		/*List<Characteristic> ch = new ArrayList<>();
		Characteristic c = new Characteristic();
		c.setName("test");
		c.setCode("2");
		c.setOptional(true);
		Set<Configuration> s = new HashSet<>();
		Configuration con = new Configuration();
		con.setCode("222");
		con.setName("default");
		con.setDefault(true);
		Configuration con1 = new Configuration();
		con1.setCode("2222");
		con1.setName("test");
		con1.setDefault(false);
		s.add(con);
		s.add(con1);
		c.setConfigs(s);
		ch.add(c);
		return ch;*/
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
	@RequestMapping(value = "{sequenceNumber}/{version}/wtwgrossprofit")
	@ResponseBody
	public List<MaterialGroups> calcWtwGrossProfit(@PathVariable String sequenceNumber, @PathVariable String version) throws Exception {
		return orderService.calcWtwGrossProfit(sequenceNumber, version);
	}

	@ApiOperation(value = "计算毛利", notes = "计算毛利")
	@RequestMapping(value = "{sequenceNumber}/{version}/grossprofit")
	@ResponseBody
	public List<MaterialGroups> calcGrossProfit(@PathVariable String sequenceNumber, @PathVariable String version) throws Exception {
		return orderService.calcGrossProfit(sequenceNumber, version);
	}

	@ApiOperation(value = "计算毛利", notes = "计算毛利")
	@PostMapping(value = "grossprofit")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<MaterialGroups> calcGrossProfit(@RequestBody BaseOrder order) throws Exception {
		return orderService.calcGrossProfit(order);
	}
	
	@ApiOperation(value = "查看订单", notes = "查看订单")
	@RequestMapping(value="viewOrder")
	@ResponseBody
	public ModelAndView viewOrder(String sequenceNumber, String orderType,String version,ModelAndView view) {
		ModelAndView mv = new ModelAndView("dealerOrder/dealerOrderView");
		OrderOption oo = orderService.getOrderOption();
		mv.addObject("order_option",oo);
		if (orderType == null || orderType.trim().length() == 0) {
			orderType = orderService.getOrderType(sequenceNumber);
		}
		AbsOrder order = orderService.findOrderDetail(sequenceNumber, version, orderType);
		oo.setOrderTypeCode(orderType);	
		mv.addObject("orderDetail",order);
		return mv;
	}
	
	@ApiOperation(value = "修改订单", notes = "修改订单")
	@RequestMapping(value="editOrder")
	@ResponseBody
	public ModelAndView editOrder(String sequenceNumber, String orderType,String version,ModelAndView view) {
		ModelAndView mv = new ModelAndView("dealerOrder/editDealerOrder");
		OrderOption oo = orderService.getOrderOption();
		mv.addObject("order_option",oo);
		if (orderType == null || orderType.trim().length() == 0) {
			orderType = orderService.getOrderType(sequenceNumber);
		}
		DealerOrder order = (DealerOrder) orderService.findOrderDetail(sequenceNumber, version, orderType);
		oo.setOrderTypeCode(orderType);	
		mv.addObject("orderDetail",order);
		return mv;
	}

    @ApiOperation(value = "修改库存订单", notes = "修改库存订单")
    @RequestMapping(value="editStockUpOrder")
    @ResponseBody
    public ModelAndView editStockUpOrder(String sequenceNumber, String orderType,String version,ModelAndView view) {
        ModelAndView mv = new ModelAndView("dealerOrder/editStockUpOrder");
        OrderOption oo = orderService.getOrderOption();
        mv.addObject("order_option",oo);
        if (orderType == null || orderType.trim().length() == 0) {
            orderType = orderService.getOrderType(sequenceNumber);
        }
        AbsOrder order = orderService.findOrderDetail(sequenceNumber, version, orderType);
        oo.setOrderTypeCode(orderType);
        mv.addObject("orderDetail",order);
        return mv;
    }
	
	@ApiOperation(value = "审批订单", notes = "审批订单")
	@RequestMapping(value="approveOrder")
	@ResponseBody
	public ModelAndView approveOrder(HttpServletRequest request,String sequenceNumber, String orderType,String version,ModelAndView view) {
		//取得session中的登陆用户域账号，查询权限
		String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
		User user = userService.selectUserIdentity(identityName);//identityName
		List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
		OrderOption oo = orderService.getOrderOption();
		if (orderType == null || orderType.trim().length() == 0) {
			orderType = orderService.getOrderType(sequenceNumber);
		}
		DealerOrder order = (DealerOrder) orderService.findOrderDetail(sequenceNumber, version, orderType);
		boolean standard = false;
		if(!StringUtils.isEmpty(oo.getStandardDiscount())) {
			standard = (Double.valueOf(oo.getStandardDiscount())==order.getMainDiscount()&&Double.valueOf(oo.getStandardDiscount())==order.getBodyDiscount());
		}
		oo.setOrderTypeCode(orderType);	
		ModelAndView mv = null;
		for(int i = 0; i < userOperationInfoList.size(); i++) {
			//查询权限id
			String operationId = userOperationInfoList.get(i).getOperationId();
			if(operationId.equals(SUPPORT_Order)) {
				//支持经理
				if(orderType.equals(orderType1)&&standard) {
					mv = new ModelAndView("dealerOrder/supportManagerOrder");
				}else if(orderType.equals(orderType1)&&standard) {
					mv = new ModelAndView("nonStandardDealerOrder/supportManagerOrder");
				}else if(orderType.equals(orderType2)) {
					mv = new ModelAndView("stockUpOrder/supportManagerOrder");
				}else if(orderType.equals(orderType3)) {
					mv = new ModelAndView("directCustomerOrder/supportManagerOrder");
				}			
				break;
			}else if(operationId.equals(ENGINEER_Order)) {
				//工程人员
				mv = new ModelAndView("directCustomerOrder/engineerManagerOrder");
				break;
			}else if(operationId.equals(B2C_Order)) {
				//B2C			
				if(orderType.equals(orderType1)&&standard) {
					mv = new ModelAndView("dealerOrder/b2cManagerOrder");
				}else if(orderType.equals(orderType1)&&standard) {
					mv = new ModelAndView("nonStandardDealerOrder/b2cManagerOrder");
				}else if(orderType.equals(orderType2)) {
					mv = new ModelAndView("stockUpOrder/b2cManagerOrder");
				}else if(orderType.equals(orderType3)) {
					mv = new ModelAndView("directCustomerOrder/b2cManagerOrder");
				}	
				break;
			}else {
				//客户经理
				if(orderType.equals(orderType1)&&standard) {
					mv = new ModelAndView("dealerOrder/customerManagerOrder");
				}else if(orderType.equals(orderType1)&&standard) {
					mv = new ModelAndView("nonStandardDealerOrder/customerManagerOrder");
				}else if(orderType.equals(orderType2)) {
					mv = new ModelAndView("stockUpOrder/customerManagerOrder");
				}else if(orderType.equals(orderType3)) {
					mv = new ModelAndView("directCustomerOrder/customerManagerOrder");
				}	
			}
			
		}
		mv.addObject("order_option",oo);
		mv.addObject("orderDetail",order);
		return mv;
	}
	
    @ApiOperation(value = "根据BOM配置获取新的Characteristic和value")
    @PostMapping(value = "material/configuration")
    @ResponseStatus(HttpStatus.OK)
    public BomExplosion findBOMWithPrice(@RequestBody BomQueryModel model)  throws Exception{
            Map<String, String> pars = new HashMap<>();
            List<String> configCodes = model.getConfigCode();
            List<String> configValueCodes = model.getConfigValueCode();
            for(int i=0;i<configCodes.size();i++){
                pars.put(configCodes.get(i), configValueCodes.get(i));
            }
            pars.put("bom_code", model.getBomCode());
            return orderService.findBOMWithPrice(pars);    
    }



}
