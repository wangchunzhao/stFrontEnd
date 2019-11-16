package com.qhc.steigenberger.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.domain.Contract;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.service.ContractService;
import com.qhc.steigenberger.service.OperationService;


@Controller
@RequestMapping("contract")
public class ContractController {
	
	@Autowired
	ContractService contractService;

	
	@GetMapping(path="/")
	@ResponseBody
	public Result findAll(@RequestParam Map<String, Object> params){
		Result r = null;
		r = contractService.find(params);
		return r;
	}
	
	@RequestMapping("/{id}")
	@ResponseBody
	public Result get(@PathVariable Integer contractId){
		Result r = null;
		r = contractService.find(contractId);
		return r;
	}
	
	@PostMapping("/")
	@ResponseBody
	public Result save(@RequestBody Contract contract){
		Result r = null;
		r = contractService.save(contract);
		return r;
	}
	
	@PutMapping("/{id}/send")
	@ResponseBody
	public Result send(@PathVariable Integer contractId){
		Result r = null;
//		contractService.
		return r;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Result preview(@PathVariable Integer contractId){
		Result r = null;
//		Map<String,Object> m = new HashMap<String,Object>();
//		m.put("list", operationService.getList());
		return r;
	}
	
}
