/**
 * 
 */
package com.qhc.steigenberger.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wang@dxc.com
 *
 */
@Setter
@Getter
public class OrderOption {
	//
	private String sequenceNumber;
	private Map<String,String> provinces;//Map<prvince code,prvince name>
	private Map<String,Map<String,String>> citys;//Map<province code,Map<city code,city name>>
	private Set<BArea> districts;
	private Map<String,String> termialClass;
	/**sale type code**/
	private Map<String,String> saleTypes;
	//
	private Map<String,Map<String,String>> offices;//Map<saleType key(code),Map<office code,office name>>
	private Map<String,Map<String,String>> groups;//Map<office code,Map<group code,group name>>
	//
	private Map<String,Map<String,Double>> taxRate;//Map<saleType key(code),Map<taxRate name,rate>>
	//
	private Set<Currency> exchangeRate;
	//
	private Map<String,String> paymentType;//回款类型
	
	/**
	 * 
	 */
	public OrderOption() {
		Date  date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date1 = sdf.format(date);
		Random r = new Random();
		int num = r.nextInt(10)+1;
		this.sequenceNumber = "QHC"+date1+String.valueOf(num);
		
	}

}
