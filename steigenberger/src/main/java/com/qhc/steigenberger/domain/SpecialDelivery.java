package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * @author lizuoshan
 *
 */
public class SpecialDelivery implements Serializable{
	
    
	public Integer applyId;
    
	public String applyer;//申请人
    
    public String approver;//通过人
    
    public Date applyTime;//申请时间
    
    public Date approvalTime;//通过时间
	
	private String receiveMailTime;
	
	private int applyStatus;
	
	private String contractTime;
	
	private String payAdvancePaymentTime;
	
	private String remark;
	
	private String enclosurePath;
	
	private String enclosureName;
    
	public Integer kOrderVersionId;
	
	public String startTime;
	
	public String endTime;

	
	
	
	
	
	
	
	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
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

	public String getReceiveMailTime() {
		return receiveMailTime;
	}

	public void setReceiveMailTime(String receiveMailTime) {
		this.receiveMailTime = receiveMailTime;
	}

	public int getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
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

	public Integer getkOrderVersionId() {
		return kOrderVersionId;
	}

	public void setkOrderVersionId(Integer kOrderVersionId) {
		this.kOrderVersionId = kOrderVersionId;
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
