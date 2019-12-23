package com.qhc.steigenberger.domain.form;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author Walker
 *
 */
public class Order {
	// 订单STATUS
	public final static String ORDER_STATUS_STRAFT = "00"; //草稿
	public final static String ORDER_STATUS_B2C = "01"; // 待B2C审批
	public final static String ORDER_STATUS_ENGINER = "02"; // 待工程人员审批
	public final static String ORDER_STATUS_MANAGER = "03"; // 待支持经理审批
	public final static String ORDER_STATUS_BPM = "04"; // 提交到BPM
	public final static String ORDER_STATUS_APPROVED = "05"; // BPM审批通过
	public final static String ORDER_STATUS_APPROVED_UPDATE = "06"; // 订单变更BPM审批通过
	public final static String ORDER_STATUS_SAP = "09"; // 已下推SAP
	public final static String ORDER_STATUS_REJECT = "10"; // Selling Tool驳回
	public final static String ORDER_STATUS_REJECT_BPM = "11"; // BPM驳回
	
	// 订单类型sap_order_type
	public final static String ORDER_TYPE_CODE_DEALER = "ZH0D";
	public final static String ORDER_TYPE_CODE_KEYACCOUNT = "ZH0T";
	public final static String ORDER_TYPE_CODE_BULK = "ZH0M";
	
	// 性质分类，客户性质 sap_customer_class
	public final static String ORDER_CUSTOMER_DEALER_CODE="01";
	public final static String ORDER_CUSTOMER_KEY_ACCOUNT_CODE="02";
	
	private Integer id; // order info id;
	private Integer orderId; // order id

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date createTime;// 创建时间
	private String creater; // 创建人identity
	private String createrName; // 创建人姓名
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date updateTime;// 修改时间
	private String updater; // 修改人identity
	private String updaterName; // 修改人姓名
	
	private String version;//版本
	private String status; // 状态
	private List<String> versions; // 历史版本
	private String userOfficeCode;//用户所在销售办公室，创建人用户信息中带出
	/**
	 * 销售工具订单类型
		1 经销商标准折扣下单
		2 经销商非标准折扣下单
		3 直签客户投标报价
		4 直签客户下定单
		5 备货 
	 */
	private String stOrderType; // 销售工具的订单类型

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date submitTime; // submit time
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date submitBpmTime; // submit to bpm time
	
	/**
	 * 客户基本信息 Basic information
	 */
	private String orderType; // 订单类型，ZH0D	经销商订单/	ZH0M	备货订单/ZH0T	大客户订单，固定
	private String customerCode;//签约单位 Contract unit
	private String customerName;//签约单位 Contract Name
	private String customerClazz;//性质分类代码，经销商/直签
	private String customerClazzName;//性质分类名称
	private String terminalType;//终端客户性质，是否是大客户的客户性质，即客户级别（sap_industry_code），大客户由客户信息带出，经销商选择
	private String terminalTypeName;//终端客户性质名称
	private String shopName;//店名 shop name
	private String recordCode;//项目报备编号
	private String salesCode;//客户经理 Customer manager
	private String salesName;//客户经理 Customer manager
	private String salesTel;//客户经理电话 Customer manager Tel
	private int isConvenientStore;//是否便利店 convenience store
	private int isNew;//是否新客户 new customer
	private int isReformed;//是否改造店
	private int isYearpurchase;// 是否年采价，客户信息带出
	private String customerIndustry; // 隶属关系（sap_industry）客户信息带出
	private String customerIndustryName; // 隶属关系（sap_industry）客户信息带出
	
	/**
	 * 合同详细信息 Contract details
	 */
	//@NotEmpty(message="流水号不能为空") 
	private String sequenceNumber;//流水号 code
	private String contractNumber;//合同号 contract no
	private String saleType;//销售类型 Sales type，10	内销/20	出口/30	冷库
	private double taxRate;//税率 Rate
	private String incoterm;//国际贸易条件code
	private String incotermName;//国际贸易条件名称
	private String incotermContect;//国际贸易条件内容
	private double contractValue;//原币合同金额 Contract amount
	private double contractRmbValue;//合同金额 Contract amount
	private String currency;//币种 currency
	private String currencyName;//币种名称
	private double currencyExchange;//汇率 exchange rate
	private double itemsAmount;//购销明细金额合计 Aggregate amount
	private String contractManager;// 支持经理，合同管理员
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private String contractCreateTime;//合同录入时间，由合同信息（k_contract）带出

