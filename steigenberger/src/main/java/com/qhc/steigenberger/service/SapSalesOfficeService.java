package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;
@Service
public class SapSalesOfficeService extends WebServcieTool<SapSalesOffice>{

	public List<SapSalesOffice> findAll() {
		return  findAll(CommonConstant.BASEURL, "sapSalesOffice",SapSalesOffice.class);
	}

}
