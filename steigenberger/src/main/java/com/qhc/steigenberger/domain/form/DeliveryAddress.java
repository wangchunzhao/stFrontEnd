package com.qhc.steigenberger.domain.form;

public class DeliveryAddress {

	private Integer id = null;
	/* 订单详情id */
	private Integer orderInfoId = null;
	/* 序号，每个订单详情从1开始，每增加一条取本订单的最大值加一 */
	private Integer seq = null;
	private String provinceCode;// 省代码
	private String provinceName;// 省名称
	private String cityCode;// 市代码
	private String cityName;// 市名称
	private String distinctCode;// 区代码
	private String distinctName;// 区名称
	private String address; // 收货地址

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistinctCode() {
		return distinctCode;
	}

	public void setDistinctCode(String distinctCode) {
		this.distinctCode = distinctCode;
	}

	public String getDistinctName() {
		return distinctName;
	}

	public void setDistinctName(String distinctName) {
		this.distinctName = distinctName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
