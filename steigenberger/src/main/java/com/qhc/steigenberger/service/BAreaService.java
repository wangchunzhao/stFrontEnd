package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.BArea;
import com.qhc.steigenberger.domain.Freight;
import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class BAreaService {
	
	@Autowired
	FryeService<List<List<String>>> fryeService;
	
	@Autowired
	FryeService<PageHelper> pageFryeService;
	
	private final static String URL_KORDERS = "location/freight";
	
	
	
//	public BArea add(BArea bArea) {
//		return fryeService.postInfo(bArea,URL_KORDERS, BArea.class);
//	}
	
	public void add(List<List<String>> freight) {
		fryeService.putJason(URL_KORDERS, freight);
	}
	
	public PageHelper getList(int pageNo, int pageSize,Freight freight){

		String name = freight.getName()==null?"":freight.getName();
		String url = URL_KORDERS+"/"+pageNo+"/"+pageSize;
		return pageFryeService.getInfo(url, PageHelper.class);
	}


}
