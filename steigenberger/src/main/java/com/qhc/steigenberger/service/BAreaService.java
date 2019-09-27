package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.BArea;
import com.qhc.steigenberger.domain.Freight;
import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class BAreaService {
	
	@Autowired
	FryeService<BArea> fryeService;
	
	@Autowired
	FryeService<PageHelper> pageFryeService;
	
	private final static String URL_KORDERS = "bArea";
	private final static String URL_KORDERS_LIST = "bArea/paging";
	
	
	public BArea add(BArea bArea) {
		return fryeService.postInfo(bArea,URL_KORDERS, BArea.class);
	}
	
	public PageHelper getList(int pageNum, int pageSize,Freight freight){

		String name = freight.getName()==null?"":freight.getName();
		String url = URL_KORDERS_LIST+"?pageNo="+pageNum+"&pageSize="+pageSize+"&name="+name;
		return pageFryeService.getInfo(url, PageHelper.class);
	}


}
