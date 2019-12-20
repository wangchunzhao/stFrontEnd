package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.BCity;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class BCityService {
	
	@Autowired
	FryeService fryeService;
	
	@Autowired
	FryeService pageFryeService;
	
	private final static String URL_KORDERS = "bCity";
	
	
	public BCity add(BCity bCity) {
		return fryeService.postInfo(bCity,URL_KORDERS, BCity.class);
	}

}
