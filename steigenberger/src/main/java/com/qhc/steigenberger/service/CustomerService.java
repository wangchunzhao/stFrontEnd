/**
 * 
 */
package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.Clazz;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.SapSalesOffice;

/**
 * @author wang@dxc.com
 *
 */
@Service
public class CustomerService {
	
	private final static String URL_CUSTOMER = "newOrder/customers/";
	private final static String URL_CUSTOMER_CLASS = "newOrder/customerClazz";
	
	@Autowired
	private FryeService fryeService;
	/**
	 * 
	 * @param name customer name 
	 * @return  customer list
	 */
	public List<Customer> searchCustomer(String name) {
		return  fryeService.getListInfo(URL_CUSTOMER+name,Customer.class);
	}
	/**
	 * 
	 * @return
	 */
	public List<Clazz> customerClazz() {
		return  fryeService.getListInfo(URL_CUSTOMER_CLASS,Clazz.class);
	}

}
