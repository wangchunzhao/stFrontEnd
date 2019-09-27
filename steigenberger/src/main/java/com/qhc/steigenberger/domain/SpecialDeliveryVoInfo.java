package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;

import com.qhc.steigenberger.util.Page;

/**
 * @author lizuoshan
 *
 */
public class SpecialDeliveryVoInfo extends Page implements Serializable{
 
	public String id;
	
	public String sequenceNumber;
	
	public String orderTypeCode;
	
	public Date createTime;
	
	public String ownerDomainId;
	
	private String	ownerName;
	
	private String	salesTel;
	
	private String	contractorCode;
	
	private String	contractorName;
	
	private String	contractorClassCode;
	
	private String	contractorClassName;
	
	private String	officeCode;
	
	
	public Integer kOrderVersionId;
	
	public Double distcount;

	public String startTime;
	
	public String endTime;
	
	
	
	
	
	

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

	public String getOrderTypeCode() {
		return orderTypeCode;
	}

	public void setOrderTypeCode(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOwnerDomainId() {
		return ownerDomainId;
	}

	public void setOwnerDomainId(String ownerDomainId) {
		this.ownerDomainId = ownerDomainId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSalesTel() {
		return salesTel;
	}

	public void setSalesTel(String salesTel) {
		this.salesTel = salesTel;
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

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public Integer getkOrderVersionId() {
		return kOrderVersionId;
	}

	public void setkOrderVersionId(Integer kOrderVersionId) {
		this.kOrderVersionId = kOrderVersionId;
	}

	public Double getDistcount() {
		return distcount;
	}

	public void setDistcount(Double distcount) {
		this.distcount = distcount;
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
	
	
	
	

	
	
	
	


}
