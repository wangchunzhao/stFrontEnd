/**
 * 
 */
package com.qhc.steigenberger.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang@dxc.com
 *
 */
@Setter
@Getter
public class Currency {
	private String code;
	private String name;
	private double rate;
}
