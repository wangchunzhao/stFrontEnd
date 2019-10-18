/**
 * 
 */
package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wang@dxc.com
 *
 */
public class Material implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4087877900159424776L;
	
	public final static String MATERIAL_CODE = "59870645008146f9938f7e8718031778";
	
	private String code;//物料号,专用号
	private String description;//物料名称，规格型号
	private boolean isConfigurable;//是否为可配置物料
	private boolean isPurchased;//类型
	private String unitName;//计量单位名称
	private double acturalPrice;//产品实卖价
	private double transcationPrice;//转移价
	private double acturalPricaOfOptional;//可选项实卖价
	private double transcationPriceOfOptional;//可选项转移价
	private double B2CPriceEstimated;//B2C评估价
	private double B2CCostOfEstimated;//B2C评估成本
	private double retailPrice;//市场零售价
	private int discount;//折扣
	private int period;//生产、采购周期
	private Date deliveryDate;//最早交货时间
	private Date shippDate; //要求发货时间
	private Date produceDate;//生产开始时间
	private Date onStoreDate;//入库时间
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isConfigurable() {
		return isConfigurable;
	}
	public void setConfigurable(boolean isConfigurable) {
		this.isConfigurable = isConfigurable;
	}
	public boolean isPurchased() {
		return isPurchased;
	}
	public void setPurchased(boolean isPurchased) {
		this.isPurchased = isPurchased;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public double getActuralPrice() {
		return acturalPrice;
	}
	public void setActuralPrice(double acturalPrice) {
		this.acturalPrice = acturalPrice;
	}
	public double getTranscationPrice() {
		return transcationPrice;
	}
	public void setTranscationPrice(double transcationPrice) {
		this.transcationPrice = transcationPrice;
	}
	public double getActuralPricaOfOptional() {
		return acturalPricaOfOptional;
	}
	public void setActuralPricaOfOptional(double acturalPricaOfOptional) {
		this.acturalPricaOfOptional = acturalPricaOfOptional;
	}
	public double getTranscationPriceOfOptional() {
		return transcationPriceOfOptional;
	}
	public void setTranscationPriceOfOptional(double transcationPriceOfOptional) {
		this.transcationPriceOfOptional = transcationPriceOfOptional;
	}
	public double getB2CPriceEstimated() {
		return B2CPriceEstimated;
	}
	public void setB2CPriceEstimated(double b2cPriceEstimated) {
		B2CPriceEstimated = b2cPriceEstimated;
	}
	public double getB2CCostOfEstimated() {
		return B2CCostOfEstimated;
	}
	public void setB2CCostOfEstimated(double b2cCostOfEstimated) {
		B2CCostOfEstimated = b2cCostOfEstimated;
	}
	public double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getShippDate() {
		return shippDate;
	}
	public void setShippDate(Date shippDate) {
		this.shippDate = shippDate;
	}
	public Date getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}
	public Date getOnStoreDate() {
		return onStoreDate;
	}
	public void setOnStoreDate(Date onStoreDate) {
		this.onStoreDate = onStoreDate;
	}
	
	
	
	
}
