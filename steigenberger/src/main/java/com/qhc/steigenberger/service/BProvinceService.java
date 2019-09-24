package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.BProvince;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class BProvinceService {
	
	@Autowired
	FryeService<BProvince> fryeService;
	
	@Autowired
	FryeService<PageHelper> pageFryeService;
	
	private final static String URL_KORDERS = "bProvince";
	
	
	public BProvince add(BProvince bProvince) {
		return fryeService.postInfo(bProvince,URL_KORDERS, BProvince.class);
	}

}
