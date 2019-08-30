/**
 * 
 */
package com.qhc.steigenberger.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Role implements Serializable{
	
    public int id;
    
    public String name;
    
    public int isActive;
	
	
	
	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




}
