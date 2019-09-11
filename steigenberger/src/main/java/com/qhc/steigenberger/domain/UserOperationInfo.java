package com.qhc.steigenberger.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class UserOperationInfo implements Serializable {
	
    private Integer userId;
	
    private String userMail;
	
    private String userIdentity;
	
	private Integer userIsActive;
	
    private Integer roleId;
	
	private String roleName;
	
	private String attachedCode;
	
	private String attachedName;
	
	private String operationId;
	
	private String operationName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public Integer getUserIsActive() {
		return userIsActive;
	}

	public void setUserIsActive(Integer userIsActive) {
		this.userIsActive = userIsActive;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAttachedCode() {
		return attachedCode;
	}

	public void setAttachedCode(String attachedCode) {
		this.attachedCode = attachedCode;
	}

	public String getAttachedName() {
		return attachedName;
	}

	public void setAttachedName(String attachedName) {
		this.attachedName = attachedName;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	

}
