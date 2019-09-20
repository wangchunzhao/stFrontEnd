/**
 * 
 */
package com.qhc.steigenberger.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.CustomerClazz;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.SapSalesOffice;

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
	
	@Autowired
	private FryeService fryeService;
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
		return  fryeService.getMapDate(URL_CUSTOMER_CLASS);
	}
	/**
	 * 
	 * @return sales type map
	 */
	public Map<String, String> getSalesType() {
		return  fryeService.getMapDate(URL_SALES_TYPE);
	}
	/**
	 * 
	 * @return currency map
	 */
	public Map<String, String> getCurrency() {
		return  fryeService.getMapDate(URL_CURRENCY);
	}

}
