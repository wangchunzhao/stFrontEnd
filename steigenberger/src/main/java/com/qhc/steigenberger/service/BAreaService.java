package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.BArea;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class BAreaService {
	
	@Autowired
	FryeService<BArea> fryeService;
	
	@Autowired
	FryeService<PageHelper> pageFryeService;
	
	private final static String URL_KORDERS = "bArea";
	
	
	public BArea add(BArea bArea) {
		return fryeService.postInfo(bArea,URL_KORDERS, BArea.class);
	}


}
