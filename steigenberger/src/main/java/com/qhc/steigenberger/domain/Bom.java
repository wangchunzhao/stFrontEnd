/**
 * 
 */
package com.qhc.steigenberger.domain;

import java.io.Serializable;


/**
 * @author wang@dxc.com
 *
 */
public class Bom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5568458921204355045L;
	private static final int FACTOR = 31;
	//
	private String code;
	private String parentCode;
	private boolean isConfigurable;
	private double price;
	private double quantity;
	private boolean isMarked;
	//
	private boolean isMarkedByDefault;
	private boolean isMarkedByConfig;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public boolean isConfigurable() {
		return isConfigurable;
	}
	public void setConfigurable(boolean isConfigurable) {
		this.isConfigurable = isConfigurable;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public boolean isMarked() {
		return isMarked;
	}
	public void setMarked(boolean isMarked) {
		this.isMarked = isMarked;
	}
	@Override
	public int hashCode() {
		
		return this.getCode().hashCode() * FACTOR+this.getParentCode().hashCode();
	}
	
	@Override
	public boolean equals(Object anObject) {
		if(anObject.getClass().equals(this.getClass()) ) {
			Bom obj = (Bom)anObject;
			if(obj.getCode().equals(this.getCode())&&obj.getParentCode().equals(this.getParentCode())) {
				return true;
			}
		}
		return false;
	}
	public boolean isMarkedByDefault() {
		return isMarkedByDefault;
	}
	public void setMarkedByDefault(boolean isMarkedByDefault) {
		this.isMarkedByDefault = isMarkedByDefault;
	}
	public boolean isMarkedByConfig() {
		return isMarkedByConfig;
	}
	public void setMarkedByConfig(boolean isMarkedByConfig) {
		this.isMarkedByConfig = isMarkedByConfig;
	}

}
