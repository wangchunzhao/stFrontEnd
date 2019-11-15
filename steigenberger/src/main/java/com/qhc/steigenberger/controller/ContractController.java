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
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.domain.Contract;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.service.OperationService;


@Controller
@RequestMapping("contract")
public class ContractController {
	
//	@Autowired
//	

	
	@GetMapping(path="/")
	@ResponseBody
	public JsonResult findAll(@RequestBody Map<String, Object> params){
		JsonResult r = null;
//		Map<String,Object> m = new HashMap<String,Object>();
//		m.put("list", operationService.getList());
		return r;
	}
	
	@RequestMapping("/{id}")
	@ResponseBody
	public JsonResult get(@PathVariable Integer contractId){
		JsonResult r = null;
//		Map<String,Object> m = new HashMap<String,Object>();
//		m.put("list", operationService.getList());
		return r;
	}
	
	@PostMapping("/")
	@ResponseBody
	public JsonResult save(@RequestBody Contract contract){
		JsonResult r = null;
//		Map<String,Object> m = new HashMap<String,Object>();
//		m.put("list", operationService.getList());
		return r;
	}
	
	@PutMapping("/{id}/send")
	@ResponseBody
	public JsonResult send(@PathVariable Integer contractId){
		JsonResult r = null;
//		Map<String,Object> m = new HashMap<String,Object>();
//		m.put("list", operationService.getList());
		return r;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public JsonResult preview(@PathVariable Integer contractId){
		JsonResult r = null;
//		Map<String,Object> m = new HashMap<String,Object>();
//		m.put("list", operationService.getList());
		return r;
	}
	
}
