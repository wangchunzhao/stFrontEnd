package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;


@Component
public class Parameter implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String sValue;
	private Date enableDate;
	private String comment;
	private String operater;
	private String optTime;
	
	private String preValue;
	private Date preEnableDate;
	private String afterValue;
	private Date afterEnableDate;
	
	
	public Date getPreEnableDate() {
		return preEnableDate;
	}
	public void setPreEnableDate(Date preEnableDate) {
		this.preEnableDate = preEnableDate;
	}
	public Date getAfterEnableDate() {
		return afterEnableDate;
	}
	public void setAfterEnableDate(Date afterEnableDate) {
		this.afterEnableDate = afterEnableDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPreValue() {
		return preValue;
	}
	public void setPreValue(String preValue) {
		this.preValue = preValue;
	}
	public String getAfterValue() {
		return afterValue;
	}
	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
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
	public void setId(int id) {
		this.id = id;
	}
	
	

}
