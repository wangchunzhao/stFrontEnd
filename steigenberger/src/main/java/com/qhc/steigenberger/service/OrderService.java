/**
 * 
 */
package com.qhc.steigenberger.service;

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
import com.qhc.steigenberger.domain.SalesGroup;
import com.qhc.steigenberger.domain.SalesOrder;
import com.qhc.steigenberger.config.ApplicationConfig;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.domain.SpecialDelivery;
import com.qhc.steigenberger.domain.form.AbsOrder;
import com.qhc.steigenberger.domain.form.DealerOrder;
import com.qhc.steigenberger.service.FryeService;
import com.qhc.steigenberger.service.exception.ExternalServerInternalException;
import com.qhc.steigenberger.service.exception.URLNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wang@dxc.com
 *
 */
@Service
public class OrderService {
	
	private final static String URL_CUSTOMER = "customer";
	private final static String URL_CUSTOMER_CLASS = "customer/customerClass";
	private final static String URL_SALES_TYPE = "order/salesType";
	private final static String URL_CURRENCY = "currency";
	private final static String URL_INCOTERMS = "incoterms";
	private final static String URL_ORDER = "order";
	private final static String URL_D_ORDER = "order/dOrder";
	private final static String URL_SEPERATOR = ",";
	private final static String URL_PARAMETER_SEPERATOR = "/";
	private final static String URL_ABS_ORDER_SALESORDER = "order/salesOrder";
	
	@Autowired
	private FryeService fryeService;
	
	@Autowired
	FryeService<DOrder> dOrderervice;
	/**
	 * 
	 * @param name customer name 
	 * @return  customer list
	 */
	public List<Customer> findCustomer(String name,int pageNo) {
		return  fryeService.getListInfo(URL_CUSTOMER+URL_PARAMETER_SEPERATOR+name+URL_SEPERATOR+String.valueOf(pageNo),Customer.class);
	}
	/**
	 * 
	 * @return customer class map
	 */
	public Map<String, String> getCustomerClazz() {
		return  fryeService.getMapData(URL_CUSTOMER_CLASS);
	}
	/**
	 * 
	 * @return sales type map
	 */
	public Map<String, String> getSalesType() {
		return  fryeService.getMapData(URL_SALES_TYPE);
	}
	/**
	 * 
	 * @return currency map
	 */
	public Map<String, String> getCurrency() {
		return  fryeService.getMapData(URL_CURRENCY);
	}
	/**
	 * 
	 * @return
	 */
	public Map<String,String> getIncoterms(){
		return fryeService.getMapData(URL_INCOTERMS);
	}
	/**
	 * 
	 * @param form : order
	 */
	public void saveOrder(DealerOrder form) {
		fryeService.postJason(URL_ORDER, DealerOrder.class);
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

}
