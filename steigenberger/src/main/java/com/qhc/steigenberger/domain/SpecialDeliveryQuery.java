package com.qhc.steigenberger.domain;

/**
 * @author lizuoshan
 *
 */
public class SpecialDeliveryQuery {
    
    int pageNo = 0;
    
    int pageSize = 10;

    public Integer id;

    public String sequenceNumber;

    public Integer orderInfoId;

    public String orderStatus;

    public String createTime;

    private String salesTel;

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
    
    private Integer applyStatus;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(Integer orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
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

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

}
