package com.qhc.steigenberger.domain;

import java.io.Serializable;

import com.qhc.steigenberger.util.Page;

public class BProvince extends Page implements Serializable{
	
	public String code;
	
	public String name;

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
	
	

}
