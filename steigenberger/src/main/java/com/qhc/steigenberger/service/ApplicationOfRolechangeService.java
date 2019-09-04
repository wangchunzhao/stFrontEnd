package com.qhc.steigenberger.service;

import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

@Service
public class ApplicationOfRolechangeService extends WebServcieTool<ApplicationOfRolechange>{

	
	public String add(ApplicationOfRolechange applicationOfRolechange) {
		String result = null;
		try {
			result = postInfo(applicationOfRolechange,CommonConstant.BASEURL+"applicationOfRolechange/add",ApplicationOfRolechange.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
