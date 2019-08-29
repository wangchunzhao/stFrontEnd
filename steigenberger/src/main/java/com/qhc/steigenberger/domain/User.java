/**
 * 
 */
package com.qhc.steigenberger.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;



@Component
public class User implements Serializable{
	
	
    public int id;
	
    public String userMail;
	
    public String userIdentity;
	
    public int isActive;
	
	
	
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





}

