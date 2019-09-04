package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.Operations;
import com.qhc.steigenberger.service.WebServcieTool;

@Service
public class OperationService extends WebServcieTool<Operations>{
	
	static String BASEURL = "http://localhost:8801/frye/";


	
	public List<Operations> findAll() {
		
		String url ="operations";
		return  findAll(BASEURL, url,Operations.class);
	}

	
}

