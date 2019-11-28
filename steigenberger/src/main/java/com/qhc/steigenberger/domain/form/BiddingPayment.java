/**
 * 
 */
package com.qhc.steigenberger.domain.form;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author wang@dxc.com
 *
 */
public class BiddingPayment {
	private String termCode;//付款条件或biding plan 
	private String termName;//付款条件名称
	private double percentage;//付款比例
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date payDate;//付款日期
	private String reason;//付款事由
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
