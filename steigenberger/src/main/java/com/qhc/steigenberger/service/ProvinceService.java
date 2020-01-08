package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.Province;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class ProvinceService {
	
	@Autowired
	FryeService fryeService;
	
	@Autowired
	FryeService pageFryeService;
	
	private final static String URL_KORDERS = "bProvince";
	
	
	public Province add(Province bProvince) {
		return fryeService.postInfo(bProvince,URL_KORDERS, Province.class);
	}

}
