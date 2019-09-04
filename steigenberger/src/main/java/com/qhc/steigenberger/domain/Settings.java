package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.stereotype.Component;


@Component
public class Settings implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String sValue;
	private Date enableDate;
	private String comment;
	private String operater;
	private String optTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getsValue() {
		return sValue;
	}
	public void setsValue(String sValue) {
		this.sValue = sValue;
	}
	public Date getEnableDate() {
		return enableDate;
	}
	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public String getOptTime() {
		return optTime;
	}
	public void setOptTime(String optTime) {
		this.optTime = optTime;
	}
	
	

}
