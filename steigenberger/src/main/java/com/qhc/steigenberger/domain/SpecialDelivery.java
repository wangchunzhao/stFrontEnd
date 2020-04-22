package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lizuoshan
 *
 */
public class SpecialDelivery {
	
	/* Id */
	private Integer id = null;

	/* OrderInfoId */
	private Integer orderInfoId = null;

	/* Applyer */
	private String applyer = null;

	/* Approver */
	private String approver = null;

	/* ApplyTime */
	private Date applyTime = null;

	/* ApprovalTime */
	private Date approvalTime = null;

	/* 0: 新建
            1：同意
            2：驳回 */
	private Integer applyStatus = null;

	/* ReceiveMailTime */
	private String receiveMailTime = null;

	/* ContractTime */
	private String contractTime = null;

	/* PayAdvancePaymentTime */
	private String payAdvancePaymentTime = null;

	/* Remark */
	private String remark = null;

	/* EnclosurePath */
	private String enclosurePath = null;

	/* EnclosureName */
	private String enclosureName = null;

	/* 订单信息 */
	private String sequenceNumber;
    private String contractNumber;
    private String customerClazz;

	// 订单类型，性质分类，ZH0D  经销商订单/  ZH0M    备货订单/ZH0T   大客户订单，固定
	private String orderType;

	private String stOrderType;

	private Date createTime;

	/**
	 * order status
	 */
	private String status;
	
	private String salesCode;//客户经理 Customer manager
//	private String salesName;//客户经理 Customer manager
    private String customerCode;//签约单位 Contract unit
    private String customerName;//签约单位 Contract Name
    
    private double discount;//合并折扣

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

	public String getApplyer() {
		return applyer;
	}

	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getReceiveMailTime() {
		return receiveMailTime;
	}

	public void setReceiveMailTime(String receiveMailTime) {
		this.receiveMailTime = receiveMailTime;
	}

	public String getContractTime() {
		return contractTime;
	}

	public void setContractTime(String contractTime) {
		this.contractTime = contractTime;
	}

	public String getPayAdvancePaymentTime() {
		return payAdvancePaymentTime;
	}

	public void setPayAdvancePaymentTime(String payAdvancePaymentTime) {
		this.payAdvancePaymentTime = payAdvancePaymentTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEnclosurePath() {
		return enclosurePath;
	}

	public void setEnclosurePath(String enclosurePath) {
		this.enclosurePath = enclosurePath;
	}

	public String getEnclosureName() {
		return enclosureName;
	}

	public void setEnclosureName(String enclosureName) {
		this.enclosureName = enclosureName;
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

  public String getCustomerClazz() {
    return customerClazz;
  }

  public void setContractNumber(String contractNumber) {
    this.contractNumber = contractNumber;
  }

  public void setCustomerClazz(String customerClazz) {
    this.customerClazz = customerClazz;
  }

  public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStOrderType() {
		return stOrderType;
	}

	public void setStOrderType(String stOrderType) {
		this.stOrderType = stOrderType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}

  public String getCustomerCode() {
    return customerCode;
  }

  public String getCustomerName() {
    return customerName;
  }

  public double getDiscount() {
    return discount;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
  }

}
