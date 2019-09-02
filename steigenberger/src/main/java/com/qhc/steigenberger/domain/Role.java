/**
 * 
 */
package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.Set;
import org.springframework.stereotype.Component;


@Component
public class Role implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int id;
    
    public String name;
    
    public int isActive;
	
    public Set<ApplicationOfRolechange> apps;
	
	private Set<Operations> operations;
	
	
	
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

	public Set<ApplicationOfRolechange> getApps() {
		return apps;
	}

	public void setApps(Set<ApplicationOfRolechange> apps) {
		this.apps = apps;
	}

	public Set<Operations> getOperations() {
		return operations;
	}

	public void setOperations(Set<Operations> operations) {
		this.operations = operations;
	}




}
