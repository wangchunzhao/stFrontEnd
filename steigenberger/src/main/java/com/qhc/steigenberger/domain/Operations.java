package com.qhc.steigenberger.domain;

import java.io.Serializable;

public class Operations implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
    private String name;
    
    private String description;
    
    private boolean selected;
    
    
	
	
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}




}
