package com.qhc.steigenberger.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.Operations;
import com.qhc.steigenberger.service.OperationServiceI;
import com.qhc.steigenberger.service.WebServcieTool;

@Service
public class OperationServiceImpl extends WebServcieTool<Operations> implements OperationServiceI{
	
	static String BASEURL = "http://localhost:8801/frye/";


	@Override
	public List<Operations> findAll() {
		String url ="operations/findAll";
		return  findAll(BASEURL, url,Operations.class);
	}

	
}

