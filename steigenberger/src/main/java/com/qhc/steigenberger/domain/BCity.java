package com.qhc.steigenberger.domain;

import java.io.Serializable;

import com.qhc.steigenberger.util.Page;

public class BCity extends Page implements Serializable{
	
	public String code;
	
	public String name;
	
	public String bProvinceCode;

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

	public String getbProvinceCode() {
		return bProvinceCode;
	}

	public void setbProvinceCode(String bProvinceCode) {
		this.bProvinceCode = bProvinceCode;
	}
	
	

}
