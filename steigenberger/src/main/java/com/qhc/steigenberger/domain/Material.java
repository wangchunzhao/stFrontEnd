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
	
	private String code;
	private String description;
	private boolean isConfigurable;
	private double mvPrice;
	private double trPrice;
	private double mkPrice;
	private Date optTime;
	private String type;
	private String clazz;
	private String unit;
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
	public double getMvPrice() {
		return mvPrice;
	}
	public void setMvPrice(double mvPrice) {
		this.mvPrice = mvPrice;
	}
	public double getTrPrice() {
		return trPrice;
	}
	public void setTrPrice(double trPrice) {
		this.trPrice = trPrice;
	}
	public double getMkPrice() {
		return mkPrice;
	}
	public void setMkPrice(double mkPrice) {
		this.mkPrice = mkPrice;
	}
	public Date getOptTime() {
		return optTime;
	}
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
