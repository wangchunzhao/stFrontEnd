package com.qhc.steigenberger.domain;

import java.io.Serializable;
import java.util.List;

public class Operations implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String description;

	private List<String> childs;

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

	public List<String> getChilds() {
		return childs;
	}

	public void setChilds(List<String> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		return "Operations [id=" + id + ", name=" + name + ", description=" + description + ", childs=" + childs
				+ ", selected=" + selected + "]";
	}

}
