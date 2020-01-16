package com.qhc.steigenberger.domain;


import java.io.Serializable;

/**
 * 
 * Function: Data transfer object. <br> 
 *
 * @author walker
 */
public class ItemAttachment
		implements Serializable {
	private static final long serialVersionUID = 8075770107761934031L;
	
	/* Id */
	private Integer id = null;

	/* OrderInfoId */
	private Integer orderInfoId = null;

	/* ItemId */
	private Integer itemId = null;

	/* FileName */
	private String fileName = null;

	/* FileUrl */
	private String fileUrl = null;


	public ItemAttachment(){
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
	 
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
		final ItemAttachment other = (ItemAttachment) obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id));
	}
	
	public String toString() {
	    final String tab = "  ";
	    String str = "";
	    str = "ItemAttachment ( "
	        + "id = " + this.id + tab
	        + "orderInfoId = " + this.orderInfoId + tab
	        + "itemId = " + this.itemId + tab
	        + "fileName = " + this.fileName + tab
	        + "fileUrl = " + this.fileUrl + tab
	        + " )";
	
	    return str;
	}

}