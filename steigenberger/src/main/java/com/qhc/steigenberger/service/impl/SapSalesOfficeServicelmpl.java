package com.qhc.steigenberger.service.impl;

import java.util.List;

import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.service.SapSalesOfficeServicel;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

public class SapSalesOfficeServicelmpl extends WebServcieTool<SapSalesOffice> implements SapSalesOfficeServicel{

	@Override
	public List<SapSalesOffice> findAll() {
		return  findAll(CommonConstant.BASEURL, CommonConstant.URl_ROLE_FINDALL,Role.class);
	}

}
