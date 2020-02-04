package com.qhc.steigenberger.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.Attachment;
import com.qhc.steigenberger.domain.BomQueryModel;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Material;
import com.qhc.steigenberger.domain.MaterialBom;
import com.qhc.steigenberger.domain.Order;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.OrderVersion;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.service.ContractService;
import com.qhc.steigenberger.service.OrderService;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.service.UserService;
import com.qhc.steigenberger.util.PageHelper;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController {
	Logger logger = LoggerFactory.getLogger(OrderController.class);
	
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
	private final static String orderStatus00="00";//订单新建保存
	private final static String orderStatus01="01";//客户经理提交待B2C审批
	private final static String orderStatus02="02";//客户经理提交待工程审批
	private final static String orderStatus03="03";//待支持经理审批
	private final static String orderStatus04="04";//提交到BPM
	private final static String orderStatus05="05";//BPM审批通过
	private final static String orderStatus06="06";//订单变更BPM审批通过
	private final static String orderStatus07="07";//
	private final static String orderStatus08="08";//
	private final static String orderStatus09="09";//已下推SAP
	private final static String orderStatus10="10";//ST驳回
	private final static String orderStatus11="11";//BPM驳回
	//订单类型
	private final static String ORDER_TYPE_DEALER = "ZH0D"; // '经销商订单'
	private final static String ORDER_TYPE_BULK = "ZH0M"; // '备货订单'
	private final static String ORDER_TYPE_KEYACCOUNT = "ZH0T"; // '大客户订单'
	
	private final static String ERROR_PAGE = "error.html"; 

	@Autowired
	UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	UserOperationInfoService userOperationInfoService;

	@Autowired
	ContractService contractService;

	/**
	 * 保存订单
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("save")
	@ResponseBody
	public Result saveOrder(@RequestBody Order order) {
		String identity = getUserIdentity();
		Result result;
		
		try {
			result = orderService.saveOrder(identity, order);
		} catch (Exception e) {
			logger.error("保存订单失败", e);
			result = Result.error("保存订单失败");
		}
		
		return result;
	}
	
	/**
	 * 保存订单
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("submit")
	@ResponseBody
	public Result submitOrder(@RequestBody Order order) {
		String identity = getUserIdentity();	
		Result result;
		
		try {
			result = orderService.submitOrder(identity, order);
		} catch (Exception e) {
			logger.error("提交订单失败", e);
			result = Result.error("提交订单失败");
		}
		
		return result;
	}
	
	/**
	 * 保存订单
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("submitbpm")
	public Result submitOrderToBpm(@RequestBody Order order) {
		String identity = this.getUserIdentity();
		
		Result result;
		
		try {
			result = orderService.submitbpmOrder(identity, order);
		} catch (Exception e) {
			logger.error("提交BPM失败", e);
			result = Result.error("提交BPM失败");
		}
		
		return result;
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
	@GetMapping(value = "{orderInfoId}")
	@ResponseBody
	public Result getOrderDetail(@PathVariable("orderInfoId") Integer orderInfoId)
			throws Exception {
		Result result;
		
		try {
			result = orderService.findOrderDetail(orderInfoId);
		} catch (Exception e) {
			logger.error("查询订单详情", e);
			result = Result.error("查询订单详情");
		}
		
		return result;
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
	@PostMapping(value = "{orderInfoId}/sap")
	@ResponseBody
	public Result toSap(@PathVariable("orderInfoId") Integer orderInfoId) throws Exception {
		String identity = getUserIdentity();
		Result result;
		
		try {
			result = orderService.sendToSap(identity, orderInfoId);
		} catch (Exception e) {
			logger.error("订单推送到sap", e);
			result = Result.error("订单推送到sap失败");
		}
		
		return result;
	}
	
	/**
	 * 订单变更
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("{orderInfoId}/upgrade")
	@ResponseBody
	public Result upgrade(@PathVariable("orderInfoId") Integer orderInfoId) {
		String identity = this.getUserIdentity();
		
		Result result;
		
		try {
			result = orderService.upgradeOrder(identity, orderInfoId);
		} catch (Exception e) {
			logger.error("订单变更失败", e);
			result = Result.error("订单变更失败");
		}
		
		return result;
	}
	
	/**
	 * 订单变更
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("{orderInfoId}/copy")
	@ResponseBody
	public Result copy(@PathVariable("orderInfoId") Integer orderInfoId) {
		String identity = this.getUserIdentity();
		
		Result result;
		
		try {
			result = orderService.copyOrder(identity, orderInfoId);
		} catch (Exception e) {
			logger.error("订单变更失败", e);
			result = Result.error("订单变更失败");
		}
		
		return result;
	}
	
	/**
	 * 修改订单报价状态
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("{orderInfoId}/quote/{status}")
	@ResponseBody
	public Result updateQuoteStatus(@PathVariable("orderInfoId") Integer orderInfoId, @PathVariable("status") String quoteStatus) {
		String identity = this.getUserIdentity();
		
		Result result;
		
		try {
			result = orderService.updateQuoteStatus(identity, orderInfoId, quoteStatus);
		} catch (Exception e) {
			logger.error("修改报价状态失败", e);
			result = Result.error("修改报价状态失败");
		}
		
		return result;
	}
	
	/**
	 * 订单对应的合同
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("{orderInfoId}/contract")
	@ResponseBody
	public Result contract(@PathVariable("orderInfoId") Integer orderInfoId) {
		String identity = this.getUserIdentity();
		
		Result result;
		
		try {
			result = contractService.findByOrderInfoId(orderInfoId);
		} catch (Exception e) {
			logger.error("查询订单合同失败", e);
			result = Result.error("查询订单合同失败");
		}
		
		return result;
	}

	@GetMapping("customers")
	@ResponseBody
	public Result searchCustomer(String clazzCode, String customerName, int pageNo, int limit) {
		Result result = null;
		result = orderService.findCustomer(clazzCode, customerName, pageNo,limit);
		return result;
	}

	@GetMapping("materials")
	@ResponseBody
	public Result searchMateril(String materialName, String industoryCode, int pageNo,int limit) {
		Result materials = orderService.findMaterialsByName(materialName,industoryCode, pageNo,limit);
		return materials;
	}

	@GetMapping("material")
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
			String identityName = this.getUserIdentity();
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
			String identityName = this.getUserIdentity();
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
	
	@ApiOperation(value = "上传订单资料", notes = "上传订单资料")
	@PostMapping(value = "upload")
	@ResponseBody
	public Result upload(@RequestParam(value = "file_data") MultipartFile file, HttpServletRequest request) {
		Result result = null;
		
		try {
			// 上传到文件名
			String fileName = file.getOriginalFilename();
			Attachment attachment = orderService.writeAttachment(fileName, file.getInputStream());
			
			result = Result.ok(attachment);
		} catch (Exception e) {
			logger.error("上传文件失败", e);
			result = result.error("上传文件失败");
		}
		
		return result;
	}
	
	@ApiOperation(value = "下载订单资料", notes = "下载订单资料")
	@GetMapping(value = "download")
	@ResponseBody
	public void download(@RequestParam String fileName, @RequestParam String fileUrl, HttpServletResponse response) {
		try {		
			InputStream in = orderService.readAttachment(fileUrl);
			
			// 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 实现文件下载
            try {
                OutputStream os = response.getOutputStream();
                IOUtils.copy(in, os);
                System.out.println("Download  successfully!");
            } catch (Exception e) {
                System.out.println("Download  failed!");
            } finally {
            	IOUtils.closeQuietly(in);
            }			
		} catch (Exception e) {
			logger.error("上传文件失败", e);
		}
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
		String operationId = ",";
		String identity = getUserIdentity();
		User user = userService.selectUserIdentity(identity);//identityName
		List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
		Boolean toSap = userOperationInfoList.stream().anyMatch(e->e.getOperationId().equals(TO_SAP));
		for(int i = 0; i < userOperationInfoList.size(); i++) {
			operationId += userOperationInfoList.get(i).getOperationId() + ",";
		}
		//判断权限确定能查看的订单范围
		if(operationId.contains(allOrder)) {//查询全部
			System.out.println("查询全部");
		}else if(operationId.contains(areaOrder)) {
			query.setOfficeCode(user.getOfficeCode());
		}else {
			query.setSalesCode(user.getUserIdentity());
		}
		// 只查询最新的版本
		query.setLast(true);
		Result result = orderService.findOrders(query);
		PageHelper<Order> order = (PageHelper<Order>)result.getData();
		if(!"".equals(query.getOfficeCode())) {
			List<Order> list = order.getRows();
			for(int i = 0; i < list.size(); i++) {
				((Map)list.get(i)).put("buttonControl", "0");
			}
		}
		List<Order> list = order.getRows();
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setButtonControl(String.valueOf(toSap));
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
	public PageHelper<Order> searchTodoOrder(@RequestBody OrderQuery query,HttpServletRequest request) throws Exception {
		String operationId = ",";
		//取得session中的登陆用户域账号，查询权限
		String identityName = this.getUserIdentity();
		User user = userService.selectUserIdentity(identityName);//identityName
		List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
		for(int i = 0; i < userOperationInfoList.size(); i++) {
			//查询权限id
			operationId += userOperationInfoList.get(i).getOperationId() + ",";
		}
		//判断权限确定能查看的订单范围
		if(operationId.contains(SUPPORT_Order)) {
			//支持经理
			List list = new ArrayList();
			list.add(orderStatus03);
			list.add(orderStatus11);
			query.setStatusList(list);
			//本人保存的
			List list2 = new ArrayList();
			list2.add(orderStatus00);
			query.setDominStatusList(list2);
			query.setDominSalesCode(user.getUserIdentity());
		}else if(operationId.contains(ENGINEER_Order)) {
			//工程人员
			List list = new ArrayList();
			list.add(orderStatus02);
			query.setStatusList(list);
//			query.setOrderType(orderType1);
			//本人保存的
			List list2 = new ArrayList();
			list2.add(orderStatus00);
			query.setDominStatusList(list2);
			query.setDominSalesCode(user.getUserIdentity());
		}else if(operationId.contains(B2C_Order)) {
			//B2C
			List list = new ArrayList();
			list.add(orderStatus01);
			query.setStatusList(list);
//			query.setB2c("1");
			//本人保存的
			List list2 = new ArrayList();
			list2.add(orderStatus00);
			query.setDominStatusList(list2);
			query.setDominSalesCode(user.getUserIdentity());
		}else {
			//客户经理
			List list = new ArrayList();
			list.add(orderStatus00);
			list.add(orderStatus10);
			list.add(orderStatus11);
			query.setStatusList(list);
			query.setSalesCode(user.getUserIdentity());
		}
		// 只查询最新的版本
		query.setLast(true);
		Result result = orderService.findOrders(query);
		PageHelper<Order> order = (PageHelper<Order>)result.getData();
		
		return order;
	}

	/**
	 * 查询物料配置
	 * 
	 * @param clazzCode
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "material/configurations")
	@ResponseBody
	public Result findCharacteristic(String clazzCode, String materialCode) {
		Result result =  orderService.getCharactersByClazzCode(clazzCode, materialCode);
		return result;
	}
	
	@GetMapping(value = "user")
	@ResponseBody
	public User findUsetDetail(HttpServletRequest request) {
		String identityName = this.getUserIdentity();
		User user = userService.selectUserIdentity(identityName);//identityName
		return user;

	}

//	@ApiOperation(value = "计算毛利", notes = "计算毛利")
//	@GetMapping(value = "{sequenceNumber}/{version}/wtwgrossprofit")
//	@ResponseBody
//	public List<MaterialGroups> calcWtwGrossProfit(@PathVariable String sequenceNumber, @PathVariable String version) throws Exception {
//		return orderService.calcWtwGrossProfit(sequenceNumber, version);
//	}
//
//	@ApiOperation(value = "计算毛利", notes = "计算毛利")
//	@GetMapping(value = "{sequenceNumber}/{version}/grossprofit")
//	@ResponseBody
//	public List<MaterialGroups> calcGrossProfit(@PathVariable String sequenceNumber, @PathVariable String version) throws Exception {
//		return orderService.calcGrossProfit(sequenceNumber, version);
//	}

	@ApiOperation(value = "计算毛利", notes = "计算毛利")
	@PostMapping(value = "grossprofit")
	@ResponseBody
	public Object calcGrossProfit(@RequestBody Order order) throws Exception {
		return orderService.calcGrossProfit(order);
	}
	
	@ApiOperation(value = "列表跳转不同订单页面", notes = "列表跳转不同订单页面")
	@GetMapping(value="toOrderPage")
	@ResponseBody
	public ModelAndView viewOrder(Integer orderInfoId,String orderOperationType, ModelAndView view) {
		ModelAndView mv = new ModelAndView();
		//操作订单的类型:1.新增, 2.查看,3修改 前台传入
		mv.addObject("orderOperationType", orderOperationType);
		OrderOption oo = null;
		Result optionResult = orderService.getOrderOption();
		if (optionResult.getStatus().equals("ok")) {
			oo = (OrderOption)optionResult.getData();	
		}else {
			mv.setViewName(ERROR_PAGE);
			mv.addObject("msg", optionResult.getMsg());
			return mv;
		}
		mv.addObject("order_option",oo);
		Result result = orderService.findOrderDetail(orderInfoId);
		Order order = null; 
		if (result.getStatus().equals("ok")) {
			order = (Order)result.getData();
			oo.setOrderTypeCode(order.getOrderType());	
		}else {
			mv.setViewName(ERROR_PAGE);
			mv.addObject("msg", optionResult.getMsg());
			return mv;
		}
		mv.addObject("orderDetail",order);
		String page = goOrderViewPageByType(order.getStOrderType());
		mv.setViewName(page);
		return mv;
	}
	
	private String goOrderViewPageByType(String stOrderType) {
		String pageName = "";
		switch(stOrderType){
		case "1":
			pageName = Constants.PAGE_DEALER;
		    break;
		case "2":
			pageName = Constants.PAGE_DEALER_NON_STANDARD;
		    break;
		case "3":
			pageName = Constants.PAGE_DIRECT_CUSTOMER_TENDER_OFFER;
		    break;
		case "4":
			pageName = Constants.PAGE_DIRECT_CUSTOMER_CREATE_ORDER;
		    break;
		case "5":
			pageName = Constants.PAGE_STOCK_UP;
		    break;
		}
		return pageName;
	}
	
	@ApiOperation(value = "修改订单", notes = "修改订单")
	@GetMapping(value="editOrder")
	@ResponseBody
	public ModelAndView editOrder(Integer orderInfoId, ModelAndView view) {
		ModelAndView mv = new ModelAndView("order/editOrder");
		OrderOption oo = null;//orderService.getOrderOption();
		mv.addObject("order_option",oo);
		Result result = orderService.findOrderDetail(orderInfoId);
		Order order = null; 
		if (result.getStatus().equals("ok")) {
			order = (Order)result.getData();
			oo.setOrderTypeCode(order.getOrderType());	
		}
		mv.addObject("orderDetail",order);
		return mv;
	}

    @ApiOperation(value = "修改库存订单", notes = "修改库存订单")
    @GetMapping(value="editStockUpOrder")
    @ResponseBody
    public ModelAndView editStockUpOrder(Integer orderInfoId,ModelAndView view) {
        ModelAndView mv = new ModelAndView("dealerOrder/editStockUpOrder");
        OrderOption oo = null;//orderService.getOrderOption();
        mv.addObject("order_option",oo);
		Result result = orderService.findOrderDetail(orderInfoId);
		Order order = null; 
		if (result.getStatus().equals("ok")) {
			order = (Order)result.getData();
			oo.setOrderTypeCode(order.getOrderType());	
		}
        mv.addObject("orderDetail",order);
        return mv;
    }
	
    @ApiOperation(value = "根据BOM配置获取新的Characteristic和value")
    @PostMapping(value = "material/configuration")
	@ResponseBody
    public Result findBOMWithPrice(@RequestBody BomQueryModel model)  throws Exception{
		/*
		 * Map<String, String> pars = new HashMap<>(); List<String> configCodes =
		 * model.getConfigCode(); List<String> configValueCodes =
		 * model.getConfigValueCode(); for(int i=0;i<configCodes.size();i++){
		 * pars.put(configCodes.get(i), configValueCodes.get(i)); } pars.put("bom_code",
		 * model.getBomCode()); return orderService.findBomPrice(pars);
		 */ 
    	//测试数据
    	MaterialBom bom = new MaterialBom();
    	bom.setPrice(1200.00);
    	bom.setTransferPrice(1300.00);
    	Result result = new Result();
    	result.setStatus("ok");
    	result.setData(bom);
    	return result;
    }
	
    @ApiOperation(value = "查询客户最后一条订单的地址列表")
    @PostMapping(value = "address/{customerCode}")
	@ResponseBody
    public Result findLastAddress(@PathVariable(name="customerCode", required = true)  String customerCode)  throws Exception{
    	OrderQuery query = new OrderQuery();
    	query.setCustomerCode(customerCode);
    	query.setIncludeDetail(true);
		// 只查询最新的版本
		query.setLast(true);
		query.setPageNo(0);
		query.setPageSize(1);
		query.setOrderByClause("oi.create_time desc");
		
		Result result = orderService.findOrders(query);
		if (result.getStatus().equals("ok")) {
			PageHelper<Order> orders = (PageHelper<Order>)result.getData();
			List<Order> list = orders.getRows();
			if (list != null && list.size() > 0) {
				result.setData(list.get(0).getDeliveryAddress());
			} else {
				result.setData(new ArrayList());
			}
		}
		
		return result;
    }

}
