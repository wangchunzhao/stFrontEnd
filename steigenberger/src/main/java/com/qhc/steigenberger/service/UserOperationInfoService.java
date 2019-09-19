package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.domain.Parameter;
import com.qhc.steigenberger.domain.UserOperationInfo;

@Service
public class UserOperationInfoService {

	@Autowired
	FryeService<UserOperationInfo> fryeService;
	
	private final static String URL_KORDERS = "userOperationInfo";

	public List<UserOperationInfo> findByUserId(Integer userId) {
		String url =URL_KORDERS+"/"+userId;
		return fryeService.getListInfo(url,UserOperationInfo.class);
    }
	
	public List<UserOperationInfo> findAll() {
		String url =URL_KORDERS;
		return fryeService.getListInfo(url,UserOperationInfo.class);
    }
	
	
}
