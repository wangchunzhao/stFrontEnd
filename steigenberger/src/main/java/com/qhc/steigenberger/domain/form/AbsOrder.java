/**
 * 
 */
package com.qhc.steigenberger.domain.form;

import java.util.Date;
import java.util.List;

/**
 * @author wang@dxc.com
 *
 */
public abstract class AbsOrder {
	
	public final static String ORDER_TYPE_CODE_DEALER = "Z001";
	public final static String ORDER_TYPE_CODE_KEYACCOUNT = "Z002";
	public final static String ORDER_TYPE_CODE_BULK = "Z003";
	
	public final static String ORDER_CUSTOMER_DEALER_CODE="01";
	public final static String ORDER_CUSTOMER_KEY_ACCOUNT_CODE="02";
	//@NotEmpty(message="order type 不能为空")
	private String orderType;
	/**
	 * 客户基本信息 Basic information
	 */
	private String customerClazzCode;//客户性质分类代码
	private String customerClazzName;//客户性质分类名称
	private String terminalType;//终端客户性质
	private String contracterCode;//签约单位 Contract unit
	private String contracterName;//签约单位名字
	private String customerName;//店名 customer name
	private int isConvenientStore;//是否便利店 convenience store
	private String salesCode;//客户经理 Customer manager
	private String salesName;//客户经理 Customer manager
	private String salesTelnumber;//客户经理电话 Customer manager Tel
	private int isNew;//是否新客户 new customer
	private int isReformed;//是否改造店
	/**
	 * 合同详细信息 Contract details
	 */
	//@NotEmpty(message="流水号不能为空") 
	private String sequenceNumber;//流水号 code
	private String contractNumber;//合同号 contract no
	private String saleType;//销售类型 Sales type
	private double taxRate;//税率 Rate
	private String incoterm;//国际贸易条件code
	private String incotermName;//国际贸易条件名称
	private String incotermContect;//国际贸易条件内容
	private double contractValue;//原币合同金额 Contract amount
	private double contractRMBValue;//合同金额 Contract amount
	private String currency;//币种 currency
	private String currencyName;//币种名称
	private double currencyExchange;//汇率 exchange rate
	private double itemsAmount;//购销明细金额合计 Aggregate amount
	private String contractManager;//合同管理员
	private Date createTime;//新建时间
	private double bodyDiscount;
	private double mainDiscount;
	private double approvedDiscount;
	
	/*
	 * 合同详细信息 Contract details
	 */
	private String officeCode;//大区 area
	private String officeName;//大区 name
	private String groupCode;//中心 center
	private String groupName;//中心name
	private int warrenty;//保修年限
	private String installCode;//安装方式 installation
	private String installName;//安装方式名称
	private String contactor1Id;//授权人1及身份证号
	private String contactor1Tel;//授权人1电话
	private String contactor2Id;//授权人2及身份证号
	private String contactor2Tel;//授权人2电话
	private String contactor3Id;//授权人3及身份证号
	private String contactor3Tel;//授权人3电话
	private String confirmTypeCode;//收货方式 Receiving way
	private String confirmTypeName;//收货方式名称
	private String transferTypeCode;//运输类型code
	private String transferTypeName;//运输类型名称
	private double freight;//运费	
	/**
	 * 结算方式 Method of payment
	 */
	//private List<AbsSettlement> settles;
	/**
	 * 调研表相关内容 Research table related content
	 * SAP字段名：VBBKZ122，格式为：汉字：是/否，汉字：是/否，汉字：是/否
	 */
	private int isTerm1;//柜体控制阀件是否甲供
	private int isTerm2;//分体柜是否远程监控
	private int isTerm3;//立柜柜体是否在地下室
	private Date earliestDeliveryDate;//要求发货时间,最早交付时间
	private Date earliestProductDate;//工厂最早交货时间,最早生产时间
	/**
	 * 购销明细 Purchase and sale subsidiar
	 */
	//private List<ProductItem> items;//购销明细
	private List<BaseItem> items;
	private String comments;//备注
	private List<OrderAddress> orderAddress;//合同明细地址
	private List<BiddingPayment> payments;//付款条件或bidding plan 
	
	private String currentVersion;//当前版本,创建时steigenberger创建
	private String currentVersionStatus;
	private List<String> versions;
	private String userOfficeCode;//用户所在销售办公室

