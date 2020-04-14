package com.qhc.steigenberger.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.SpecialDelivery;
import com.qhc.steigenberger.domain.SpecialDeliveryQuery;

@Service
public class SpecialDeliveryService {

	@Autowired
	FryeService fryeService;

	public Result saveSpecialDelivery(String username, SpecialDelivery specialDelivery) {
		return fryeService.postInfo(specialDelivery, "specialdelivery/" + username, Result.class);
	}

	public Result findSpecialDeliveryById(Integer applyId) {
		return fryeService.getInfo("specialdelivery/" + applyId, Result.class);
	}

	public Result findSpecialDelivery(int pageNum, int pageSize, Map sdv) {
		String sequenceNumber = trimToEmpty(sdv.get("sequenceNumber"));
		String startTime = trimToEmpty(sdv.get("startTime"));
		String endTime = trimToEmpty(sdv.get("endTime"));
		String orderTypeCode = trimToEmpty(sdv.get("orderTypeCode"));
		String ownerDomainId = trimToEmpty(sdv.get("ownerDomainId"));
		String officeCode = trimToEmpty(sdv.get("officeCode"));
        String list = trimToEmpty(sdv.get("list"));
        String orderInfoId = trimToEmpty(sdv.get("orderInfoId"));

		String url = "specialdelivery?pageNo=" + pageNum + "&pageSize" + pageSize + "&list=" + list + "&orderInfoId=" + orderInfoId + "&sequenceNumber=" + sequenceNumber
				+ "&orderStartCreateTime=" + startTime + "&orderEndCreateTime=" + endTime + "&orderTypeCode=" + orderTypeCode
				+ "&ownerDomainId=" + ownerDomainId + "&officeCode=" + officeCode;
		return fryeService.getInfo(url, Result.class);
	}
	
  private String trimToEmpty(Object str) {
    if (str == null) {
      return "";
    }
    return str.toString().trim();
  }

	public Result submit(String username, SpecialDelivery specialDelivery) {
		return fryeService.postInfo(specialDelivery, "specialdelivery/submit/" + username, Result.class);
	}

}
