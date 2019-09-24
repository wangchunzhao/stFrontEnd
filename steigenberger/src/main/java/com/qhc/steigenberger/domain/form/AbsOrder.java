/**
 * 
 */
package com.qhc.steigenberger.domain.form;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wang@dxc.com
 *
 */
public abstract class AbsOrder {
	public final static String ORDER_TYPE_CODE_DEALER = "Z001";
	public final static String ORDER_TYPE_CODE_KEYACCOUNT = "Z002";
	public final static String ORDER_TYPE_CODE_BULK = "Z003";
	
	private String orderType;
	/**
	 * 客户基本信息 Basic information
	 */
	private String contracterCode;//签约单位 Contract unit
	private String customerName;//店名 customer name
	private int isConvenientStore;//是否便利店 convenience store
	//private String salesCode;//客户经理 Customer manager
	private String salesTelnumber;//客户经理电话 Customer manager Tel
	private int isNew;//是否新客户 new customer
	/**
	 * 合同详细信息 Contract details
	 */
	private String sequenceNumber;//流水号 code
	private String contractNumber;//合同号 contract no
	private String saleType;//销售类型 Sales type
	private String taxRate;//税率 Rate
	private String incoterm;//国际贸易条件 international trade
	private String incotermContect;//国际贸易条件 international trade
	private double contractValue;//原币合同金额 Contract amount
	private double contractRMBValue;//合同金额 Contract amount
	private String currency;//币种 currency
	private double currencyRate;//汇率 exchange rate
	//public abstract double getItemsAmount();//购销明细金额合计 Aggregate amount
	/*
	 * 合同详细信息 Contract details
	 */
	private String officeCode;//大区 area
	private String groupCode;//中心 center
	private int warrenty;//保修年限
	private String installCode;//安装方式 installation
	private String provinceCode; //地区 Region,到货地址 Address
	private String cityCode; //地区 Region,到货地址 Address
	private String distinctCode; //地区 Region,到货地址 Address
	private String address; //地区 Region,到货地址 Address
	private String contactor1Id;//授权人1及身份证号
	private String contactor1Tel;//授权人1及身份证号
	private String contactor2Id;//授权人2及身份证号
	private String contactor2Tel;//授权人2及身份证号
	private String contactor3Id;//授权人3及身份证号
	private String contactor3Tel;//授权人3及身份证号
	private String confirmTypeCode;//收货方式 Receiving way
	private String transferTypeCode;//运输类型 Type of transportation
	private double freight;//运费
	private int isAllinBulk;//是否全部为散件
	/**
	 * 结算方式 Method of payment
	 */
	private List<AbsSettlement> settles;
	/**
	 * 调研表相关内容 Research table related content
	 */
	private int isTerm1;//柜体控制阀件是否甲供
	private int isTerm2;//分体柜是否远程监控
	private int isTerm3;//立柜柜体是否在地下室
	/**
	 * 购销明细 Purchase and sale subsidiar
	 */
	private List<ProductItem> items;//购销明细
	private String comments;//备注
	/**
	 * 附件信息 Attachment information
	 */
	private List<String> attachedFileName;
	//
	private boolean isSubmit;
	
	public boolean isSubmit() {
		return isSubmit;
	}
	public void setSubmit(boolean isSubmit) {
		this.isSubmit = isSubmit;
	}
	//
	public String getContracterCode() {
		return contracterCode;
	}
	public void setContracterCode(String contracterCode) {
		this.contracterCode = contracterCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getIsConvenientStore() {
		return isConvenientStore;
	}
	public void setIsConvenientStore(int isConvenientStore) {
		this.isConvenientStore = isConvenientStore;
	}
	public String getSalesTelnumber() {
		return salesTelnumber;
	}
	public void setSalesTelnumber(String salesTelnumber) {
		this.salesTelnumber = salesTelnumber;
	}
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	public String getIncoterm() {
		return incoterm;
	}
	public void setIncoterm(String incoterm) {
		this.incoterm = incoterm;
	}
	public String getIncotermContect() {
		return incotermContect;
	}
	public void setIncotermContect(String incotermContect) {
		this.incotermContect = incotermContect;
	}
	public double getContractValue() {
		return contractValue;
	}
	public void setContractValue(double contractValue) {
		this.contractValue = contractValue;
	}
	public double getContractRMBValue() {
		return contractRMBValue;
	}
	public void setContractRMBValue(double contractRMBValue) {
		this.contractRMBValue = contractRMBValue;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public int getWarrenty() {
		return warrenty;
	}
	public void setWarrenty(int warrenty) {
		this.warrenty = warrenty;
	}
	
	public String getContactor1Id() {
		return contactor1Id;
	}
	public void setContactor1Id(String contactor1Id) {
		this.contactor1Id = contactor1Id;
	}
	public String getContactor1Tel() {
		return contactor1Tel;
	}
	public void setContactor1Tel(String contactor1Tel) {
		this.contactor1Tel = contactor1Tel;
	}
	public String getContactor2Id() {
		return contactor2Id;
	}
	public void setContactor2Id(String contactor2Id) {
		this.contactor2Id = contactor2Id;
	}
	public String getContactor2Tel() {
		return contactor2Tel;
	}
	public void setContactor2Tel(String contactor2Tel) {
		this.contactor2Tel = contactor2Tel;
	}
	public String getContactor3Id() {
		return contactor3Id;
	}
	public void setContactor3Id(String contactor3Id) {
		this.contactor3Id = contactor3Id;
	}
	public String getContactor3Tel() {
		return contactor3Tel;
	}
	public void setContactor3Tel(String contactor3Tel) {
		this.contactor3Tel = contactor3Tel;
	}
	public String getConfirmTypeCode() {
		return confirmTypeCode;
	}
	public void setConfirmTypeCode(String confirmTypeCode) {
		this.confirmTypeCode = confirmTypeCode;
	}
	public String getTransferTypeCode() {
		return transferTypeCode;
	}
	public void setTransferTypeCode(String transferTypeCode) {
		this.transferTypeCode = transferTypeCode;
	}
	public List<AbsSettlement> getSettles() {
		return settles;
	}
	public void setSettles(List<AbsSettlement> settles) {
		this.settles = settles;
	}
	public int getIsTerm1() {
		return isTerm1;
	}
	public void setIsTerm1(int isTerm1) {
		this.isTerm1 = isTerm1;
	}
	public int getIsTerm2() {
		return isTerm2;
	}
	public void setIsTerm2(int isTerm2) {
		this.isTerm2 = isTerm2;
	}
	public int getIsTerm3() {
		return isTerm3;
	}
	public void setIsTerm3(int isTerm3) {
		this.isTerm3 = isTerm3;
	}
	public List<ProductItem> getItems() {
		return items;
	}
	public void setItems(List<ProductItem> items) {
		this.items = items;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<String> getAttachedFileName() {
		return attachedFileName;
	}
	public void setAttachedFileName(List<String> attachedFileName) {
		this.attachedFileName = attachedFileName;
	}
	public String getInstallCode() {
		return installCode;
	}
	public void setInstallCode(String installCode) {
		this.installCode = installCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getDistinctCode() {
		return distinctCode;
	}
	public void setDistinctCode(String distinctCode) {
		this.distinctCode = distinctCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getIsAllinBulk() {
		return isAllinBulk;
	}
	public void setIsAllinBulk(int isAllinBulk) {
		this.isAllinBulk = isAllinBulk;
	}
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
