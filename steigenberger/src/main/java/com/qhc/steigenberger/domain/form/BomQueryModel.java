package com.qhc.steigenberger.domain.form;

import java.util.List;

public class BomQueryModel {
	
	private String bomCode;
	private List<String> configCode;
	public String getBomCode() {
		return bomCode;
	}
	public void setBomCode(String bomCode) {
		this.bomCode = bomCode;
	}
	public List<String> getConfigCode() {
		return configCode;
	}
	public void setConfigCode(List<String> configCode) {
		this.configCode = configCode;
	}
	public List<String> getConfigValueCode() {
		return configValueCode;
	}
	public void setConfigValueCode(List<String> configValueCode) {
		this.configValueCode = configValueCode;
	}
	private List<String> configValueCode;

}
