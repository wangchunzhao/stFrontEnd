package com.qhc.steigenberger.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.Operations;

@Service
public class OperationService{
	
	@Autowired
	FryeService fryeService;
	
	private final static String URL_OPERATIONS = "operations";

	
	public List<Operations> getList() {
		
		return fryeService.getListInfo(URL_OPERATIONS, Operations.class);
	}


	
}

