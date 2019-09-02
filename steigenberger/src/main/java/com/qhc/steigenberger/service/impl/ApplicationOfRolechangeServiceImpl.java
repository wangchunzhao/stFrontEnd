package com.qhc.steigenberger.service.impl;

import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.ApplicationOfRolechangeService;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

@Service
public class ApplicationOfRolechangeServiceImpl extends WebServcieTool<ApplicationOfRolechange> implements ApplicationOfRolechangeService{

	@Override
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
