package com.qhc.steigenberger;

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
	public final static String PAGE_STOCK_UP = "stockUp/stockUp";

}
