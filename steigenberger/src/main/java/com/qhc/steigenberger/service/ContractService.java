/**
 * 
 */
package com.qhc.steigenberger.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.JsonResult;

/**
 * @author Walker
 *
 */
@Service
public class ContractService {
	
	@Autowired
	private FryeService fryeService;
	
	public JsonResult find(Map<String, Object> params) {
		return (JsonResult)fryeService.getInfo("/contract", JsonResult.class);
	}
}
