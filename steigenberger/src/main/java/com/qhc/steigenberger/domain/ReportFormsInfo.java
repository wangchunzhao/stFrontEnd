package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.qhc.steigenberger.util.Page;


/**
 * 报表信息实体
 * @author lizuoshan
 *
 */
public class ReportFormsInfo extends Page implements Serializable {
	
	
	private String id;
	
	private String	sequenceNumber	;//	单据编号//订单编号
	
	private String	ownerName	;//	签约人//客户经理
	
	private String	officeCode	;//	区域
	
	private String	centre	;//	中心
	
	private Date	contractDate	;//	签约日期
	
	private String	coustomerNo	;//	客户编号
	
	private String	contractorCode	;//	合同号
	
	private String	contractUnit	;//	签约单位
	
	private String	contractorClassName	;//	客户性质
	private String	contractorClassCode;//	客户性质
	
	private String	shopName	;//	店名
	
	private String	terminalIndustryCode	;//	终端客户性质
	private String	terminalIndustryCodeName	;//	终端客户性质
	
	private Integer	isSpecialDiscount	;//	是否特批折扣
	
	private Integer	isRreformed	;//	是否改造店
	
	private BigDecimal contractRmbAmount	;//	合同金额
	
	private Integer	status	;//	合同状态
	
	private BigDecimal	grossProfit	;//	毛利率
	
	private Integer	isLongTermDiscount	;//	是否长期折扣
	
	private Double	discount	;//	折扣
	private Double	bodyDiscount	;//	机身折扣
	private Double	mainDiscount	;//	机主折扣
	
	private String	materialCode	;//	产品规格型号
	
	private String	materialSpecificNumber	;//	物料专用号
	
	private String	materialAttribute	;//	物料属性
	
	private Integer	quantity	;//	合同数量
	
	private BigDecimal	price	;//	销售单价
	
	private BigDecimal	amount	;//	销售金额
	
	private String	measureUnitName	;//	单位
	
	private String	receiverAddress	;//	到货地址
	
	private Date	earliestDeliveryDate	;//	要求发货时间
	
	private String	installTermCode	;//	安装公司
	private String	installTermName	;//	安装公司
	
	private String	receiveType	;//	收货方式
	
	private String	contactor1id	;//	授权人及身份证号
	private String	contactor2id	;//	授权人及身份证号
	private String	contactor3id	;//	授权人及身份证号
	
	private String	contactor1Tel	;//	授权人电话
	private String	contactor2Tel	;//	授权人电话
	private String	contactor3Tel	;//	授权人电话
	
	private String	receiverID	;//	收货人身份证号
	
	private String	settlementMethod	;//	结算方式
	
	
	private BigDecimal	freight	;//运费
	
	private Integer	warranty;//保修期限（年）
	
	private String	currencyCode;//币别
	private String	currencyName;//币别
	

	private BigDecimal	contractAmount	;//原币合同金额
	
	private Double	exchange	;//汇率
	
	private Integer	isNew	;//是否新客户
	
	private String officeName;//大区名称 
	private String	statusName	;//	合同状态名称
	private String	customerTypeName	;//	客户性质名称
	
	
	public String startTime;
	
	public String endTime;
	
	private String	orderTypeCode	;//	订单状态
	
