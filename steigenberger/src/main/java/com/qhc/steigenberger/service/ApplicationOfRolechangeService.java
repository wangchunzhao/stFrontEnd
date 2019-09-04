package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.ApplicationOfRolechange;

@Service
public class ApplicationOfRolechangeService {

	@Autowired
	FryeService<ApplicationOfRolechange> fryeService;
	
	private static final String URL_APPLICATION = "applicationOfRolechange";
	
	public ApplicationOfRolechange add(ApplicationOfRolechange applicationOfRolechange) {
		
		return fryeService.postInfo(applicationOfRolechange,URL_APPLICATION, ApplicationOfRolechange.class);
		
	}
	
	
}
