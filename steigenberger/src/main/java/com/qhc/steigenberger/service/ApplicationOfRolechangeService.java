package com.qhc.steigenberger.service;

import java.text.DateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.domain.form.DealerOrder;

@Service
public class ApplicationOfRolechangeService {

	@Autowired
	FryeService fryeService;
	
	private static final String URL_APPLICATION = "applicationOfRolechange";
	
	public ApplicationOfRolechange add(ApplicationOfRolechange applicationOfRolechange) {
		String json = (String)fryeService.postForm(URL_APPLICATION, applicationOfRolechange, String.class);

		ApplicationOfRolechange applicationOfRolechange2 = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM));
		try {
			applicationOfRolechange2 = mapper.readValue(json, ApplicationOfRolechange.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return applicationOfRolechange2;
//		return fryeService.postInfo(applicationOfRolechange,URL_APPLICATION, ApplicationOfRolechange.class);
		
	}
	
	
}
