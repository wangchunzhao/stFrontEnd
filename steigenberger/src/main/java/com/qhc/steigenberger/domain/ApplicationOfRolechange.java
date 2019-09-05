package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;

public class ApplicationOfRolechange implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private String creator;
	
    private Date createTime;

    private Date approvalTime;

	private String approverRequired;
	
	private String approverFact;
	
    private int busersId;

    private int isactive;

    private String attachedCode;//区域编码

    private int bRolesId;
	

    
//	 private User user = new User();
//	 
//	 private Role role = new Role();
//	 
//	 private SapSalesOffice sapSales = new SapSalesOffice();
    

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getApproverRequired() {
        return approverRequired;
    }

    public void setApproverRequired(String approverRequired) {
        this.approverRequired = approverRequired == null ? null : approverRequired.trim();
    }

    public String getApproverFact() {
        return approverFact;
    }

    public void setApproverFact(String approverFact) {
        this.approverFact = approverFact == null ? null : approverFact.trim();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public int getbusersId() {
        return busersId;
    }

    public void setbusersId(int busersId) {
        this.busersId = busersId;
    }

    public String getAttachedCode() {
        return attachedCode;
    }

    public void setAttachedCode(String attachedCode) {
        this.attachedCode = attachedCode == null ? null : attachedCode.trim();
    }

    public int getbRolesId() {
        return bRolesId;
    }

    public void setbRolesId(int bRolesId) {
        this.bRolesId = bRolesId;
    }

	public int getIsactive() {
		return isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

    
    
}