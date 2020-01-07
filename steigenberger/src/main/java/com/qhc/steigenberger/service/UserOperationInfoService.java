package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.UserOperationInfo;

@Service
public class UserOperationInfoService {

	@Autowired
	FryeService fryeService;

	public List<UserOperationInfo> findByUserId(Integer userId) {
		String url = "userOperationInfo/" + userId;
		return fryeService.getListInfo(url, UserOperationInfo.class);
	}

	public List<UserOperationInfo> findAll() {
		String url = "userOperationInfo";
		return fryeService.getListInfo(url, UserOperationInfo.class);
	}

}
