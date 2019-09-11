package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;

import com.qhc.steigenberger.util.Page;

public class KOrderInfo extends Page implements Serializable {
	
	private int id;
	
	private String contractNo;
	
	private String contractUnit;
	
	private int area;
	
	private int orderType;
	
	private int b2c;
	
	private int specialDiscount;
	
	private Date createTime;
	
	private int status;
	
	private int sapStatus;
	
	private int createId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractUnit() {
		return contractUnit;
	}

	public void setContractUnit(String contractUnit) {
		this.contractUnit = contractUnit;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getB2c() {
		return b2c;
	}

	public void setB2c(int b2c) {
		this.b2c = b2c;
	}

	public int getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(int specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSapStatus() {
		return sapStatus;
	}

	public void setSapStatus(int sapStatus) {
		this.sapStatus = sapStatus;
	}

	public int getCreateId() {
		return createId;
	}

	public void setCreateId(int createId) {
		this.createId = createId;
	}
	
	
}
