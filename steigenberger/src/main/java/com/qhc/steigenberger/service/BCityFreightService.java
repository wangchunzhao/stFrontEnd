package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.BCityFreight;
import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class BCityFreightService {

	@Autowired
	FryeService<BCityFreight> fryeService;
	
	@Autowired
	FryeService<PageHelper> pageFryeService;
	
	private final static String URL_KORDERS = "bCityFreight";
	private final static String URL_KORDERS_LIST = "bCityFreight/paging";
	
	
	public BCityFreight add(BCityFreight bCityFreight) {
		return fryeService.postInfo(bCityFreight,URL_KORDERS, BCityFreight.class);
	}
	
	public PageHelper<BCityFreight> getList(int pageNum, int pageSize,BCityFreight bCityFreight){
		
		String countyName = bCityFreight.getCountyName()==null?"":bCityFreight.getCountyName();
		System.out.println(countyName+"==============");
		String url = URL_KORDERS_LIST+"?pageNo="+pageNum+"&pageSize="+pageSize+"&countyName="+countyName;
		return pageFryeService.getInfo(url, PageHelper.class);
	}
	
}
