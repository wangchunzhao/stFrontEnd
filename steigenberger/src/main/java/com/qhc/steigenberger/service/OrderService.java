/**
 * 
 */
package com.qhc.steigenberger.service;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.qhc.steigenberger.domain.CustomerClazz;
import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.domain.Material;
import com.qhc.steigenberger.domain.MaterialGroups;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.OrderVersion;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.SalesGroup;
import com.qhc.steigenberger.domain.SalesOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.config.ApplicationConfig;
import com.qhc.steigenberger.domain.B2CComments;
import com.qhc.steigenberger.domain.BomExplosion;
import com.qhc.steigenberger.domain.Characteristic;
import com.qhc.steigenberger.domain.Contract;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.SpecialDelivery;
import com.qhc.steigenberger.domain.form.Order;
import com.qhc.steigenberger.service.FryeService;
import com.qhc.steigenberger.service.exception.ExternalServerInternalException;
import com.qhc.steigenberger.service.exception.URLNotFoundException;
import com.qhc.steigenberger.util.PageHelper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wang@dxc.com
 *
 */
@Service
public class OrderService {
	private ObjectMapper mapper = new ObjectMapper()
			.setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM))
			// 忽略不存在的字段
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

	private final static String ORDER_TYPE_DEALER = "ZH0D"; // '经销商订单'
	private final static String ORDER_TYPE_BULK = "ZH0M"; // '备货订单'
	private final static String ORDER_TYPE_KEYACCOUNT = "ZH0T"; // '大客户订单'

	private final static String URL_ORDER_OPTION = "order/option";
//	private final static String URL_SALES_TYPE = "order/salesType";
//	private final static String URL_CURRENCY = "currency";
//	private final static String URL_INCOTERMS = "incoterms";
	private final static String URL_ORDER = "order";
	private final static String URL_D_ORDER = "order/dOrder";
	private final static String URL_B2C_ORDER = "order/b2c";
	private final static String URL_SEPERATOR = ",";
	private final static String URL_PARAMETER_SEPERATOR = "/";

	private final static String URL_ABS_ORDER_SALESORDER = "order/salesOrder";
	private final static String URL_MATERIAL_CONFIG = "material/configurations";
	private final static String URL_MATERIAL_BOM = "material/configuration";

	@Autowired
	private FryeService fryeService;

	@Autowired
	FryeService dOrderervice;

	public Order getOrderBySequenceNumber(String sqNumber) {
		return null;
	}

	public OrderOption getOrderOption() {

		Object obj = fryeService.getInfo(URL_ORDER_OPTION, OrderOption.class);

		return (OrderOption) obj;
	}

	/**
	 * 
	 * @param name customer name
	 * @return customer list
	 */
	public PageHelper<Customer> findCustomer(String clazzCode, String name, int pageNo) {
		return (PageHelper<Customer>) fryeService.getInfo("customer/" + clazzCode
				+ "," + name + "," + String.valueOf(pageNo), PageHelper.class);
	}

	public PageHelper<Material> findMaterialsByName(String name, int pageNo) {
		if (name == null) {
			return null;
		}
		Map<String, String> pars = new HashMap<String, String>();
		pars.put("name", name);
		pars.put("pageNo", String.valueOf(pageNo));

		return (PageHelper<Material>) fryeService.postForm("material", pars, PageHelper.class);
	}

	public Material getMaterial(String code) {
		return (Material) fryeService.getInfo("material/" + code, Material.class);
	}

//	/**
//	 * 
//	 * @return customer class map
//	 */
//	public Map<String, String> getCustomerClazz() {
//		//return  fryeService.getInfo(URL_CUSTOMER_CLASS);
//		return null;
//	}
//	/**
//	 * 
//	 * @return sales type map
//	 */
//	public Map<String, String> getSalesType() {
//		return  fryeService.getMapData(URL_SALES_TYPE);
//	}
//	/**
//	 * 
//	 * @return currency map
//	 */
//	public Map<String, String> getCurrency() {
//		return  fryeService.getMapData(URL_CURRENCY);
//	}
//	/**
//	 * 
//	 * @return
//	 */
//	public Map<String,String> getIncoterms(){
//		return fryeService.getMapData(URL_INCOTERMS);
//	}
	/**
	 * 
	 * @param form : order
	 */
	public void saveOrder(Order form) {
		fryeService.postJason(URL_ORDER, form);
	}

	public Order getInfoById(String orderId) {

		return dOrderervice.getInfo(URL_D_ORDER + "/" + orderId, Order.class);
	}

	@Autowired
	ApplicationConfig config;

	public List<SalesGroup> getGrossProfitList(SalesOrder salesOrder) {

		String url = config.getFryeURL() + URL_ABS_ORDER_SALESORDER;
		@SuppressWarnings("unchecked")
		Flux<SalesGroup> sgFlux = WebClient.create().post().uri(url).contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(salesOrder), SalesOrder.class).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new URLNotFoundException()))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new ExternalServerInternalException()))
				.bodyToFlux(SalesGroup.class);
		return sgFlux.collectList().block();

	}

	public List<OrderVersion> findOrderVersions(String orderId) {
		String url = URL_ORDER + URL_PARAMETER_SEPERATOR + orderId + URL_PARAMETER_SEPERATOR + "version";
		return (List<OrderVersion>) fryeService.getInfo(url, List.class);
	}

	public PageHelper<Order> findOrders(OrderQuery query) {
		String url = "order/query";
		PageHelper page = (PageHelper) fryeService.postInfo(query, url, PageHelper.class);
		try {
			System.out.println(mapper.writeValueAsString(page));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		JavaType javaType = mapper.getTypeFactory().constructParametricType(PageHelper.class, Order.class);
//		page = mapper.convertValue(page, javaType);

		javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Order.class);
		page.setRows(mapper.convertValue(page.getRows(), javaType));

		return page;
	}

	public List<Characteristic> getCharactersByClazzCode(String clazzCode, String materialCode) {
		String url = URL_MATERIAL_CONFIG + URL_PARAMETER_SEPERATOR + clazzCode + ',' + materialCode;
		return (List<Characteristic>) fryeService.getInfo(url, List.class);
	}

