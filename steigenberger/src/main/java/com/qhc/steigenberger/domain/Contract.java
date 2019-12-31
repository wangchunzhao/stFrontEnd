package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Function: Data transfer object. <br>
 *
 * @author walker
 */
public class Contract implements Serializable {
	private static final long serialVersionUID = -7870996928236577566L;

	/* Id */
	private Integer id = null;

	/* OrderInfoId */
	private Integer orderInfoId = null;

	/* 收到预付款后发货时间 */
	private Integer deliveryDays = null;

	/* 终端用户店名 */
	private String clientName = null;

	/* 安装地点 */
	private String installLocation = null;

	/*
	 * 验收标准 1001 需方负责安装调试 1002 供方负责安装调试
	 */
	private String acceptanceCriteriaCode = null;

	/* 签约单位邮箱 */
	private String customerEmail = null;

	/* 发票地址 */
	private String invoiceAddress = null;

	/* 邮政编码 */
	private String invoicePostCode = null;

	/* 发票接收人 */
	private String invoiceReceiver = null;

	/* 联系电话 */
	private String invoiceTel = null;

	/* 委托代理人 */
	private String broker = null;

	/* 公司电话 */
	private String companyTel = null;

	/* 银行名称 */
	private String bankName = null;

	/* 银行账号 */
	private String accountNumber = null;

	/* 单位地址 */
	private String companyAddress = null;

	/* 单位邮政编码 */
	private String companyPostCode = null;

	/*
	 * 合同状态 01 已制作 02 已发送 03 已发送 04 已上传 05 客户已签署 06 已签署
	 */
	private String status = null;

	/* 合同制作人 */
	private String creater = null;

	/* 合同制作时间 */
	private Date createTime = null;

	/* 合同发送人 */
	private String sender = null;

	/* 合同发送时间 */
	private Date sendTime = null;

	/* 合同签署人 */
	private String signer = null;

	/* 合同签署时间 */
	private Date signTime = null;

	/* 合同文档Hash值 */
	private String fileHashcode = null;

	/* 电子签约中合同Id，存放上上签中的contractId */
	private String signContractid = null;

	/**
	 * Order information
	 */
	private String version = null;
	
	/* 订单创建时间 */
	private Date orderCreateTime = null;

	/* SequenceNumber */
	private String sequenceNumber = null;

	private String contractNumber = null;

	private String contractManager = null;

	private String customerCode;// 签约单位 Contract unit
	private String customerName;// 签约单位 Contract Name

	/* AmountOnContract */
	private Double contractRmbValue = null;

	/* Settlement */
	private String paymentType = null;

	/* ReceiveTermsCode */
	private String receiveType = null;

	/* ReceiveTermsName */
	private String receiveTypeName = null;

	private String contactor1Id;// 授权人1及身份证号
	private String contactor1Tel;// 授权人1电话
	private String contactor2Id;// 授权人2及身份证号
	private String contactor2Tel;// 授权人2电话
	private String contactor3Id;// 授权人3及身份证号
	private String contactor3Tel;// 授权人3电话

	private String customerClazz;// 性质分类代码，经销商/直签
	private String customerClazzName;// 性质分类名称

	private String installType;//安装方式 installation
	private String installTypeName;//安装方式名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderInfoId() {
		return orderInfoId;
	}
	public void setOrderInfoId(Integer orderInfoId) {
		this.orderInfoId = orderInfoId;
	}
	public Integer getDeliveryDays() {
		return deliveryDays;
	}
	public void setDeliveryDays(Integer deliveryDays) {
		this.deliveryDays = deliveryDays;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getInstallLocation() {
		return installLocation;
	}
	public void setInstallLocation(String installLocation) {
		this.installLocation = installLocation;
	}
	public String getAcceptanceCriteriaCode() {
		return acceptanceCriteriaCode;
	}
	public void setAcceptanceCriteriaCode(String acceptanceCriteriaCode) {
		this.acceptanceCriteriaCode = acceptanceCriteriaCode;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getInvoiceAddress() {
		return invoiceAddress;
	}
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
	public String getInvoicePostCode() {
		return invoicePostCode;
	}
	public void setInvoicePostCode(String invoicePostCode) {
		this.invoicePostCode = invoicePostCode;
	}
	public String getInvoiceReceiver() {
		return invoiceReceiver;
	}
	public void setInvoiceReceiver(String invoiceReceiver) {
		this.invoiceReceiver = invoiceReceiver;
	}
	public String getInvoiceTel() {
		return invoiceTel;
	}
	public void setInvoiceTel(String invoiceTel) {
		this.invoiceTel = invoiceTel;
	}
	public String getBroker() {
		return broker;
	}
	public void setBroker(String broker) {
		this.broker = broker;
	}
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyPostCode() {
		return companyPostCode;
	}
	public void setCompanyPostCode(String companyPostCode) {
		this.companyPostCode = companyPostCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getSigner() {
		return signer;
	}
	public void setSigner(String signer) {
		this.signer = signer;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getFileHashcode() {
		return fileHashcode;
	}
	public void setFileHashcode(String fileHashcode) {
		this.fileHashcode = fileHashcode;
	}
	public String getSignContractid() {
		return signContractid;
	}
	public void setSignContractid(String signContractid) {
		this.signContractid = signContractid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
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
	public String getContractManager() {
		return contractManager;
	}
	public void setContractManager(String contractManager) {
		this.contractManager = contractManager;
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
	public Double getContractRmbValue() {
		return contractRmbValue;
	}
	public void setContractRmbValue(Double contractRmbValue) {
		this.contractRmbValue = contractRmbValue;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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
	@Override
	public String toString() {
		return "Contract [id=" + id + ", orderInfoId=" + orderInfoId + ", deliveryDays=" + deliveryDays
				+ ", clientName=" + clientName + ", installLocation=" + installLocation + ", acceptanceCriteriaCode="
				+ acceptanceCriteriaCode + ", customerEmail=" + customerEmail + ", invoiceAddress=" + invoiceAddress
				+ ", invoicePostCode=" + invoicePostCode + ", invoiceReceiver=" + invoiceReceiver + ", invoiceTel="
				+ invoiceTel + ", broker=" + broker + ", companyTel=" + companyTel + ", bankName=" + bankName
				+ ", accountNumber=" + accountNumber + ", companyAddress=" + companyAddress + ", companyPostCode="
				+ companyPostCode + ", status=" + status + ", creater=" + creater + ", createTime=" + createTime
				+ ", sender=" + sender + ", sendTime=" + sendTime + ", signer=" + signer + ", signTime=" + signTime
				+ ", fileHashcode=" + fileHashcode + ", signContractid=" + signContractid + ", version=" + version
				+ ", orderCreateTime=" + orderCreateTime + ", sequenceNumber=" + sequenceNumber + ", contractNumber="
				+ contractNumber + ", contractManager=" + contractManager + ", customerCode=" + customerCode
				+ ", customerName=" + customerName + ", contractRmbValue=" + contractRmbValue + ", paymentType="
				+ paymentType + ", receiveType=" + receiveType + ", receiveTypeName=" + receiveTypeName
				+ ", contactor1Id=" + contactor1Id + ", contactor1Tel=" + contactor1Tel + ", contactor2Id="
				+ contactor2Id + ", contactor2Tel=" + contactor2Tel + ", contactor3Id=" + contactor3Id
				+ ", contactor3Tel=" + contactor3Tel + ", customerClazz=" + customerClazz + ", customerClazzName="
				+ customerClazzName + ", installType=" + installType + ", installTypeName=" + installTypeName + "]";
	}

}
