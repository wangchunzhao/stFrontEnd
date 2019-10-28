package com.qhc.steigenberger.domain;

/**
 * 订单查询类
 * @author zsu4
 *
 */
public class OrderQuery {
	private String orderId;
	private String sequenceNumber;
	private String status;
	private String versionId;
	private String version;
	
	private boolean last = false;
	
	private Integer pageNo = null;
	private Integer pageSize = null;
	//订单管理查询条件
	private String contractNumber;//合同编号
	private String contracterCode;//签约单位
	private String officeCode;//区域
	private String orderType;//订单类型
	private String b2c;//是否有B2C
	private String specialDiscount;//是否特批折扣
	
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
	public String getContracterCode() {
		return contracterCode;
	}
	public void setContracterCode(String contracterCode) {
		this.contracterCode = contracterCode;
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
	
}
