package com.qhc.steigenberger.domain;

import java.io.Serializable;

import com.qhc.steigenberger.util.Page;

public class BArea extends Page implements Serializable{
	
	public String code;
	
	public String name;
	
	public String bCityCode;
	
	public Double price;

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

	public String getbCityCode() {
		return bCityCode;
	}

	public void setbCityCode(String bCityCode) {
		this.bCityCode = bCityCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	

}
