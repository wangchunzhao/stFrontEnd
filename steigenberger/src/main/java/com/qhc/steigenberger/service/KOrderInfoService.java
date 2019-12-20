package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class KOrderInfoService {
	
	@Autowired
	FryeService fryeService;
	
	@Autowired
	FryeService pageFryeService;
	
	private final static String URL_KORDERS = "kOrderInfo";
	private final static String URL_KORDERS_LIST = "kOrderInfo/paging";
	private final static String URL_KORDERS_LIST_SPECIAL = "kOrderInfo/pagingForSpecial";
	
	public PageHelper<KOrderInfo> getList(int pageNum, int pageSize,KOrderInfo kOrderInfo){
		
		String contractNo = kOrderInfo.getContractNo()==null?"":kOrderInfo.getContractNo();
		String contractUnit = kOrderInfo.getContractUnit()==null?"":kOrderInfo.getContractUnit();
		String startTime = kOrderInfo.getStartTime()==null?"":kOrderInfo.getStartTime();
		String endTime = kOrderInfo.getEndTime()==null?"":kOrderInfo.getEndTime();
		
		
		String url = URL_KORDERS_LIST+"?pageNo="+pageNum+"&pageSize="+pageSize+"&contractNo="+contractNo+"&contractUnit="+contractUnit+"&startTime="+startTime+"&endTime="+endTime+"&b2c="+kOrderInfo.getB2c()+"&createId="+kOrderInfo.getCreateId()+"&area="+kOrderInfo.getArea()+"&orderType="+kOrderInfo.getOrderType()+"&specialDiscount="+kOrderInfo.getSpecialDiscount()+"&status="+kOrderInfo.getStatus();
		return pageFryeService.getInfo(url, PageHelper.class);
	}
	
	public KOrderInfo getInfoById(int orderId){
		
		return fryeService.getInfo(URL_KORDERS+"/"+orderId, KOrderInfo.class);
	}
	
public PageHelper<KOrderInfo> getListForSpecial(int pageNum, int pageSize,KOrderInfo kOrderInfo){
		
		String contractNo = kOrderInfo.getContractNo()==null?"":kOrderInfo.getContractNo();
		String startTime = kOrderInfo.getStartTime()==null?"":kOrderInfo.getStartTime();
		String endTime = kOrderInfo.getEndTime()==null?"":kOrderInfo.getEndTime();
		
		String url = URL_KORDERS_LIST_SPECIAL+"?pageNo="+pageNum+"&pageSize="+pageSize+"&contractNo="+contractNo+"&startTime="+startTime+"&endTime="+endTime+"&status="+kOrderInfo.getStatus();
		return pageFryeService.getInfo(url, PageHelper.class);
	}

}
