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
import com.qhc.steigenberger.domain.DOrder;
import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.domain.Material;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.OrderVersion;
import com.qhc.steigenberger.domain.SalesGroup;
import com.qhc.steigenberger.domain.SalesOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.config.ApplicationConfig;
import com.qhc.steigenberger.domain.BomExplosion;
import com.qhc.steigenberger.domain.Characteristic;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.SpecialDelivery;
import com.qhc.steigenberger.domain.form.AbsOrder;
import com.qhc.steigenberger.domain.form.BaseOrder;
import com.qhc.steigenberger.domain.form.BulkOrder;
import com.qhc.steigenberger.domain.form.DealerOrder;
import com.qhc.steigenberger.domain.form.KeyAccountOrder;
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
	private final static String ORDER_TYPE_DEALER = "ZH0D"; //'经销商订单'
	private final static String ORDER_TYPE_BULK = "ZH0M"; // '备货订单'
	private final static String ORDER_TYPE_KEYACCOUNT = "ZH0T"; // '大客户订单'
	
	private final static String URL_CUSTOMER = "customer";
	private final static String URL_MATERIAL = "material";
	private final static String URL_ORDER_OPTION = "order/option";
//	private final static String URL_SALES_TYPE = "order/salesType";
//	private final static String URL_CURRENCY = "currency";
//	private final static String URL_INCOTERMS = "incoterms";
	private final static String URL_ORDER = "order";
	private final static String URL_D_ORDER = "order/dOrder";
	private final static String URL_SEPERATOR = ",";
	private final static String URL_PARAMETER_SEPERATOR = "/";
	
	private final static String URL_ABS_ORDER_SALESORDER = "order/salesOrder";
	private final static String URL_MATERIAL_CONFIG = "material/configurations";
	private final static String URL_MATERIAL_BOM = "material/configuration";
	
	@Autowired
	private FryeService fryeService;
	
	@Autowired
	FryeService<DOrder> dOrderervice;
	
	
	public DealerOrder getOrderBySequenceNumber(String sqNumber) {
		return null;
	}
	public OrderOption getOrderOption() {
		
		Object obj =fryeService.getInfo(URL_ORDER_OPTION, OrderOption.class);
		
		return (OrderOption) obj;
	}
	/**
	 * 
	 * @param name customer name 
	 * @return  customer list
	 */
	public PageHelper<Customer> findCustomer(String clazzCode,String name,int pageNo) {
		return  (PageHelper<Customer>) fryeService.getInfo(URL_CUSTOMER+URL_PARAMETER_SEPERATOR+clazzCode+URL_SEPERATOR+name+URL_SEPERATOR+String.valueOf(pageNo),PageHelper.class);
	}
	
	public PageHelper<Material> findMaterialsByName(String name,int pageNo) {
		if(name==null) {
			return null;
		}
		Map<String,String> pars = new HashMap<String,String>();
		pars.put("name", name);
		pars.put("pageNo", String.valueOf(pageNo));
		
		return (PageHelper<Material>) fryeService.postForm(URL_MATERIAL,pars,PageHelper.class);
	}
	public Material getMaterial(String code) {
		return (Material)fryeService.getInfo(URL_MATERIAL+URL_PARAMETER_SEPERATOR+code,Material.class);
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
	public void saveOrder(DealerOrder form) {
		fryeService.postJason(URL_ORDER, form);
	}
	
	public DOrder getInfoById(String orderId) {
		
		return dOrderervice.getInfo(URL_D_ORDER+"/"+orderId,DOrder.class);
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
		String url = URL_ORDER+URL_PARAMETER_SEPERATOR+orderId+URL_PARAMETER_SEPERATOR+"version";
		return (List<OrderVersion>)fryeService.getInfo(url,List.class);
	}
	
	public PageHelper<BaseOrder> findOrders(OrderQuery query) {
		String url = URL_ORDER+URL_PARAMETER_SEPERATOR+"query";
		return (PageHelper<BaseOrder>)fryeService.postInfo(query, url, PageHelper.class);
	}
	
	public List<Characteristic> getCharactersByClazzCode(String clazzCode, String materialCode) {
        String url = URL_MATERIAL_CONFIG+URL_PARAMETER_SEPERATOR+clazzCode+','+materialCode;
        return (List<Characteristic>)fryeService.getInfo(url,List.class);
    }
	
	public DealerOrder findDealerOrderDetail(String sequenceNumber, String version) {
		return (DealerOrder)findOrderDetail(sequenceNumber, version, ORDER_TYPE_DEALER);
	}
	
	public AbsOrder findOrderDetail(String sequenceNumber, String version, String orderType) {
		String url = URL_ORDER+URL_PARAMETER_SEPERATOR+"detail?sequenceNumber=" + sequenceNumber + "&version=" + version;
		AbsOrder order = null;
		switch(orderType) {
			case ORDER_TYPE_DEALER:
				String json = (String)fryeService.getInfo(url, String.class);
				ObjectMapper mapper = new ObjectMapper();
				mapper.setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM));
				try {
					order = mapper.readValue(json, DealerOrder.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
//				order = (DealerOrder)fryeService.getInfo(url, DealerOrder.class);
				break;
			case ORDER_TYPE_KEYACCOUNT:
				order = (KeyAccountOrder)fryeService.getInfo(url, KeyAccountOrder.class);
				break;
			case ORDER_TYPE_BULK:
				order = (BulkOrder)fryeService.getInfo(url, BulkOrder.class);
				break;
			
			default :
				throw new RuntimeException(MessageFormat.format("Unknown order type [{0}]", orderType));
		}
		
		return order;
	}
	
	public String getOrderType(String sequenceNumber) {
		String url = URL_ORDER+URL_PARAMETER_SEPERATOR+"type?sequenceNumber=" + sequenceNumber;
		return (String)fryeService.getInfo(url, String.class);
	}
	
	public String toSap(String sequenceNumber, String version) {
		String url = URL_ORDER+URL_PARAMETER_SEPERATOR+"sap?sequenceNumber=" + sequenceNumber + "&version=" + version;
		return (String)fryeService.postInfo(null, url, String.class);
	}

	public BomExplosion findBOMWithPrice(Map<String, String> pars) {
		String url = URL_ORDER+URL_PARAMETER_SEPERATOR+URL_MATERIAL_BOM;
		return (BomExplosion)fryeService.postInfo(pars,url, BomExplosion.class);
	}
}