	/*
	 * 合同详细信息 Contract details
	 */
	private String officeCode;//大区 area
	private String officeName;//大区 name
	private String groupCode;//中心 center
	private String groupName;//中心name
	private int warranty;//保修年限，大客户可以修改
	private String installType;//安装方式 installation
	private String installTypeName;//安装方式名称
	private String receiveType;//收货方式 Receiving way
	private String receiveTypeName;//收货方式名称
	private String transferType;//运输类型code
	private String transferTypeName;//运输类型名称
	private double freight; // 外销运费	
	private String contactor1Id;//授权人1及身份证号
	private String contactor1Tel;//授权人1电话
	private String contactor2Id;//授权人2及身份证号
	private String contactor2Tel;//授权人2电话
	private String contactor3Id;//授权人3及身份证号
	private String contactor3Tel;//授权人3电话
	private List<DeliveryAddress> deliveryAddress;//收货地址
	
	private double bodyDiscount; // 机柜折扣
	private double approvedBodyDiscount; // 批准的机柜折扣
	private double mainDiscount; // 机柜折扣
	private double approvedMainDiscount; // 批准的机组折扣
	
	private double discount;//合并折扣
	
	private int isLongterm;//是否为长期折扣
	
	private int isSpecial; // 是否非标准折扣，默认为0，经销商非标下单为1
	
	private String paymentType; //结算方式，经销商
	private List<BillingPlan> payments; //回款计划，大客户
	
	/**
	 * 调研表相关内容 Research table related content
	 * SAP字段名：VBBKZ122，格式为：汉字：是/否，汉字：是/否，汉字：是/否
	 */
	private int isTerm1;//柜体控制阀件是否甲供
	private int isTerm2;//分体柜是否远程监控
	private int isTerm3;//立柜柜体是否在地下室

	/* 工程安装费 */
	private Double installFee = null;
	/* 工程材料费 */
	private Double materialFee = null;
	/* 工程电气费 */
	private Double electricalFee = null;
	/* 工程冷库费 */
	private Double refrigeratoryFee = null;
	/* 工程维保费 */
	private Double maintenanceFee = null;
	
	/**
	 * 购销明细 Purchase and sale subsidiar
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date earliestDeliveryDate;//要求发货时间,最早交付时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date earliestProductDate;//工厂最早交货时间,最早生产时间

	private List<Item> items;
	private int isB2c; // 是否由B2C备注
	private String grossProfitMargin; // 订单毛利率
	
	private String comments;//备注
	
	/**
	 * 附件信息 Attachment information
	 */
	private List<Attachment> attachments;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getVersions() {
		return versions;
	}

	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

	public String getUserOfficeCode() {
		return userOfficeCode;
	}

	public void setUserOfficeCode(String userOfficeCode) {
		this.userOfficeCode = userOfficeCode;
	}

	public String getStOrderType() {
		return stOrderType;
	}

