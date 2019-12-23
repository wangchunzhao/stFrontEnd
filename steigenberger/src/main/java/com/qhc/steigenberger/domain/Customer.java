package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * @author wang@dxc.com
 *
 */
public class Customer implements Serializable {
	private static final long serialVersionUID = -9169262959918008183L;


	private String code; // 客户编码，专用号
	private String name; // 客户名称，规格型号
	private Date changedDate;
	private String address; // 地址
	private String clazzCode; // 客户分类
	private String clazzName;

	private String industryCodeCode; // 客户级别
	private String industryCodeName;
	private String industryCode; // 大客户，隶属关系
	private String industryName;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getChangedDate() {
		return changedDate;
	}
	public void setChangedDate(Date changedDate) {
		this.changedDate = changedDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getClazzCode() {
		return clazzCode;
	}
	public void setClazzCode(String clazzCode) {
		this.clazzCode = clazzCode;
	}
	public String getClazzName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	public String getIndustryCodeCode() {
		return industryCodeCode;
	}
	public void setIndustryCodeCode(String industryCodeCode) {
		this.industryCodeCode = industryCodeCode;
	}
	public String getIndustryCodeName() {
		return industryCodeName;
	}
	public void setIndustryCodeName(String industryCodeName) {
		this.industryCodeName = industryCodeName;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
}
