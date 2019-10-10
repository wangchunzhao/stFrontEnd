package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public int id;
	
    public String userMail;
    
    public String userIdentity;
    
    public int isActive=1;
    
    public String userName;
    
    public String tel;
    
    public List<ApplicationOfRolechange> apps;
	
    public SalesOffice region;//区域
   	
   	public List<Role> roles;
   	
   	public List<Operations> operations;//区域下对应的权限
	


   	
	public SalesOffice getRegion() {
		return region;
	}

	public void setRegion(SalesOffice region) {
		this.region = region;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public List<Operations> getOperations() {
		return operations;
	}

	public void setOperations(List<Operations> operations) {
		this.operations = operations;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<ApplicationOfRolechange> getApps() {
		return apps;
	}

	public void setApps(List<ApplicationOfRolechange> apps) {
		this.apps = apps;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	


}

