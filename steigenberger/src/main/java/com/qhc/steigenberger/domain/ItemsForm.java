package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;




public class ItemsForm implements Serializable{
	
	private static final long serialVersionUID = -1429807827446950753L;

	public String id;
	
	public Date earliestProductDate;//最早交货时间
	
	public Date earliestDeliveryDate;//最早发货时间
	
	public String comments;
	
	public String kOrderInfoId;

	public String operator;

	public int type;
	
	public Date optTime;
	
	public int isReady;
	
	public List<ItemDetails> detailsList;
	
	
	
	

	






	public List<ItemDetails> getDetailsList() {
		return detailsList;
	}


	public void setDetailsList(List<ItemDetails> detailsList) {
		this.detailsList = detailsList;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public Date getEarliestDeliveryDate() {
		return earliestDeliveryDate;
	}


	public void setEarliestDeliveryDate(Date earliestDeliveryDate) {
		this.earliestDeliveryDate = earliestDeliveryDate;
	}


	public Date getEarliestProductDate() {
		return earliestProductDate;
	}


	public void setEarliestProductDate(Date earliestProductDate) {
		this.earliestProductDate = earliestProductDate;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getkOrderInfoId() {
		return kOrderInfoId;
	}


	public void setkOrderInfoId(String kOrderInfoId) {
		this.kOrderInfoId = kOrderInfoId;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public Date getOptTime() {
		return optTime;
	}


	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}


	public int getIsReady() {
		return isReady;
	}


	public void setIsReady(int isReady) {
		this.isReady = isReady;
	}






}
