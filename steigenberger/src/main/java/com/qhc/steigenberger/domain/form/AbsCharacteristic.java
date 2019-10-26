/**
 * 
 */
package com.qhc.steigenberger.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wwang67
 *
 */
public abstract class AbsCharacteristic {
	
	private Boolean optional;//可选、必选
	@JsonProperty(value = "code")
	private String configCode;//配置项
	private String configValueCode;//配置值
	
	public AbsCharacteristic() {
		
	}
	
	public Boolean getOptional() {
		return optional;
	}

	public void setOptional(Boolean optional) {
		this.optional = optional;
	}

	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public String getConfigValueCode() {
		return configValueCode;
	}
	public void setConfigValueCode(String configValueCode) {
		this.configValueCode = configValueCode;
	}
	
	
}