	public void setStOrderType(String stOrderType) {
		this.stOrderType = stOrderType;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getSubmitBpmTime() {
		return submitBpmTime;
	}

	public void setSubmitBpmTime(Date submitBpmTime) {
		this.submitBpmTime = submitBpmTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerClazz() {
		return customerClazz;
	}

	public void setCustomerClazz(String customerClazz) {
		this.customerClazz = customerClazz;
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

	public String getTerminalTypeName() {
		return terminalTypeName;
	}

	public void setTerminalTypeName(String terminalTypeName) {
		this.terminalTypeName = terminalTypeName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesTel() {
		return salesTel;
	}

	public void setSalesTel(String salesTel) {
		this.salesTel = salesTel;
	}

	public int getIsConvenientStore() {
		return isConvenientStore;
	}

	public void setIsConvenientStore(int isConvenientStore) {
		this.isConvenientStore = isConvenientStore;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public int getIsReformed() {
		return isReformed;
	}

	public void setIsReformed(int isReformed) {
		this.isReformed = isReformed;
	}

	public int getIsYearpurchase() {
		return isYearpurchase;
	}

	public void setIsYearpurchase(int isYearpurchase) {
		this.isYearpurchase = isYearpurchase;
	}

	public String getCustomerIndustry() {
		return customerIndustry;
	}

	public void setCustomerIndustry(String customerIndustry) {
		this.customerIndustry = customerIndustry;
	}

	public String getCustomerIndustryName() {
		return customerIndustryName;
	}

	public void setCustomerIndustryName(String customerIndustryName) {
		this.customerIndustryName = customerIndustryName;
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

	public String getIncotermName() {
		return incotermName;
	}

	public void setIncotermName(String incotermName) {
		this.incotermName = incotermName;
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

	public double getContractRmbValue() {
		return contractRmbValue;
	}

	public void setContractRmbValue(double contractRmbValue) {
		this.contractRmbValue = contractRmbValue;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public double getCurrencyExchange() {
		return currencyExchange;
	}

	public void setCurrencyExchange(double currencyExchange) {
		this.currencyExchange = currencyExchange;
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

	public String getContractCreateTime() {
		return contractCreateTime;
	}

	public void setContractCreateTime(String contractCreateTime) {
		this.contractCreateTime = contractCreateTime;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getWarranty() {
		return warranty;
	}

	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}

	public String getInstallType() {
		return installType;
	}

	public void setInstallType(String installType) {
		this.installType = installType;
	}

	public String getInstallTypeName() {
		return installTypeName;
	}

	public void setInstallTypeName(String installTypeName) {
		this.installTypeName = installTypeName;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getReceiveTypeName() {
		return receiveTypeName;
	}

	public void setReceiveTypeName(String receiveTypeName) {
		this.receiveTypeName = receiveTypeName;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getTransferTypeName() {
		return transferTypeName;
	}

	public void setTransferTypeName(String transferTypeName) {
		this.transferTypeName = transferTypeName;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
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

	public List<DeliveryAddress> getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(List<DeliveryAddress> deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public double getBodyDiscount() {
		return bodyDiscount;
	}

	public void setBodyDiscount(double bodyDiscount) {
		this.bodyDiscount = bodyDiscount;
	}

	public double getApprovedBodyDiscount() {
		return approvedBodyDiscount;
	}

	public void setApprovedBodyDiscount(double approvedBodyDiscount) {
		this.approvedBodyDiscount = approvedBodyDiscount;
	}

	public double getMainDiscount() {
		return mainDiscount;
	}

	public void setMainDiscount(double mainDiscount) {
		this.mainDiscount = mainDiscount;
	}

	public double getApprovedMainDiscount() {
		return approvedMainDiscount;
	}

	public void setApprovedMainDiscount(double approvedMainDiscount) {
		this.approvedMainDiscount = approvedMainDiscount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getIsLongterm() {
		return isLongterm;
	}

	public void setIsLongterm(int isLongterm) {
		this.isLongterm = isLongterm;
	}

	public int getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(int isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public List<BillingPlan> getPayments() {
		return payments;
	}

	public void setPayments(List<BillingPlan> payments) {
		this.payments = payments;
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

	public Double getInstallFee() {
		return installFee;
	}

	public void setInstallFee(Double installFee) {
		this.installFee = installFee;
	}

	public Double getMaterialFee() {
		return materialFee;
	}

	public void setMaterialFee(Double materialFee) {
		this.materialFee = materialFee;
	}

	public Double getElectricalFee() {
		return electricalFee;
	}

	public void setElectricalFee(Double electricalFee) {
		this.electricalFee = electricalFee;
	}

	public Double getRefrigeratoryFee() {
		return refrigeratoryFee;
	}

	public void setRefrigeratoryFee(Double refrigeratoryFee) {
		this.refrigeratoryFee = refrigeratoryFee;
	}

	public Double getMaintenanceFee() {
		return maintenanceFee;
	}

	public void setMaintenanceFee(Double maintenanceFee) {
		this.maintenanceFee = maintenanceFee;
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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getIsB2c() {
		return isB2c;
	}

	public void setIsB2c(int isB2c) {
		this.isB2c = isB2c;
	}

	public String getGrossProfitMargin() {
		return grossProfitMargin;
	}

	public void setGrossProfitMargin(String grossProfitMargin) {
		this.grossProfitMargin = grossProfitMargin;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((sequenceNumber == null) ? 0 : sequenceNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (sequenceNumber == null) {
			if (other.sequenceNumber != null)
				return false;
		} else if (!sequenceNumber.equals(other.sequenceNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderDto [id=" + id + ", orderId=" + orderId + ", createTime=" + createTime + ", creater=" + creater
				+ ", createrName=" + createrName + ", updateTime=" + updateTime + ", updater=" + updater
				+ ", updaterName=" + updaterName + ", version=" + version + ", status=" + status + ", versions="
				+ versions + ", userOfficeCode=" + userOfficeCode + ", submitTime=" + submitTime + ", submitBpmTime="
				+ submitBpmTime + ", orderType=" + orderType + ", customerCode=" + customerCode + ", customerName="
				+ customerName + ", customerClazz=" + customerClazz + ", customerClazzName=" + customerClazzName
				+ ", terminalType=" + terminalType + ", terminalTypeName=" + terminalTypeName + ", shopName=" + shopName
				+ ", recordCode=" + recordCode + ", salesCode=" + salesCode + ", salesName=" + salesName + ", salesTel="
				+ salesTel + ", isConvenientStore=" + isConvenientStore + ", isNew=" + isNew + ", isReformed="
				+ isReformed + ", isYearpurchase=" + isYearpurchase + ", customerIndustry=" + customerIndustry
				+ ", customerIndustryName=" + customerIndustryName + ", sequenceNumber=" + sequenceNumber
				+ ", contractNumber=" + contractNumber + ", saleType=" + saleType + ", taxRate=" + taxRate
				+ ", incoterm=" + incoterm + ", incotermName=" + incotermName + ", incotermContect=" + incotermContect
				+ ", contractValue=" + contractValue + ", contractRmbValue=" + contractRmbValue + ", currency="
				+ currency + ", currencyName=" + currencyName + ", currencyExchange=" + currencyExchange
				+ ", itemsAmount=" + itemsAmount + ", contractManager=" + contractManager + ", contractCreateTime="
				+ contractCreateTime + ", officeCode=" + officeCode + ", officeName=" + officeName + ", groupCode="
				+ groupCode + ", groupName=" + groupName + ", warranty=" + warranty + ", installType=" + installType
				+ ", installTypeName=" + installTypeName + ", receiveType=" + receiveType + ", receiveTypeName="
				+ receiveTypeName + ", transferType=" + transferType + ", transferTypeName=" + transferTypeName
				+ ", freight=" + freight + ", contactor1Id=" + contactor1Id + ", contactor1Tel=" + contactor1Tel
				+ ", contactor2Id=" + contactor2Id + ", contactor2Tel=" + contactor2Tel + ", contactor3Id="
				+ contactor3Id + ", contactor3Tel=" + contactor3Tel + ", deliveryAddress=" + deliveryAddress
				+ ", bodyDiscount=" + bodyDiscount + ", approvedBodyDiscount=" + approvedBodyDiscount
				+ ", mainDiscount=" + mainDiscount + ", approvedMainDiscount=" + approvedMainDiscount + ", discount="
				+ discount + ", isLongterm=" + isLongterm + ", isSpecial=" + isSpecial + ", paymentType=" + paymentType
				+ ", payments=" + payments + ", isTerm1=" + isTerm1 + ", isTerm2=" + isTerm2 + ", isTerm3=" + isTerm3
				+ ", earliestDeliveryDate=" + earliestDeliveryDate + ", earliestProductDate=" + earliestProductDate
				+ ", items=" + items + ", isB2c=" + isB2c + ", grossProfitMargin=" + grossProfitMargin + ", comments="
				+ comments + ", attachments=" + attachments + "]";
	}
	
}
