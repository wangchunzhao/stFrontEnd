package com.qhc.steigenberger.service;

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

	public Result findSpecialDelivery(int pageNum, int pageSize, SpecialDeliveryQuery sdv) {
		String sequenceNumber = sdv.getSequenceNumber() == null ? "" : sdv.getSequenceNumber();
		String startTime = sdv.getStartTime() == null ? "" : sdv.getStartTime();
		String endTime = sdv.getEndTime() == null ? "" : sdv.getEndTime();
		String orderTypeCode = sdv.getOrderTypeCode() == null ? "" : sdv.getOrderTypeCode();
		String ownerDomainId = sdv.getOwnerDomainId() == null ? "" : sdv.getOwnerDomainId();
		String officeCode = sdv.getOfficeCode() == null ? "" : sdv.getOfficeCode();

		String url = "specialdelivery?pageNo=" + pageNum + "&pageSize" + pageSize + "&sequenceNumber=" + sequenceNumber
				+ "&startTime=" + startTime + "&endTime=" + endTime + "&orderTypeCode=" + orderTypeCode
				+ "&ownerDomainId=" + ownerDomainId + "&officeCode=" + officeCode;
		return fryeService.getInfo(url, Result.class);
	}

	public Result submit(String username, SpecialDelivery specialDelivery) {
		return fryeService.postInfo(specialDelivery, "specialdelivery/submit/" + username, Result.class);
	}

}
