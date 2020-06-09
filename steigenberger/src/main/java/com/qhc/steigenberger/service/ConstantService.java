package com.qhc.steigenberger.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConstantService {

	@Autowired
	FryeService fryeService;

	public Map<String, String> getIndustryCodes() {
		return fryeService.getInfo("constant/industryCode", Map.class);
	}
	public Map<String, String> getIndustrys() {
		return fryeService.getInfo("constant/industry", Map.class);
	}
	public Map<String, String> getOrderTypes() {
		return fryeService.getInfo("constant/orderType", Map.class);
	}
	public Map<String, String> getSaleTypes() {
		return fryeService.getInfo("constant/saleType", Map.class);
	}
	public Map<String, String> getOffice() {
		return fryeService.getInfo("constant/office", Map.class);
	}

}
