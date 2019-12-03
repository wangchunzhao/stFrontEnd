package com.qhc.steigenberger.domain;

import java.util.List;

/**
 * 订单查询类
 * @author zsu4
 *
 */
public class OrderQuery {
	private String orderId;
	private String sequenceNumber;//模糊查询
	private String status;
	private String versionId;
	private String version;
	
	private boolean last = false;
	
	private Integer pageNo = null;
	private Integer pageSize = null;
	//订单管理查询条件
	private String contractNumber;//合同编号   模糊查询
	private String contracterName;//签约单位   模糊查询
	private String officeCode;//区域   精确查询
	private String orderType;//订单类型    精确查询
	private String salesCode;//客户经理code   精确查询 
	private String b2c;//是否有B2C B2C备注里面有值则是true,前台传值1-true，0-false
	private String specialDiscount;//是否特批折扣，审批折扣字段是48的就是否，反之就是是，前台传值1-true，0-false
	private List statusList;  //订单状态集合
	private List dominStatusList;//当支持经理可以下单的情况下，支持经理代办也要看到自己的单子
	private String salesName;//客户经理姓名   模糊查询
	private String createTime;//2019-11-08 - 2019-11-08
	private String submitTime;//2019-11-08 - 2019-11-08
	
	private boolean includeDetail = false;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isIncludeDetail() {
		return includeDetail;
	}
	public void setIncludeDetail(boolean includeDetail) {
		this.includeDetail = includeDetail;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	public String getContracterName() {
		return contracterName;
	}
	public void setContracterName(String contracterName) {
		this.contracterName = contracterName;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
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
	public List getStatusList() {
		return statusList;
	}
	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getB2c() {
		return b2c;
	}
	public void setB2c(String b2c) {
		this.b2c = b2c;
	}
	public String getSpecialDiscount() {
		return specialDiscount;
	}
	public void setSpecialDiscount(String specialDiscount) {
		this.specialDiscount = specialDiscount;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public List getDominStatusList() {
		return dominStatusList;
	}
	public void setDominStatusList(List dominStatusList) {
		this.dominStatusList = dominStatusList;
	}
	
}
