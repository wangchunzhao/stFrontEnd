package com.qhc.steigenberger.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.service.SapSalesOfficeServicel;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;
@Service
public class SapSalesOfficeServicelmpl extends WebServcieTool<SapSalesOffice> implements SapSalesOfficeServicel{

	@Override
	public List<SapSalesOffice> findAll() {
		return  findAll(CommonConstant.BASEURL, "sapSalesOffice/findAll",SapSalesOffice.class);
	}

}
