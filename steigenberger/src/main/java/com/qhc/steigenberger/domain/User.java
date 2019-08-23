/**
 * 
 */
package com.qhc.steigenberger.domain;

import org.springframework.stereotype.Component;

@Component
public class User {
	
    private int id;
    private String user_mail;
    private boolean active;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_mail() {
		return user_mail;
	}

	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


}
