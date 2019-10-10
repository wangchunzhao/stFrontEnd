package com.qhc.steigenberger.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.SalesOffice;
@Service
public class SapSalesOfficeService{
	
	@Autowired
	FryeService<SalesOffice> fryeService;
	
	private final static String URL_SAPSALEOFFICE = "location/sapSalesOffice";

	public List<SalesOffice> getList() {
		return  fryeService.getListInfo(URL_SAPSALEOFFICE,SalesOffice.class);
	}

}
