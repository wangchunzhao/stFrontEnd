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
	
}
