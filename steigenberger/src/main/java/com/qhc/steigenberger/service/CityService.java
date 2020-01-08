package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.City;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class CityService {
	
	@Autowired
	FryeService fryeService;
	
	@Autowired
	FryeService pageFryeService;
	
	private final static String URL_KORDERS = "bCity";
	
	
	public City add(City bCity) {
		return fryeService.postInfo(bCity,URL_KORDERS, City.class);
	}

}
