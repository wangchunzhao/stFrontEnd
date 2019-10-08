package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalesGroup implements Serializable {
	
	private static final long serialVersionUID = 1176483521099061923L;

    private String code;
	
    private String name;
	
    private String officeCode;
	
	private BigDecimal amount;//金额
	
	private BigDecimal excludingTaxAmount;//不含税金额
	
	private BigDecimal cost;//成本
	
	private BigDecimal grossProfit;//毛利
	
	private Double grossProfitMargin;//毛利率
	
	
	
	
	
	
	

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getExcludingTaxAmount() {
		return excludingTaxAmount;
	}

	public void setExcludingTaxAmount(BigDecimal excludingTaxAmount) {
		this.excludingTaxAmount = excludingTaxAmount;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(BigDecimal grossProfit) {
		this.grossProfit = grossProfit;
	}

	public Double getGrossProfitMargin() {
		return grossProfitMargin;
	}

	public void setGrossProfitMargin(Double grossProfitMargin) {
		this.grossProfitMargin = grossProfitMargin;
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

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
