package com.qhc.steigenberger.service;

import java.util.List;
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
			
		
			String orderNumber = reportFormsInfo.getOrderNumber()==null?"":reportFormsInfo.getOrderNumber();
			String contractNo = reportFormsInfo.getContractNo()==null?"":reportFormsInfo.getContractNo();
			String contractUnit = reportFormsInfo.getContractUnit()==null?"":reportFormsInfo.getContractUnit();
			String startTime = reportFormsInfo.getStartTime()==null?"":reportFormsInfo.getStartTime();
			String endTime = reportFormsInfo.getEndTime()==null?"":reportFormsInfo.getEndTime();
			
			String customerType = reportFormsInfo.getCustomerNo()==null?"":reportFormsInfo.getCustomerNo();
			
			
			
			String url = REPORT_FORMS_INFO
					+"/"+pageNum
					+"/"+pageSize
					+"/"+orderNumber
					+"/"+contractNo
					+"/"+contractUnit
					+"/"+startTime
					+"/"+endTime
					+"/"+reportFormsInfo.getCustomerType()
					+"/"+reportFormsInfo.getIsLongTermDiscount()
					+"/"+reportFormsInfo.getStatus()
					+"/"+reportFormsInfo.getArea();
			return fryeService.getInfo(url, PageHelper.class);
	

	}
	
}
