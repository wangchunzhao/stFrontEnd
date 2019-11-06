package com.qhc.steigenberger.domain;

import java.math.BigDecimal;

public class MaterialGroups {
	
	private String code;

	private String name;
    
	private String materialGroupOrderCode;
	
	private BigDecimal amount;//金额
	
	private BigDecimal excludingTaxAmount;//不含税金额
	
	private BigDecimal wtwCost;//wtw成本
	
	private BigDecimal cost;//成本
	
	private BigDecimal wtwGrossProfit;//wtw毛利
	
	private BigDecimal grossProfit;//毛利
	
	private Double wtwGrossProfitMargin;//wtw毛利率	
	
	private Double grossProfitMargin;//毛利率	

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

	public String getMaterialGroupOrderCode() {
		return materialGroupOrderCode;
	}

	public void setMaterialGroupOrderCode(String materialGroupOrderCode) {
		this.materialGroupOrderCode = materialGroupOrderCode;
	}

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

	public BigDecimal getWtwCost() {
		return wtwCost;
	}

	public void setWtwCost(BigDecimal wtwCost) {
		this.wtwCost = wtwCost;
	}

	public BigDecimal getWtwGrossProfit() {
		return wtwGrossProfit;
	}

	public void setWtwGrossProfit(BigDecimal wtwGrossProfit) {
		this.wtwGrossProfit = wtwGrossProfit;
	}

	public Double getWtwGrossProfitMargin() {
		return wtwGrossProfitMargin;
	}

	public void setWtwGrossProfitMargin(Double wtwGrossProfitMargin) {
		this.wtwGrossProfitMargin = wtwGrossProfitMargin;
	}
}
