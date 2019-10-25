/**
 * 
 */
package com.qhc.steigenberger.domain.form;

import java.util.List;

/**
 * @author wang@dxc.com
 *
 */
public abstract class AbsForm {
	
	private List<AbsItem> items;

	public List<AbsItem> getItems() {
		return items;
	}

	public void setItems(List<AbsItem> items) {
		this.items = items;
	}
	
		
	
}
