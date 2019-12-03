/**
 * 
 */
package com.qhc.steigenberger.domain.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author wang@dxc.com
 *
 */
public abstract class AbsItem {
	private Integer rowNumber;//行号
	private String materialCode;//物料号,专用号
	private String materialName;//物料名称,规格型号
	private boolean configurable;//是否为可配置物料
	private boolean purchased;//类型  1:采购  0:生产
	private double quantity;//数量
	private String unitName;//计量单位名称
	private String unitCode;//计量单位code
	private double acturalPrice;//产品实卖价
	private double transcationPrice;//转移价
	private double acturalPricaOfOptional;//可选项实卖价
	private double transcationPriceOfOptional;//可选项转移价
	private double B2CPriceEstimated;//B2C评估价
	private double B2CCostOfEstimated;//B2C评估成本
	private double retailPrice;//市场零售价
	private double standardPrice;//标准价格
	private double discount;//折扣
	private int period;//生产、采购周期
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date deliveryDate;//最早交货时间
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date shippDate; //要求发货时间  自己填
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date produceDate;//生产开始时间
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date onStoreDate;//入库时间	
	private String groupCode;//物料类型代码
	private String groupName;//物料类型名称
	private String itemCategory;//行项目类别
	private String itemRequirementPlan;//需求计划
	private String b2cComments;//B2C备注
	private String specialComments;//特殊备注
	private String configComments;//配置表备注(配置表页面)
	private String mosaicImage;//拼接图备注(配置表页面)
	private String attachedImage;//拼接图附件(配置表页面)
	private List<BaseChracteristic> configs;//配置表数据(配置表页面)
	private int isVirtual;//工程虚拟物料
	private double volumeCube;//工程虚拟物料
	private double feight;//运费
	private String requestBrand;
	private String requestPackage;
	private String requestNameplate;
	private String requestCircult;
	private String comments;
	private String colorComments;
	
	
	
	public String getRequestBrand() {
		return requestBrand;
	}
	public void setRequestBrand(String requestBrand) {
		this.requestBrand = requestBrand;
	}
	public String getRequestPackage() {
		return requestPackage;
	}
	public void setRequestPackage(String requestPackage) {
		this.requestPackage = requestPackage;
	}
	public String getRequestNameplate() {
		return requestNameplate;
	}
	public void setRequestNameplate(String requestNameplate) {
		this.requestNameplate = requestNameplate;
	}
	public String getRequestCircult() {
		return requestCircult;
	}
	public void setRequestCircult(String requestCircult) {
		this.requestCircult = requestCircult;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getColorComments() {
		return colorComments;
	}
	public void setColorComments(String colorComments) {
		this.colorComments = colorComments;
	}
	public double getFeight() {
		return feight;
	}
	public void setFeight(double feight) {
		this.feight = feight;
	}
	public double getVolumeCube() {
		return volumeCube;
	}
	public void setVolumeCube(double volumeCube) {
		this.volumeCube = volumeCube;
	}
	public int getIsVirtual() {
		return isVirtual;
	}
	public void setIsVirtual(int isVirtual) {
		this.isVirtual = isVirtual;
	}
	public Integer getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
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
	
	public boolean isConfigurable() {
		return configurable;
	}
	public void setIsConfigurable(boolean configurable) {
		this.configurable = configurable;
	}
	public boolean isPurchased() {
		return purchased;
	}
	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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
	public double getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrice(double standardPrice) {
		this.standardPrice = standardPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	public String getB2cComments() {
		return b2cComments;
	}
	public void setB2cComments(String b2cComments) {
		this.b2cComments = b2cComments;
	}
	public String getSpecialComments() {
		return specialComments;
	}
	public void setSpecialComments(String specialComments) {
		this.specialComments = specialComments;
	}
	public String getConfigComments() {
		return configComments;
	}
	public void setConfigComments(String configComments) {
		this.configComments = configComments;
	}
	public String getMosaicImage() {
		return mosaicImage;
	}
	public void setMosaicImage(String mosaicImage) {
		this.mosaicImage = mosaicImage;
	}
	public String getAttachedImage() {
		return attachedImage;
	}
	public void setAttachedImage(String attachedImage) {
		this.attachedImage = attachedImage;
	}
	public List<BaseChracteristic> getConfigs() {
		return configs;
	}
	public void setConfigs(List<BaseChracteristic> configs) {
		this.configs = configs;
	}
	
}
