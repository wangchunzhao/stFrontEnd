package com.qhc.steigenberger.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Walker
 *
 */
public class Characteristic {

	/* Id */
	private Integer id = null;

	/* ItemDetailId */
	private Integer itemId = null;

	/* 选定的特征代码 */
	private String keyCode = null;

	/* 选定的特征代码 */
	private String keyName = null;

	/* 选定的特征值的代码 */
	private String valueCode = null;

	/* 选定的特征值的代码 */
	private String valueName = null;

	/* 可选 */
	private boolean optional = false;

	/* 可配置 */
	private boolean configurable = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public boolean isConfigurable() {
		return configurable;
	}

	public void setConfigurable(boolean configurable) {
		this.configurable = configurable;
	}

	@Override
	public String toString() {
		return "Characteristic [id=" + id + ", itemId=" + itemId + ", keyCode=" + keyCode + ", keyName=" + keyName
				+ ", valueCode=" + valueCode + ", valueName=" + valueName + ", optional=" + optional + ", configurable="
				+ configurable + "]";
	}
}
