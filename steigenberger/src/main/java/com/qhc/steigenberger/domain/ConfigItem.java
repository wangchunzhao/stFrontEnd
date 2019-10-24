package com.qhc.steigenberger.domain;

public class ConfigItem {
	
	private String option;//可选、必选
	private String configCode;//配置项
	private String configValueCode;//配置值
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
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
