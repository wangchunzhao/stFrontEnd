package com.qhc.steigenberger.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Material;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.OrderVersion;
import com.qhc.steigenberger.domain.SalesOrder;
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
		String domainId = "wangch";
		String officeCode = "1234";
		orderData.setCurrentUser(domainId);
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
		orderService.saveOrder(orderData);

		return MenuController.goDealerOrder();
	}

	@RequestMapping("customers")
	@ResponseBody
	public PageHelper<Customer> searchCustomer(String clazzCode,String customerName,int pageNo) {
		PageHelper<Customer> cus = orderService.findCustomer(clazzCode,customerName,pageNo);
		return cus;
	}
	@RequestMapping("materials")
	@ResponseBody
	public PageHelper<Material> searchMateril(String materialName,int pageNo) {
		PageHelper<Material> cms = orderService.findMaterialsByName(materialName,pageNo);
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
     * @param orderId
     * @return
     * @throws Exception
     */
    @ApiOperation(value="根据orderId查询订单版本历史", notes="根据orderId查询订单版本历史")
    @GetMapping(value = "{orderId}/version")
	@ResponseBody
    public List<OrderVersion> orderVersions(@PathVariable String orderId) throws Exception {	
    	return orderService.findOrderVersions(orderId);
    }

    
    /**
     * 查询订单版本历史
     * @param orderId
     * @return
     * @throws Exception
     */
    @ApiOperation(value="查询订单", notes="查询订单")
    @PostMapping(value = "query")
	@ResponseBody
    public List<BaseOrder> searchOrder(@RequestBody OrderQuery query) throws Exception {	
    	return orderService.findOrders(query);
    }
    
    /**
     * 查询订单
     * @param orderId
     * @return
     * @throws Exception
     */
    @ApiOperation(value="查询Dealer订单详情", notes="查询Dealer订单详情")
    @PostMapping(value = "order/dealerOrder")
    @ResponseStatus(HttpStatus.OK)
    public DealerOrder getDealerOrder(@RequestParam String sequenceNumber, @RequestParam String versionId) throws Exception {	
    	return orderService.findDealerOrder(sequenceNumber, versionId);
    }
    
    /**
     * 查询物料配置
     * @param clazzCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "material/configurations")
    @ResponseBody
    public List<Characteristic> findCharacteristic(String clazzCode,String materialCode) {
        return orderService.getCharactersByClazzCode(clazzCode,materialCode);
        
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
