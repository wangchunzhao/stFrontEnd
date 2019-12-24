package com.qhc.steigenberger.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.B2CComments;
import com.qhc.steigenberger.domain.BomExplosion;
import com.qhc.steigenberger.domain.Characteristic;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Material;
import com.qhc.steigenberger.domain.MaterialGroups;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.OrderVersion;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.domain.form.BomQueryModel;
import com.qhc.steigenberger.domain.form.Order;
import com.qhc.steigenberger.service.OrderService;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.service.UserService;
import com.qhc.steigenberger.util.PageHelper;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController {
	//权限
	private final static String allOrder = "1011";//查看所有订单
	private final static String areaOrder = "1012";//查看本区域订单
	private final static String B2C_Order = "1014";//B2C审核订单
	private final static String ENGINEER_Order = "1015";//工程人员审批订单
	private final static String SUPPORT_Order = "1016";//支持经理审批订单
	private final static String CREATE_Order = "1017";//下订单
	private final static String CREATE_BEIHUO_Order = "1018";//下备货订单
	private final static String TO_SAP = "1020";//推送订单到
	
	//订单状态
	private final static String orderStatus0010="0010";//订单新建保存
	private final static String orderStatus0001="0001";//订单新建保存
	private final static String orderStatus0011="0011";//订单新建保存
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
    //st驳回
    private final static String orderStatus1000="1000";//
    private final static String orderStatus1010="1010";//
    private final static String orderStatus1011="1011";//
    private final static String orderStatus1012="1012";//
    private final static String orderStatus1001="1001";//
    private final static String orderStatus1002="1002";//
    private final static String orderStatus1020="1020";//
    private final static String orderStatus1021="1021";//
    private final static String orderStatus1022="1022";//
	
	
	private final static String ORDER_TYPE_DEALER = "ZH0D"; // '经销商订单'
	private final static String ORDER_TYPE_BULK = "ZH0M"; // '备货订单'
	private final static String ORDER_TYPE_KEYACCOUNT = "ZH0T"; // '大客户订单'


	@Autowired
	UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	UserOperationInfoService userOperationInfoService;

	@PostMapping("dealer")
	@ResponseBody
	public ModelAndView submitDlealerOrder(@RequestBody Order orderData, ModelAndView model,
			@RequestParam(value = "action", required = true) String action, HttpServletRequest request,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addObject("error", bindingResult.getFieldError().getDefaultMessage());
			return MenuController.goDealerOrder();
		}
		String identityName = request.getSession().getAttribute(Constants.IDENTITY).toString();
		User user = userService.selectUserIdentity(identityName);
//		orderData.setCurrentUser(user.getUserIdentity());
		if(user.getRegion()!=null){
			orderData.setUserOfficeCode(user.getRegion().getCode());
		}
		
		orderService.saveOrder(orderData);
		return MenuController.goDealerOrder();
	}
	
	/**
	 * 保存订单
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("")
	public ModelAndView saveOrder(@RequestBody Order order, HttpServletRequest request) {
		String identityName = getValueByAttribute(request, Constants.IDENTITY).toString();
		
		orderService.saveOrder(order);
		
		if (order.getCustomerClazz().equals(Order.ORDER_CUSTOMER_DEALER_CODE)) {
			if (order.getIsSpecial() == 1) {
				return MenuController.goNonStandardDealerOrder();
			} else {
				return MenuController.goDealerOrder();
			}
		}
		
		return null;
	}
	
	/**
	 * 保存订单
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("submit")
	public ModelAndView submitOrder(@RequestBody Order order, HttpServletRequest request) {
		String identityName = getValueByAttribute(request, Constants.IDENTITY).toString();
		
		orderService.submit(order);
		
		if (order.getCustomerClazz().equals(Order.ORDER_CUSTOMER_DEALER_CODE)) {
			if (order.getIsSpecial() == 1) {
				return MenuController.goNonStandardDealerOrder();
			} else {
				return MenuController.goDealerOrder();
			}
		}
		
		return null;
	}

	@RequestMapping("customers")
	@ResponseBody
	public Result searchCustomer(String clazzCode, String customerName, int pageNo, int limit) {
		Result result = null;
		result = orderService.findCustomer(clazzCode, customerName, pageNo,limit);
		return result;
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
			String identityName = request.getSession().getAttribute(Constants.IDENTITY).toString();
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
			String identityName = request.getSession().getAttribute(Constants.IDENTITY).toString();
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
	public PageHelper<Order> searchOrder(@RequestBody OrderQuery query,HttpServletRequest request) throws Exception {
		String identityName = request.getSession().getAttribute(Constants.IDENTITY).toString();
		User user = userService.selectUserIdentity(identityName);//identityName
		List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
		Boolean toSap = userOperationInfoList.stream().anyMatch(e->e.getOperationId().equals(TO_SAP));
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
		//状态是需求单新建保存
		if("0000".equals(query.getStatus())) {
			List list = new ArrayList();
			list.add(orderStatus0000);
			list.add(orderStatus0010);
			list.add(orderStatus0001);
			list.add(orderStatus0011);
			query.setStatusList(list);
			query.setStatus("");
		}
		System.out.println(identityName+"======================");
		// 只查询最新的版本
		query.setLast(true);
		PageHelper<Order> order = orderService.findOrders(query);
//		if(!"".equals(query.getOfficeCode())) {
//			List<BaseOrder> list = order.getRows();
//			for(int i = 0; i < list.size(); i++) {
//				((Map)list.get(i)).put("buttonControl", "0");
//			}
//		}
		List<Order> list = order.getRows();
//		for(int i = 0; i < list.size(); i++) {
//			list.get(i).setButtonControl(String.valueOf(toSap));
//		}
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
	public PageHelper<Order> searchTodoOrder(@RequestBody OrderQuery query,HttpServletRequest request) throws Exception {
		//取得session中的登陆用户域账号，查询权限
		String identityName = request.getSession().getAttribute(Constants.IDENTITY).toString();
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
				list.add(orderStatus0010);
				list.add(orderStatus0001);
				list.add(orderStatus0011);
				
                list.add(orderStatus1000);
                list.add(orderStatus1010);
                list.add(orderStatus1011);
                list.add(orderStatus1012);
                list.add(orderStatus1001);
                list.add(orderStatus1002);
                list.add(orderStatus1020);
                list.add(orderStatus1021);
                list.add(orderStatus1022);
				query.setStatusList(list);
				query.setSalesCode(user.getUserIdentity());
			}
			
		}
		// 只查询最新的版本
		query.setLast(true);
		PageHelper<Order> order = orderService.findOrders(query);
		
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
	public Order getOrderDetail(@RequestParam String sequenceNumber, @RequestParam String version, @RequestParam(required = false) String orderType)
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
		return orderService.getCharactersByClazzCode(clazzCode, materialCode);

	}
	
	@RequestMapping(value = "user")
	@ResponseBody
	public User findUsetDetail(HttpServletRequest request) {
		String identityName = request.getSession().getAttribute(Constants.IDENTITY).toString();
		User user = userService.selectUserIdentity(identityName);//identityName
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
	public List<MaterialGroups> calcGrossProfit(@RequestBody Order order) throws Exception {
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
		Order order = orderService.findOrderDetail(sequenceNumber, version, orderType);
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
		Order order = (Order) orderService.findOrderDetail(sequenceNumber, version, orderType);
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
        Order order = orderService.findOrderDetail(sequenceNumber, version, orderType);
        oo.setOrderTypeCode(orderType);
        mv.addObject("orderDetail",order);
        return mv;
    }
	
	@ApiOperation(value = "审批订单", notes = "审批订单")
	@RequestMapping(value="approveOrder")
	@ResponseBody
	public ModelAndView approveOrder(HttpServletRequest request,String sequenceNumber, String orderType,String version,ModelAndView view) {
		//取得session中的登陆用户域账号，查询权限
		String identityName = request.getSession().getAttribute(Constants.IDENTITY).toString();
		User user = userService.selectUserIdentity(identityName);//identityName
		List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
		OrderOption oo = orderService.getOrderOption();
		if (orderType == null || orderType.trim().length() == 0) {
			orderType = orderService.getOrderType(sequenceNumber);
		}
		Order order = null;
		
		switch (orderType) {
		case ORDER_TYPE_DEALER:
			order =  orderService.findOrderDetail(sequenceNumber, version, orderType);
			break;
		case ORDER_TYPE_KEYACCOUNT:
			order = orderService.findOrderDetail(sequenceNumber, version, orderType);
			break;
		case ORDER_TYPE_BULK:
			order = orderService.findOrderDetail(sequenceNumber, version, orderType);
			break;
		default:
			throw new RuntimeException(MessageFormat.format("Unknown order type [{0}]", orderType));
		}
		boolean standard = false;
		if(ORDER_TYPE_DEALER.equals(orderType)&&!StringUtils.isEmpty(oo.getStandardDiscount())) {
			standard = (Double.valueOf(oo.getStandardDiscount())==order.getMainDiscount()&&Double.valueOf(oo.getStandardDiscount())==order.getBodyDiscount());
		}
		oo.setOrderTypeCode(orderType);	
		ModelAndView mv = null;
		for(int i = 0; i < userOperationInfoList.size(); i++) {
			//查询权限id
			String operationId = userOperationInfoList.get(i).getOperationId();
			if(operationId.equals(SUPPORT_Order)) {
				//支持经理
				if(orderType.equals(ORDER_TYPE_DEALER)&&standard) {
					mv = new ModelAndView("dealerOrder/supportManagerOrder");
				}else if(orderType.equals(ORDER_TYPE_DEALER)&&standard) {
					mv = new ModelAndView("nonStandardDealerOrder/supportManagerOrder");
				}else if(orderType.equals(ORDER_TYPE_BULK)) {
					mv = new ModelAndView("stockUpOrder/supportManagerOrder");
				}else if(orderType.equals(ORDER_TYPE_KEYACCOUNT)) {
					mv = new ModelAndView("directCustomerOrder/supportManagerOrder");
				}			
				break;
			}else if(operationId.equals(ENGINEER_Order)) {
				//工程人员
				mv = new ModelAndView("directCustomerOrder/engineerManagerOrder");
				break;
			}else if(operationId.equals(B2C_Order)) {
				//B2C			
				if(orderType.equals(ORDER_TYPE_DEALER)&&standard) {
					mv = new ModelAndView("dealerOrder/b2cManagerOrder");
				}else if(orderType.equals(ORDER_TYPE_DEALER)&&standard) {
					mv = new ModelAndView("nonStandardDealerOrder/b2cManagerOrder");
				}else if(orderType.equals(ORDER_TYPE_BULK)) {
					mv = new ModelAndView("stockUpOrder/b2cManagerOrder");
				}else if(orderType.equals(ORDER_TYPE_KEYACCOUNT)) {
					mv = new ModelAndView("directCustomerOrder/b2cManagerOrder");
				}	
				break;
			}else {
				//客户经理
				if(orderType.equals(ORDER_TYPE_DEALER)&&standard) {
					mv = new ModelAndView("dealerOrder/customerManagerOrder");
				}else if(orderType.equals(ORDER_TYPE_DEALER)&&standard) {
					mv = new ModelAndView("nonStandardDealerOrder/customerManagerOrder");
				}else if(orderType.equals(ORDER_TYPE_BULK)) {
					mv = new ModelAndView("stockUpOrder/customerManagerOrder");
				}else if(orderType.equals(ORDER_TYPE_KEYACCOUNT)) {
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
    
    @ApiOperation(value = "B2c审核订单", notes = "B2c审核订单")
	@PostMapping(value = "b2c")
	@ResponseStatus(HttpStatus.OK)
	public void approvedByB2C(HttpServletRequest request,@RequestParam int isApproved,@RequestParam String seqnum,@RequestParam String version,@RequestBody List<B2CComments> b2cs) throws Exception{
    	String identityName = request.getSession().getAttribute(Constants.IDENTITY).toString();//
		orderService.b2cCost(isApproved, seqnum, version, identityName, b2cs);
	}
	
	
	@ApiOperation(value = "工程经理审核", notes = "工程经理审核订单")
	@PostMapping(value = "order/engineering")
	@ResponseStatus(HttpStatus.OK)
	public void approvedByEngineering(@RequestParam String operator,@RequestParam int isApproved,@RequestParam String seqnum,@RequestParam String version,@RequestParam double installation,@RequestParam double materials,@RequestParam double electrical ,@RequestParam double coolroom,@RequestParam double maintanance) throws Exception{
		boolean isPro = false;
		if(isApproved!=0)
			isPro = true;
		orderService.enginingCost(operator,isPro, seqnum, version, installation,materials,electrical,coolroom,maintanance);
	}


}
