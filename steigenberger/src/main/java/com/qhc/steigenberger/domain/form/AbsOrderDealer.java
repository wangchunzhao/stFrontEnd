/**
 * 
 */
package com.qhc.steigenberger.domain.form;

/**
 * @author wang@dxc.com
 *
 */
public abstract class AbsOrderDealer extends AbsOrder {
	
	private String recordCode;// 项目报备编号 report number
	
	private String customerNatureCode;//终端客户性质 customer nature
	/**
	 * 
	 */
	private double discount;//合并折扣
	
	private double bodyDiscount;//柜体折扣 standard discount


	private double mainDiscount;//机组折扣 standard discount
	
	private int isLongterm;//是否为长期折扣
	
	private double approvedDicount;//批准的折扣、标准折扣
	
	private String paymentType;//结算方式

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public String getCustomerNatureCode() {
		return customerNatureCode;
	}

	public void setCustomerNatureCode(String customerNatureCode) {
		this.customerNatureCode = customerNatureCode;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getBodyDiscount() {
		return bodyDiscount;
	}

	public void setBodyDiscount(double bodyDiscount) {
		this.bodyDiscount = bodyDiscount;
	}

	public double getMainDiscount() {
		return mainDiscount;
	}

	public void setMainDiscount(double mainDiscount) {
		this.mainDiscount = mainDiscount;
	}

	public int getIsLongterm() {
		return isLongterm;
	}

	public void setIsLongterm(int isLongterm) {
		this.isLongterm = isLongterm;
	}

	public double getApprovedDicount() {
		return approvedDicount;
	}

	public void setApprovedDicount(double approvedDicount) {
		this.approvedDicount = approvedDicount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
}
