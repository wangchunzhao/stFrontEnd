/**
 * 
 */
package com.qhc.steigenberger.domain.form;

import java.util.List;

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
	
//	private double bodyDiscount;//柜体折扣 standard discount
//
//
//	private double mainDiscount;//机组折扣 standard discount
	
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

	// 合并折扣= SUM{每行市场零售金额*（折扣率/100）}/SUM(市场零售金额)
	public double getDiscount() {
		List<BaseItem> items = super.getItems();
		discount = 1;
		if (items != null && items.size() > 0) {
			double amount = this.getItemsAmount();
			double discountAmount = 0;
			for (AbsItem item : items) {
				double price = item.getRetailPrice();
				double quantity = item.getQuantity();
				double d = item.getDiscount();
				discountAmount += price * quantity * d / 100;
			}
			discount = discountAmount / amount;
		}
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
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
