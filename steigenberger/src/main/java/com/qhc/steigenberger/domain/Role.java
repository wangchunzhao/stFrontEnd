/**
 * 
 */
package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;


@Component
public class Role implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public int id;
    
    public String name;
    
    public int isActive=1;
	
    public Set<ApplicationOfRolechange> apps;
	
	public List<Operations> operations;
	
	public boolean selected;
	
	
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

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

	public List<Operations> getOperations() {
		return operations;
	}

	public void setOperations(List<Operations> operations) {
		this.operations = operations;
	}


}
