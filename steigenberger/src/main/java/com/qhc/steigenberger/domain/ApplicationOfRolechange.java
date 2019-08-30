package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Date;

public class ApplicationOfRolechange implements Serializable{
	
    private Integer id;

	private String creator;
	
    private Date createTime;

    private Date approvalTime;

	private String approverRequired;
	
	private String approverFact;
	
    private Integer bUsersId;

    private int isactive;

    private String attachedCode;//区域编码

    private Integer bRolesId;
	

    
	 private User user = new User();
	 
	 private Role role = new Role();
	 
	 private SapSalesOffice sapSales = new SapSalesOffice();
    

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getbUsersId() {
        return bUsersId;
    }

    public void setbUsersId(Integer bUsersId) {
        this.bUsersId = bUsersId;
    }

    public String getAttachedCode() {
        return attachedCode;
    }

    public void setAttachedCode(String attachedCode) {
        this.attachedCode = attachedCode == null ? null : attachedCode.trim();
    }

    public Integer getbRolesId() {
        return bRolesId;
    }

    public void setbRolesId(Integer bRolesId) {
        this.bRolesId = bRolesId;
    }

	public int getIsactive() {
		return isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public SapSalesOffice getSapSales() {
		return sapSales;
	}

	public void setSapSales(SapSalesOffice sapSales) {
		this.sapSales = sapSales;
	}
    
    
}