/**
 * 
 */
package com.qhc.steigenberger.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.Contract;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Result;

/**
 * @author Walker
 *
 */
@Service
public class ContractService {
	
	@Autowired
	private FryeService fryeService;
	
	public Result find(Map<String, Object> params) {
		return (Result)fryeService.getInfo("/contract", params, Result.class);
	}
	
	public Result find(Integer contractId) {
		return (Result)fryeService.getInfo("/contract/" + contractId, Result.class);
	}
	
	public Result save(Contract contract) {
		return (Result)fryeService.postForm("/contract", contract, Result.class);
	}
}
