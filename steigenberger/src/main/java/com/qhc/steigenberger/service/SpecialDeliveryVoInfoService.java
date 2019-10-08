package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.SpecialDeliveryVoInfo;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class SpecialDeliveryVoInfoService {
	
	@Autowired
	FryeService<SpecialDeliveryVoInfo> fryeService;
	
	@Autowired
	FryeService<PageHelper> pageFryeService;
	
	private final static String SPECIAL_DELIVERY_VO_INFO = "specialDeliveryVoInfo";
	
	
	public PageHelper<SpecialDeliveryVoInfo> getListForSpecial(int pageNum, int pageSize,SpecialDeliveryVoInfo sdv){
		
		String sequenceNumber = sdv.getSequenceNumber()==null?"":sdv.getSequenceNumber();
		String startTime = sdv.getStartTime()==null?"":sdv.getStartTime();
		String endTime = sdv.getEndTime()==null?"":sdv.getEndTime();
		String orderTypeCode = sdv.getOrderTypeCode()==null?"":sdv.getOrderTypeCode();
		String ownerDomainId = sdv.getOwnerDomainId()==null?"":sdv.getOwnerDomainId();
		String officeCode = sdv.getOfficeCode()==null?"":sdv.getOfficeCode();
		
		String url = SPECIAL_DELIVERY_VO_INFO+"/"+pageNum+"/"+pageSize+"?sequenceNumber="
		+sequenceNumber+"&startTime="+startTime+"&endTime="
				+endTime+"&orderTypeCode="+orderTypeCode+"&ownerDomainId="+ownerDomainId+"&officeCode="+officeCode;
		return pageFryeService.getInfo(url, PageHelper.class);
	}

}