//	public DealerOrder findDealerOrderDetail(String sequenceNumber, String version) {
//		return (DealerOrder)findOrderDetail(sequenceNumber, version, ORDER_TYPE_DEALER);
//	}

	public Order findOrderDetail(String sequenceNumber, String version, String orderType) {
		String url = URL_ORDER + URL_PARAMETER_SEPERATOR + "detail?sequenceNumber=" + sequenceNumber + "&version="
				+ version;
		Order order = null;
		String json = (String) fryeService.getInfo(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM));
		switch (orderType) {
		case ORDER_TYPE_DEALER:
//				String json = (String)fryeService.getInfo(url, String.class);
//				order = mapper.convertValue(order, DealerOrder.class);
			try {
				order = mapper.readValue(json, Order.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
//				order = (DealerOrder)fryeService.getInfo(url, DealerOrder.class);
			break;
		case ORDER_TYPE_KEYACCOUNT:
//				order = (KeyAccountOrder)fryeService.getInfo(url, KeyAccountOrder.class);
//				order = mapper.convertValue(order, KeyAccountOrder.class);
			try {
				order = mapper.readValue(json, Order.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case ORDER_TYPE_BULK:
//				order = (BulkOrder)fryeService.getInfo(url, BulkOrder.class);
//				order = mapper.convertValue(order, BulkOrder.class);
			try {
				order = mapper.readValue(json, Order.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			throw new RuntimeException(MessageFormat.format("Unknown order type [{0}]", orderType));
		}

		return order;
	}

	public String getOrderType(String sequenceNumber) {
		String url = URL_ORDER + URL_PARAMETER_SEPERATOR + "type?sequenceNumber=" + sequenceNumber;
		return (String) fryeService.getInfo(url, String.class);
	}

	public String toSap(String sequenceNumber, String version) {
		String url = URL_ORDER + URL_PARAMETER_SEPERATOR + "sap?sequenceNumber=" + sequenceNumber + "&version="
				+ version;
		return (String) fryeService.postInfo("", url, String.class);
	}

	public BomExplosion findBOMWithPrice(Map<String, String> pars) {
		return (BomExplosion) fryeService.postInfo(pars, URL_MATERIAL_BOM, HashMap.class);
	}

	public List<MaterialGroups> calcGrossProfit(Order order) {
		String url = URL_ORDER + URL_PARAMETER_SEPERATOR + "grossprofit";
		return (List<MaterialGroups>) fryeService.postForm(url, order, ArrayList.class);
	}

	public List<MaterialGroups> calcWtwGrossProfit(String sequenceNumber, String version) {
		String url = URL_ORDER + URL_PARAMETER_SEPERATOR + sequenceNumber + "/" + version + "/wtwgrossprofit";
		return (List<MaterialGroups>) fryeService.postForm(url, "", ArrayList.class);
	}

	public List<MaterialGroups> calcGrossProfit(String sequenceNumber, String version) {
		String url = URL_ORDER + URL_PARAMETER_SEPERATOR + sequenceNumber + "/" + version + "/grossprofit";
		return (List<MaterialGroups>) fryeService.postForm(url, "", ArrayList.class);
	}

	public void b2cCost(int isPro, String seqnum, String version,String operator, List<B2CComments> b2cs) {
		String url = URL_B2C_ORDER + "?isApproved="+isPro+"&seqnum="+seqnum+"&version="+version+"&operator="+operator;
		fryeService.postJason(url,b2cs);
		
	}

	public void enginingCost(String operator, boolean isPro, String seqnum, String version, double installation,
			double materials, double electrical, double coolroom, double maintanance) {
		// TODO Auto-generated method stub
		
	}

	public void submit(Order order) {
		// TODO Auto-generated method stub
		
	}
}
