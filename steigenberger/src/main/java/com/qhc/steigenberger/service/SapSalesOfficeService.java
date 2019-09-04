package com.qhc.steigenberger.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.SapSalesOffice;
@Service
public class SapSalesOfficeService{
	
	@Autowired
	FryeService<SapSalesOffice> fryeService;
	
	private final static String URL_SAPSALEOFFICE = "sapSalesOffice";

	public List<SapSalesOffice> getList() {
		return  fryeService.getListInfo(URL_SAPSALEOFFICE,SapSalesOffice.class);
	}

}
