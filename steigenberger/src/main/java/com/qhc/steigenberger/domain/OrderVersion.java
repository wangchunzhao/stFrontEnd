package com.qhc.steigenberger.domain;

import java.util.Date;

import javax.persistence.Column;

public class OrderVersion {
	String id;
	String version;
	Integer status;
	Date createTime;
	String kOrdersId;
	
	private Date submitDate = null;
	private Date bpmSubmitTime = null;
	Date optTime;
	String kOrderInfoId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public Date getBpmSubmitTime() {
		return bpmSubmitTime;
	}
	public void setBpmSubmitTime(Date bpmSubmitTime) {
		this.bpmSubmitTime = bpmSubmitTime;
	}
	public Date getOptTime() {
		return optTime;
	}
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}
	public String getkOrdersId() {
		return kOrdersId;
	}
	public void setkOrdersId(String kOrdersId) {
		this.kOrdersId = kOrdersId;
	}
	public String getkOrderInfoId() {
		return kOrderInfoId;
	}
	public void setkOrderInfoId(String kOrderInfoId) {
		this.kOrderInfoId = kOrderInfoId;
	}
}
