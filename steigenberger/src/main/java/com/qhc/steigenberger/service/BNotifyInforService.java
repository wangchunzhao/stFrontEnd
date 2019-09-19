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
	
	//订单编号，客户经理，订单类型，签约人，发送人员组
	public Boolean sendEmail(String id,String customerManage,String orderType,String contractUnit,int type) {
		if(type == 1) {
			
		}
		
		
		
		
		return true;
	}
	
	
}