	private String queryType;//查询类型
	
	
	
	
	
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getOrderTypeCode() {
		return orderTypeCode;
	}
	public void setOrderTypeCode(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getCentre() {
		return centre;
	}
	public void setCentre(String centre) {
		this.centre = centre;
	}
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public String getCoustomerNo() {
		return coustomerNo;
	}
	public void setCoustomerNo(String coustomerNo) {
		this.coustomerNo = coustomerNo;
	}
	public String getContractorCode() {
		return contractorCode;
	}
	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}
	public String getContractUnit() {
		return contractUnit;
	}
	public void setContractUnit(String contractUnit) {
		this.contractUnit = contractUnit;
	}
	public String getContractorClassName() {
		return contractorClassName;
	}
	public void setContractorClassName(String contractorClassName) {
		this.contractorClassName = contractorClassName;
	}
	public String getContractorClassCode() {
		return contractorClassCode;
	}
	public void setContractorClassCode(String contractorClassCode) {
		this.contractorClassCode = contractorClassCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getTerminalIndustryCode() {
		return terminalIndustryCode;
	}
	public void setTerminalIndustryCode(String terminalIndustryCode) {
		this.terminalIndustryCode = terminalIndustryCode;
	}
	public String getTerminalIndustryCodeName() {
		return terminalIndustryCodeName;
	}
	public void setTerminalIndustryCodeName(String terminalIndustryCodeName) {
		this.terminalIndustryCodeName = terminalIndustryCodeName;
	}
	public Integer getIsSpecialDiscount() {
		return isSpecialDiscount;
	}
	public void setIsSpecialDiscount(Integer isSpecialDiscount) {
		this.isSpecialDiscount = isSpecialDiscount;
	}
	public Integer getIsRreformed() {
		return isRreformed;
	}
	public void setIsRreformed(Integer isRreformed) {
		this.isRreformed = isRreformed;
	}
	public BigDecimal getContractRmbAmount() {
		return contractRmbAmount;
	}
	public void setContractRmbAmount(BigDecimal contractRmbAmount) {
		this.contractRmbAmount = contractRmbAmount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getGrossProfit() {
		return grossProfit;
	}
	public void setGrossProfit(BigDecimal grossProfit) {
		this.grossProfit = grossProfit;
	}
	public Integer getIsLongTermDiscount() {
		return isLongTermDiscount;
	}
	public void setIsLongTermDiscount(Integer isLongTermDiscount) {
		this.isLongTermDiscount = isLongTermDiscount;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialSpecificNumber() {
		return materialSpecificNumber;
	}
	public void setMaterialSpecificNumber(String materialSpecificNumber) {
		this.materialSpecificNumber = materialSpecificNumber;
	}
	public String getMaterialAttribute() {
		return materialAttribute;
	}
	public void setMaterialAttribute(String materialAttribute) {
		this.materialAttribute = materialAttribute;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getMeasureUnitName() {
		return measureUnitName;
	}
	public void setMeasureUnitName(String measureUnitName) {
		this.measureUnitName = measureUnitName;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public Date getEarliestDeliveryDate() {
		return earliestDeliveryDate;
	}
	public void setEarliestDeliveryDate(Date earliestDeliveryDate) {
		this.earliestDeliveryDate = earliestDeliveryDate;
	}
	public String getInstallTermCode() {
		return installTermCode;
	}
	public void setInstallTermCode(String installTermCode) {
		this.installTermCode = installTermCode;
	}
	public String getInstallTermName() {
		return installTermName;
	}
	public void setInstallTermName(String installTermName) {
		this.installTermName = installTermName;
	}
	public String getReceiveType() {
		return receiveType;
	}
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	public String getContactor1id() {
		return contactor1id;
	}
	public void setContactor1id(String contactor1id) {
		this.contactor1id = contactor1id;
	}
	public String getContactor2id() {
		return contactor2id;
	}
	public void setContactor2id(String contactor2id) {
		this.contactor2id = contactor2id;
	}
	public String getContactor3id() {
		return contactor3id;
	}
	public void setContactor3id(String contactor3id) {
		this.contactor3id = contactor3id;
	}
	public String getContactor1Tel() {
		return contactor1Tel;
	}
	public void setContactor1Tel(String contactor1Tel) {
		this.contactor1Tel = contactor1Tel;
	}
	public String getContactor2Tel() {
		return contactor2Tel;
	}
	public void setContactor2Tel(String contactor2Tel) {
		this.contactor2Tel = contactor2Tel;
	}
	public String getContactor3Tel() {
		return contactor3Tel;
	}
	public void setContactor3Tel(String contactor3Tel) {
		this.contactor3Tel = contactor3Tel;
	}
	public String getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}
	public String getSettlementMethod() {
		return settlementMethod;
	}
	public void setSettlementMethod(String settlementMethod) {
		this.settlementMethod = settlementMethod;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public Integer getWarranty() {
		return warranty;
	}
	public void setWarranty(Integer warranty) {
		this.warranty = warranty;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public Double getExchange() {
		return exchange;
	}
	public void setExchange(Double exchange) {
		this.exchange = exchange;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCustomerTypeName() {
		return customerTypeName;
	}
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Double getBodyDiscount() {
		return bodyDiscount;
	}
	public void setBodyDiscount(Double bodyDiscount) {
		this.bodyDiscount = bodyDiscount;
	}
	public Double getMainDiscount() {
		return mainDiscount;
	}
	public void setMainDiscount(Double mainDiscount) {
		this.mainDiscount = mainDiscount;
	}
	
	

	
	
	
}
