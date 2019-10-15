/**
 * 
 */
package com.qhc.steigenberger.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;


/**
 * @author wang@dxc.com
 *
 */
public class OrderOption {
	public final static String ORDER_BULK = "bulk";
	//
	private String sequenceNumber;
	private Map<String,String> provinces;//Map<province code,province name>
	private Map<String,Map<String,String>> citys;//Map<province code,Map<city code,city name>>
	private Set<BArea> districts;
	private Map<String,String> termialClass;
	/**sale type code**/
	private Map<String,String> saleTypes;
	//
	private Map<String,Map<String,String>> offices;//Map<saleType key(code),Map<office code,office name>>
	private Map<String,Map<String,String>> groups;//Map<office code,Map<group code,group name>>
	//
	private Map<String,Double> taxRate;//Map<saleType key(code),Map<taxRate name,rate>>
	//
	private Map<String,Currency> exchangeRate;
	//
	private Map<String,String> paymentType;//回款类型
	private Map<String,String> biddingPlan;//回款类型,大客户
	
	
	private Map<String,String> orderTypes;//大客户，经销商，备货Map<customer class code, orderType>
	/*
	 * need to be setted by invoker
	 */
	private String customerClazzCode;
	/*
	 * need to be setted by invoker
	 */
	private String orderTypeCode;


	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Map<String, String> getProvinces() {
		return provinces;
	}

	public void setProvinces(Map<String, String> provinces) {
		this.provinces = provinces;
	}

	public Map<String, Map<String, String>> getCitys() {
		return citys;
	}

	public void setCitys(Map<String, Map<String, String>> citys) {
		this.citys = citys;
	}

	public Set<BArea> getDistricts() {
		return districts;
	}

	public void setDistricts(Set<BArea> districts) {
		this.districts = districts;
	}

	public Map<String, String> getTermialClass() {
		return termialClass;
	}

	public void setTermialClass(Map<String, String> termialClass) {
		this.termialClass = termialClass;
	}

	public Map<String, String> getSaleTypes() {
		return saleTypes;
	}

	public void setSaleTypes(Map<String, String> saleTypes) {
		this.saleTypes = saleTypes;
	}

	public Map<String, Map<String, String>> getOffices() {
		return offices;
	}

	public void setOffices(Map<String, Map<String, String>> offices) {
		this.offices = offices;
	}

	public Map<String, Map<String, String>> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, Map<String, String>> groups) {
		this.groups = groups;
	}

	
	public Map<String, Double> getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Map<String, Double> taxRate) {
		this.taxRate = taxRate;
	}

	public Map<String, Currency> getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Map<String, Currency> exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Map<String, String> getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Map<String, String> paymentType) {
		this.paymentType = paymentType;
	}

	public Map<String, String> getBiddingPlan() {
		return biddingPlan;
	}

	public void setBiddingPlan(Map<String, String> biddingPlan) {
		this.biddingPlan = biddingPlan;
	}

	public Map<String, String> getOrderTypes() {
		if(orderTypes==null) {
			return new HashMap();
		}
		return orderTypes;
		
	}

	public void setOrderTypes(Map<String, String> orderTypes) {
		this.orderTypes = orderTypes;
	}

	public String getCustomerClazzCode() {
		return customerClazzCode;
	}

	public void setCustomerClazzCode(String customerClazzCode) {
		this.customerClazzCode = customerClazzCode;
	}

	public String getOrderTypeCode() {
		return orderTypeCode;
	}

	public void setOrderTypeCode(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	
}
