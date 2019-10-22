package com.qhc.steigenberger.domain;

import java.util.Date;

public class OrderVersion {
	String id;
	String version;
	Integer status;
	Date createTime;
	String kOrdersId;
	
	String parentVersionId;
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
	public String getParentVersionId() {
		return parentVersionId;
	}
	public void setParentVersionId(String parentVersionId) {
		this.parentVersionId = parentVersionId;
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
