package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class KOrderInfoService {
	
	@Autowired
	FryeService<KOrderInfo> fryeService;
	
	@Autowired
	FryeService<PageHelper> pageFryeService;
	
	private final static String URL_KORDERS = "kOrderInfo";
	private final static String URL_KORDERS_LIST = "kOrderInfo/paging";
	
	public PageHelper<KOrderInfo> getList(int pageNum, int pageSize,KOrderInfo kOrderInfo){
		
		String contractNo = kOrderInfo.getContractNo()==null?"":kOrderInfo.getContractNo();
		String contractUnit = kOrderInfo.getContractUnit()==null?"":kOrderInfo.getContractUnit();
		
		
		String url = URL_KORDERS_LIST+"?pageNo="+pageNum+"&pageSize="+pageSize+"&contractNo="+contractNo+"&contractUnit="+contractUnit+"&b2c="+kOrderInfo.getB2c()+"&createId="+kOrderInfo.getCreateId()+"&area="+kOrderInfo.getArea();
		return pageFryeService.getInfo(url, PageHelper.class);
	}

}
