package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;



public class ItemDetails implements Serializable{
	
	private static final long serialVersionUID = -1429807827446950753L;

	public String id;
	
	public String kFormsId;

	
	//产品规格型号
	public String materialCode;
	
	public String materialName;
	
	//物料专用号
	public String materialSpecificNumber;
	
	//物料属性
	public String materialAttribute;
	
	//合同数量
	public Integer quantity;
	
	//销售单价
	
	//销售金额
	public BigDecimal amount;
	
	//单位 
	public String measureUnitCode;
	
	public String measureUnitName;

	public String b2cComments;
	
	public String type;
	
	public Integer rowNumber;
	
	public String specialCode;
	
	public String materialGroupCode;
	
	public String materialGroupName;
	
	public BigDecimal transfterPrice;
	
	public Double discount;
	
	public String itemCategory;
	
	public String itemRequirementPlan;
	
	public BigDecimal freight;
	
	public BigDecimal standardPrice;
	
	
	
	
	
	

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getkFormsId() {
		return kFormsId;
	}

	public void setkFormsId(String kFormsId) {
		this.kFormsId = kFormsId;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialSpecificNumber() {
		return materialSpecificNumber;
	}

	public void setMaterialSpecificNumber(String materialSpecificNumber) {
		this.materialSpecificNumber = materialSpecificNumber;
	}

	public String getMaterialAttribute() {
		return materialAttribute;
	}

	public void setMaterialAttribute(String materialAttribute) {
		this.materialAttribute = materialAttribute;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMeasureUnitCode() {
		return measureUnitCode;
	}

	public void setMeasureUnitCode(String measureUnitCode) {
		this.measureUnitCode = measureUnitCode;
	}

	public String getMeasureUnitName() {
		return measureUnitName;
	}

	public void setMeasureUnitName(String measureUnitName) {
		this.measureUnitName = measureUnitName;
	}

	public String getB2cComments() {
		return b2cComments;
	}

	public void setB2cComments(String b2cComments) {
		this.b2cComments = b2cComments;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getSpecialCode() {
		return specialCode;
	}

	public void setSpecialCode(String specialCode) {
		this.specialCode = specialCode;
	}

	

	public String getMaterialGroupCode() {
		return materialGroupCode;
	}

	public void setMaterialGroupCode(String materialGroupCode) {
		this.materialGroupCode = materialGroupCode;
	}

	public String getMaterialGroupName() {
		return materialGroupName;
	}

	public void setMaterialGroupName(String materialGroupName) {
		this.materialGroupName = materialGroupName;
	}

	public BigDecimal getTransfterPrice() {
		return transfterPrice;
	}

	public void setTransfterPrice(BigDecimal transfterPrice) {
		this.transfterPrice = transfterPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemRequirementPlan() {
		return itemRequirementPlan;
	}

	public void setItemRequirementPlan(String itemRequirementPlan) {
		this.itemRequirementPlan = itemRequirementPlan;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(BigDecimal standardPrice) {
		this.standardPrice = standardPrice;
	}
	
	
	
	
	
}
