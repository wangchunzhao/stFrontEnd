package com.qhc.steigenberger.domain;

/**
 * @author lizuoshan
 *
 */
public class SpecialDeliveryQuery {
	
	int pageNo = 0;
	
	int pageSize = 10;

	public String id;

	public String sequenceNumber;

	public String orderInfoId;

	public String orderTypeCode;

	public String createTime;

	private String salesTel;

	private String contractorCode;

	private String contractorName;

	private String contractorClassCode;

	private String contractorClassName;

	public Integer kOrderVersionId;

	public Double distcount;

	public String startTime;

	public String endTime;

    /**
     * 合同号
     */
    public String contractNumber;

    /**
     * 签约单位
     */
    public String customerName;

    /**
     * 客户经理
     */
    public String salesCode;

    /**
     * 客户经理
     */
    public String salesName;
    
    /**
     * 区域
     */
    private String officeCode;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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

	public String getOrderInfoId() {
		return orderInfoId;
	}

	public void setOrderInfoId(String orderInfoId) {
		this.orderInfoId = orderInfoId;
	}

	public String getOrderTypeCode() {
		return orderTypeCode;
	}

	public void setOrderTypeCode(String orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

    public String getContractNumber() {
        return contractNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getSalesCode() {
        return salesCode;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

}
