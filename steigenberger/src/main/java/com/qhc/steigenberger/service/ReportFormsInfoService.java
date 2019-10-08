package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.ReportFormsInfo;
import com.qhc.steigenberger.util.PageHelper;

@Service
public class ReportFormsInfoService {

	@Autowired
	FryeService<PageHelper> fryeService;
	
	private final static String REPORT_FORMS_INFO = "reportFormsInfo";

	

	public PageHelper<ReportFormsInfo> findByConditions(int pageNum, int pageSize,ReportFormsInfo reportFormsInfo) {
			
		
			String sequenceNumber = reportFormsInfo.getSequenceNumber()==null?"":reportFormsInfo.getSequenceNumber();
			String contractorCode = reportFormsInfo.getContractorCode()==null?"":reportFormsInfo.getContractorCode();
			String contractUnit = reportFormsInfo.getContractUnit()==null?"":reportFormsInfo.getContractUnit();
			String startTime = reportFormsInfo.getStartTime()==null?"":reportFormsInfo.getStartTime();
			String endTime = reportFormsInfo.getEndTime()==null?"":reportFormsInfo.getEndTime();
			
			String customerType = reportFormsInfo.getContractorClassCode()==null?"":reportFormsInfo.getContractorClassCode();
			
			
			String url = REPORT_FORMS_INFO
					+"/"+pageNum
					+"/"+pageSize
					+"?sequenceNumber="+sequenceNumber
					+"&contractorCode="+contractorCode
					+"&contractUnit="+contractUnit
					+"&startTime"+startTime
					+"&endTime"+endTime
					+"&customerType"+customerType
					+"&isSpecialDiscount="+reportFormsInfo.getIsLongTermDiscount()
					+"&orderTypeCode="+reportFormsInfo.getStatus()
					+"&officeCode="+reportFormsInfo.getOfficeCode();
			return fryeService.getInfo(url, PageHelper.class);
	

	}
	
}
