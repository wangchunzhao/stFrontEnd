package com.qhc.steigenberger.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.service.KOrderInfoService;
import com.qhc.steigenberger.service.SapSalesOfficeService;
import com.qhc.steigenberger.util.PageHelper;

@Controller
@RequestMapping("/kOrderInfo")
public class KOrderInfoController extends BaseController{
	
	@Autowired
	SapSalesOfficeService sapSalesOfficeService;
	
	@Autowired
	KOrderInfoService kOrderInfoService;
	
	@RequestMapping("/kOrderInfoList")
	@ResponseBody
	public PageHelper<KOrderInfo> getUserListPage(KOrderInfo kOrderInfo,HttpServletRequest request) {
		
	
		// 查询当前页实体对象
		PageHelper<KOrderInfo> pageHelper = kOrderInfoService.getList(kOrderInfo.getPage()-1, kOrderInfo.getLimit(), kOrderInfo);
		return pageHelper;
	}
	
	@RequestMapping("/orderManageList")
  	public String todo() {
  		return "orderManage/myOrder";
  	}
	
	
	@RequestMapping("/getUserListPage")
	@ResponseBody
	public List<SapSalesOffice> getUserListPage1(SapSalesOffice sapSalesOffice,HttpServletRequest request){
		List<SapSalesOffice> list = sapSalesOfficeService.getList();
		return list;
	}

}
