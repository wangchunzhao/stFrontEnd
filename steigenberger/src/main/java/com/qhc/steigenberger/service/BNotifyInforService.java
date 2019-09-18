package com.qhc.steigenberger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.domain.BNotifyInfor;

@Service
public class BNotifyInforService {
	
	@Autowired
	FryeService<BNotifyInfor> fryeService;
	
	private final static String URL = "bNotifyInfor";
	
	public BNotifyInfor add(BNotifyInfor bNotifyInfor) {
		
		return fryeService.postInfo(bNotifyInfor,URL, BNotifyInfor.class);
		
	}
	
	
	
}
