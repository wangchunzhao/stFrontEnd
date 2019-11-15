package com.qhc.steigenberger.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Function: Data transfer object. <br> 
 *
 * @author walker
 */
public class Contract
		implements Serializable {
	private static final long serialVersionUID = -7870996928236577566L;
	
	/* Id */
	private Integer id = null;

	/* SequenceNumber */
	private String sequenceNumber = null;

	/* PartyaCode */
	private String partyaCode = null;

	/* PartyaName */
	private String partyaName = null;

	/* PartyaMail */
	private String partyaMail = null;

	/* AmountOnContract */
	private BigDecimal amountOnContract = null;

	/* DeliveryDaysAfterPrepay */
	private Short deliveryDaysAfterPrepay = null;

	/* ClientName */
	private String clientName = null;

	/* InstallLocation */
	private String installLocation = null;

	/* QualityStand */
	private String qualityStand = null;

	/* Settlement */
	private String settlement = null;

	/* ParyaAddress */
	private String paryaAddress = null;

	/* InvoiceAddress */
	private String invoiceAddress = null;

	/* Broker */
	private String broker = null;

	/* InvoiceReceiver */
	private String invoiceReceiver = null;

	/* InvoiceTel */
	private String invoiceTel = null;

	/* InvoicePostCode */
	private String invoicePostCode = null;

	/* CompanyTel */
	private String companyTel = null;

	/* BankName */
	private String bankName = null;

	/* AccountNumber */
	private String accountNumber = null;

	/* OrderVersionId */
	private String orderVersionId = null;

	/* ReceiveTermsCode */
	private String receiveTermsCode = null;

	/* ReceiveTermsName */
	private String receiveTermsName = null;

	/* KAcceptanceCriteriaCode */
	private String acceptanceCriteriaCode = null;

	/* Mail */
	private String mail = null;

	/* Contractor1Id */
	private String contractor1Id = null;

	/* Contractor1Tel */
	private String contractor1Tel = null;

	/* Contractor2Id */
	private String contractor2Id = null;

	/* Contractor2Tel */
	private String contractor2Tel = null;

	/* Contractor3Id */
	private String contractor3Id = null;

	/* Contractor3Tel */
	private String contractor3Tel = null;

	/* 合同制作时间 */
	private Date productionTime = null;

	/* 合同发送时间 */
	private Date sendTime = null;

	/* 合同状态：1 未发送 2 已发送 */
	private Integer status = null;

	// 订单信息
	/* order id */
	private String orderId = null;

	/* order version id */
	private String versionId = null;

	/* order version */
	private String version = null;
	
	private String contractNumber = null;
	
	private String opteratorDomainId = null;
	
	private String contractorCode = null;
	
	private String contractorName = null;
	
	private String contractorClassCode = null;
	
	private String contractorClassName = null;
	
	private Date createTime = null;


	public Contract(){
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	 
	public String getSequenceNumber() {
		return this.sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	 
	public String getPartyaCode() {
		return this.partyaCode;
	}

	public void setPartyaCode(String partyaCode) {
		this.partyaCode = partyaCode;
	}
	 
	public String getPartyaName() {
		return this.partyaName;
	}

	public void setPartyaName(String partyaName) {
		this.partyaName = partyaName;
	}
	 
	public String getPartyaMail() {
		return this.partyaMail;
	}

	public void setPartyaMail(String partyaMail) {
		this.partyaMail = partyaMail;
	}
	 
	public BigDecimal getAmountOnContract() {
		return this.amountOnContract;
	}

	public void setAmountOnContract(BigDecimal amountOnContract) {
		this.amountOnContract = amountOnContract;
	}
	 
	public Short getDeliveryDaysAfterPrepay() {
		return this.deliveryDaysAfterPrepay;
	}

	public void setDeliveryDaysAfterPrepay(Short deliveryDaysAfterPrepay) {
		this.deliveryDaysAfterPrepay = deliveryDaysAfterPrepay;
	}
	 
	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	 
	public String getInstallLocation() {
		return this.installLocation;
	}

	public void setInstallLocation(String installLocation) {
		this.installLocation = installLocation;
	}
	 
	public String getQualityStand() {
		return this.qualityStand;
	}

	public void setQualityStand(String qualityStand) {
		this.qualityStand = qualityStand;
	}
	 
	public String getSettlement() {
		return this.settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	 
	public String getParyaAddress() {
		return this.paryaAddress;
	}

	public void setParyaAddress(String paryaAddress) {
		this.paryaAddress = paryaAddress;
	}
	 
	public String getInvoiceAddress() {
		return this.invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
	 
	public String getBroker() {
		return this.broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}
	 
	public String getInvoiceReceiver() {
		return this.invoiceReceiver;
	}

	public void setInvoiceReceiver(String invoiceReceiver) {
		this.invoiceReceiver = invoiceReceiver;
	}
	 
	public String getInvoiceTel() {
		return this.invoiceTel;
	}

	public void setInvoiceTel(String invoiceTel) {
		this.invoiceTel = invoiceTel;
	}
	 
	public String getInvoicePostCode() {
		return this.invoicePostCode;
	}

	public void setInvoicePostCode(String invoicePostCode) {
		this.invoicePostCode = invoicePostCode;
	}
	 
	public String getCompanyTel() {
		return this.companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	 
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	 
	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	 
	public String getReceiveTermsCode() {
		return this.receiveTermsCode;
	}

	public void setReceiveTermsCode(String receiveTermsCode) {
		this.receiveTermsCode = receiveTermsCode;
	}
	 
	public String getReceiveTermsName() {
		return this.receiveTermsName;
	}

	public void setReceiveTermsName(String receiveTermsName) {
		this.receiveTermsName = receiveTermsName;
	}
	 
	public String getOrderVersionId() {
		return orderVersionId;
	}

	public void setOrderVersionId(String orderVersionId) {
		this.orderVersionId = orderVersionId;
	}

	public String getAcceptanceCriteriaCode() {
		return acceptanceCriteriaCode;
	}

	public void setAcceptanceCriteriaCode(String acceptanceCriteriaCode) {
		this.acceptanceCriteriaCode = acceptanceCriteriaCode;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	 
	public String getContractor1Id() {
		return this.contractor1Id;
	}

	public void setContractor1Id(String contractor1Id) {
		this.contractor1Id = contractor1Id;
	}
	 
	public String getContractor1Tel() {
		return this.contractor1Tel;
	}

	public void setContractor1Tel(String contractor1Tel) {
		this.contractor1Tel = contractor1Tel;
	}
	 
	public String getContractor2Id() {
		return this.contractor2Id;
	}

	public void setContractor2Id(String contractor2Id) {
		this.contractor2Id = contractor2Id;
	}
	 
	public String getContractor2Tel() {
		return this.contractor2Tel;
	}

	public void setContractor2Tel(String contractor2Tel) {
		this.contractor2Tel = contractor2Tel;
	}
	 
	public String getContractor3Id() {
		return this.contractor3Id;
	}

	public void setContractor3Id(String contractor3Id) {
		this.contractor3Id = contractor3Id;
	}
	 
	public String getContractor3Tel() {
		return this.contractor3Tel;
	}

	public void setContractor3Tel(String contractor3Tel) {
		this.contractor3Tel = contractor3Tel;
	}
	 
	public Date getProductionTime() {
		return this.productionTime;
	}

	public void setProductionTime(Date productionTime) {
		this.productionTime = productionTime;
	}
	 
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	 
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	 
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getOpteratorDomainId() {
		return opteratorDomainId;
	}

	public void setOpteratorDomainId(String opteratorDomainId) {
		this.opteratorDomainId = opteratorDomainId;
	}

	public String getContractorCode() {
		return contractorCode;
	}

	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getContractorClassCode() {
		return contractorClassCode;
	}

	public void setContractorClassCode(String contractorClassCode) {
		this.contractorClassCode = contractorClassCode;
	}

	public String getContractorClassName() {
		return contractorClassName;
	}

	public void setContractorClassName(String contractorClassName) {
		this.contractorClassName = contractorClassName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Contract other = (Contract) obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id));
	}
	
	public String toString() {
	    final String tab = "  ";
	    String str = "";
	    str = "Contract ( "
	        + "id = " + this.id + tab
	        + "sequenceNumber = " + this.sequenceNumber + tab
	        + "partyaCode = " + this.partyaCode + tab
	        + "partyaName = " + this.partyaName + tab
	        + "partyaMail = " + this.partyaMail + tab
	        + "amountOnContract = " + this.amountOnContract + tab
	        + "deliveryDaysAfterPrepay = " + this.deliveryDaysAfterPrepay + tab
	        + "clientName = " + this.clientName + tab
	        + "installLocation = " + this.installLocation + tab
	        + "qualityStand = " + this.qualityStand + tab
	        + "settlement = " + this.settlement + tab
	        + "paryaAddress = " + this.paryaAddress + tab
	        + "invoiceAddress = " + this.invoiceAddress + tab
	        + "broker = " + this.broker + tab
	        + "invoiceReceiver = " + this.invoiceReceiver + tab
	        + "invoiceTel = " + this.invoiceTel + tab
	        + "invoicePostCode = " + this.invoicePostCode + tab
	        + "companyTel = " + this.companyTel + tab
	        + "bankName = " + this.bankName + tab
	        + "accountNumber = " + this.accountNumber + tab
	        + "kOrderVersionId = " + this.orderVersionId + tab
	        + "receiveTermsCode = " + this.receiveTermsCode + tab
	        + "receiveTermsName = " + this.receiveTermsName + tab
	        + "kAcceptanceCriteriaCode = " + this.acceptanceCriteriaCode + tab
	        + "mail = " + this.mail + tab
	        + "contractor1Id = " + this.contractor1Id + tab
	        + "contractor1Tel = " + this.contractor1Tel + tab
	        + "contractor2Id = " + this.contractor2Id + tab
	        + "contractor2Tel = " + this.contractor2Tel + tab
	        + "contractor3Id = " + this.contractor3Id + tab
	        + "contractor3Tel = " + this.contractor3Tel + tab
	        + "productionTime = " + this.productionTime + tab
	        + "sendTime = " + this.sendTime + tab
	        + "status = " + this.status + tab
	        + " )";
	
	    return str;
	}

}
