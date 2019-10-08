package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.SpecialDelivery;

@Service
public class SpecialDeliveryService {
	
	@Autowired
	FryeService<SpecialDelivery> fryeService;
	
	private final static String URL_SD = "specialDelivery";
	private final static String URL_SD_ORDER = "specialApply";
	
	

	public SpecialDelivery saveSpecialDelivery(SpecialDelivery specialDelivery) {
		return fryeService.postInfo(specialDelivery, URL_SD, SpecialDelivery.class);
	}
	
	public List<SpecialDelivery> findListInfoByOrderId(Integer orderId) {
		return fryeService.getListInfo(URL_SD+"/"+orderId,SpecialDelivery.class);
	}

	public SpecialDelivery findInfoById(String applyId) {
		// TODO Auto-generated method stub
		return fryeService.getInfo(URL_SD_ORDER+"/"+applyId,SpecialDelivery.class);
	}


}
