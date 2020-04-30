package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Function: Data transfer object. <br> 
 *
 * @author walker
 */
public class SpecialAttachment
		implements Serializable {
	private static final long serialVersionUID = 7776933116928074574L;
	
	/* Id */
	private Integer id = null;

	/* OrderInfoId */
	private Integer orderInfoId = null;

	/* SpecialId */
	private Integer specialId = null;

	/* FileName */
	private String fileName = null;

	/* FileUrl */
	private String fileUrl = null;


	public SpecialAttachment(){
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	 
	public Integer getOrderInfoId() {
		return this.orderInfoId;
	}

	public void setOrderInfoId(Integer orderInfoId) {
		this.orderInfoId = orderInfoId;
	}
	 
	public Integer getSpecialId() {
		return this.specialId;
	}

	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}
	 
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	 
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SpecialAttachment other = (SpecialAttachment) obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id));
	}
	
	public String toString() {
	    final String tab = "  ";
	    String str = "";
	    str = "SpecialAttachment ( "
	        + "id = " + this.id + tab
	        + "orderInfoId = " + this.orderInfoId + tab
	        + "specialId = " + this.specialId + tab
	        + "fileName = " + this.fileName + tab
	        + "fileUrl = " + this.fileUrl + tab
	        + " )";
	
	    return str;
	}

}