	public AbsOrder() {
		
	}
	public AbsOrder(String json) {
		
	}
	
	
	public String getContracterName() {
		return contracterName;
	}
	public void setContracterName(String contracterName) {
		this.contracterName = contracterName;
	}
	public String getCurrentVersionStatus() {
		return currentVersionStatus;
	}
	public void setCurrentVersionStatus(String currentVersionStatus) {
		this.currentVersionStatus = currentVersionStatus;
	}
	public List<String> getVersions() {
		return versions;
	}
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}
	public String getCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	
	public String getIncotermName() {
		return incotermName;
	}
	public void setIncotermName(String incotermName) {
		this.incotermName = incotermName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getInstallName() {
		return installName;
	}
	public void setInstallName(String installName) {
		this.installName = installName;
	}
	public String getConfirmTypeName() {
		return confirmTypeName;
	}
	public void setConfirmTypeName(String confirmTypeName) {
		this.confirmTypeName = confirmTypeName;
	}
	public String getTransferTypeName() {
		return transferTypeName;
	}
	public void setTransferTypeName(String transferTypeName) {
		this.transferTypeName = transferTypeName;
	}
	public Date getEarliestDeliveryDate() {
		return earliestDeliveryDate;
	}
	public void setEarliestDeliveryDate(Date earliestDeliveryDate) {
		this.earliestDeliveryDate = earliestDeliveryDate;
	}
	public Date getEarliestProductDate() {
		return earliestProductDate;
	}
	public void setEarliestProductDate(Date earliestProductDate) {
		this.earliestProductDate = earliestProductDate;
	}
	public double getItemsAmount() {
		return itemsAmount;
	}
	public void setItemsAmount(double itemsAmount) {
		this.itemsAmount = itemsAmount;
	}
	public String getContractManager() {
		return contractManager;
	}
	public void setContractManager(String contractManager) {
		this.contractManager = contractManager;
	}

	public Date getOptTime() {
		return optTime;
	}
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}
	/*public List<AbsItem> getItems() {
		return items;
	}
	public void setItems(List<AbsItem> items) {
		this.items = items;
	}*/
	public List<OrderAddress> getOrderAddress() {
		return orderAddress;
	}
	public List<BaseItem> getItems() {
		return items;
	}
	public void setItems(List<BaseItem> items) {
		this.items = items;
	}
	public void setOrderAddress(List<OrderAddress> orderAddress) {
		this.orderAddress = orderAddress;
	}
	/**
	 * 附件信息 Attachment information
	 */
	private List<String> attachedFileName;
	//
	private int submitType;
	private String currentUser;//当前session用户
	private Date optTime;//新建时为录入日期/其它为修改时间
	//
	
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public int getSubmitType() {
		return submitType;
	}
	public void setSubmitType(int submitType) {
		this.submitType = submitType;
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
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
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

	public double getCurrencyExchange() {
		return currencyExchange;
	}
	public void setCurrencyExchange(double currencyExchange) {
		this.currencyExchange = currencyExchange;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
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
	public String getSalesCode() {
		return salesCode;
	}
	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}
	public String getCustomerClazzCode() {
		return customerClazzCode;
	}
	public void setCustomerClazzCode(String customerClazzCode) {
		this.customerClazzCode = customerClazzCode;
	}
	public String getCustomerClazzName() {
		return customerClazzName;
	}
	public void setCustomerClazzName(String customerClazzName) {
		this.customerClazzName = customerClazzName;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getUserOfficeCode() {
		return userOfficeCode;
	}
	public void setUserOfficeCode(String userOfficeCode) {
		this.userOfficeCode = userOfficeCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getIsReformed() {
		return isReformed;
	}
	public void setIsReformed(int isReformed) {
		this.isReformed = isReformed;
	}
	public double getBodyDiscount() {
		return bodyDiscount;
	}
	public void setBodyDiscount(double bodyDiscount) {
		this.bodyDiscount = bodyDiscount;
	}
	public double getMainDiscount() {
		return mainDiscount;
	}
	public void setMainDiscount(double mainDiscount) {
		this.mainDiscount = mainDiscount;
	}
	public double getApprovedDiscount() {
		return approvedDiscount;
	}
	public void setApprovedDiscount(double approvedDiscount) {
		this.approvedDiscount = approvedDiscount;
	}
	public List<BiddingPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<BiddingPayment> payments) {
		this.payments = payments;
	}
	
}
