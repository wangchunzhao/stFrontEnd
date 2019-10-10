package com.qhc.steigenberger.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wang@dxc.com
 *
 */
public class Customer {
	private static final long serialVersionUID = -9169262959918008183L;
	public final static String CODE_CUSTOMER = "59870645008146f9938f7e8818031778";
	public final static String NAME_CUSTOMER = "Customer master date";

	private String code;
	private String name;
	private Date changedDate;
	private String address;
	private String groupCode;
	private String clazzCode;
	private String clazzName;
	private String levelCode;
	private String affiliationCode;
	private String affiliationName;
	
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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getClazzCode() {
		return clazzCode;
	}
	public void setClazzCode(String clazzCode) {
		this.clazzCode = clazzCode;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	
	
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
	
		
	public String getAffiliationCode() {
		return affiliationCode;
	}
	public void setAffiliationCode(String affiliationCode) {
		this.affiliationCode = affiliationCode;
	}
	public String getAffiliationName() {
		return affiliationName;
	}
	public void setAffiliationName(String affiliationName) {
		this.affiliationName = affiliationName;
	}
	public String getClazzName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	
}
