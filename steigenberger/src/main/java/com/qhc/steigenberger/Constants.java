package com.qhc.steigenberger;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	/**
	 * Session 当前用户账号变量名
	 */
	public static final String ACCOUNT = "account";
	
	/**
	 * Session 当前用户标识名
	 */
	public final static String IDENTITY = "identity";
	
	/**
	 * Session 当前用户权限变量名
	 */
	public static final String PERMISSIONS = "permissions";
	
	
	/**
	 * Session 当前用户权限变量名
	 */
	public static final String MENUS = "menus";
	
	/**
	 * 五种订单页面
	 */
	
	/**
	 * 经销商标准订单
	 */
	public final static String PAGE_DEALER = "dealerOrder/dealerOrder";
	
	/**
	 * 经销商非标准订单
	 */
	public final static String PAGE_DEALER_NON_STANDARD = "nonStandardDealerOrder/nonStandardOrder";
	
	/**
	 * 直签客户投标报价
	 */
	public final static String PAGE_DIRECT_CUSTOMER_TENDER_OFFER = "directCustomerTenderOffer/directCustomerTenderOffer";
	
	/**
	 * 直签客户投标下单
	 */
	public final static String PAGE_DIRECT_CUSTOMER_CREATE_ORDER = "directCustomerCreateOrder/directCustomerCreateOrder";
	
	/**
	 * 备货
	 */
	public final static String PAGE_STOCK_UP = "stockUpOrder/stockUpOrder";
	  
	public final static Map<String, String> itemCategoryMap = new HashMap() {
		  {
			// ZH0D/ZH01 可配置
			  put("ZHD1","标准");
			  put("ZHD3","免费");
			  put("ZHR3","退货");
			  // ZH0D/ZH02 不可配置
			  put("ZHD2","标准");
			  put("ZHD4","免费");
			  put("ZHR4","退货");
			  // ZH0T/ZH01 可配置
			  put("ZHT1","标准");
			  put("ZHT3","免费");
			  put("ZHR1","退货");
			  // ZH0D/ZH02 可配置
			  put("ZHT2","标准");
			  put("ZHT6","免费");
			  put("ZHR2","退货");
			  // 不可预估费，其他项目收付费
			  put("ZH97","ZH97");
			  put("ZH98","ZH98");
		  }
	  };
	  
	  public final static Map<String, String> itemRequirementPlanMap = new HashMap() {
		  {
			  put("004","物料需求计划");
			  put("001","B2C");
			  put("002","消化");
			  put("003","调发");
			  put("005","替换");
		  }
	  };

}
