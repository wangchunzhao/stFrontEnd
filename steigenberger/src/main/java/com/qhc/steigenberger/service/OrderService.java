/**
 * 
 */
package com.qhc.steigenberger.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.CustomerClazz;
import com.qhc.steigenberger.domain.DOrder;
import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.domain.SpecialDelivery;
import com.qhc.steigenberger.domain.form.AbsOrder;
import com.qhc.steigenberger.domain.form.DealerOrder;
import com.qhc.steigenberger.service.FryeService;

/**
 * @author wang@dxc.com
 *
 */
@Service
public class OrderService {
	
	private final static String URL_CUSTOMER = "customer/customers/";
	private final static String URL_CUSTOMER_CLASS = "customer/customerClass";
	private final static String URL_SALES_TYPE = "order/salesType";
	private final static String URL_CURRENCY = "currency";
	private final static String URL_INCOTERMS = "incoterms";
	private final static String URL_ORDER = "order";
	private final static String URL_D_ORDER = "dOrder";
	
	@Autowired
	private FryeService fryeService;
	
	@Autowired
	FryeService<DOrder> dOrderervice;
	/**
	 * 
	 * @param name customer name 
	 * @return  customer list
	 */
	public List<Customer> findCustomer(String name) {
		return  fryeService.getListInfo(URL_CUSTOMER+name,Customer.class);
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

}
