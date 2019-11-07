/**
 * 
 */
package com.qhc.steigenberger.domain;

/**
 * @author wang@dxc.com
 *
 */
public class Configuration {
	private String code;
	private String name;
	private boolean isDefault;
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
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
	

}
