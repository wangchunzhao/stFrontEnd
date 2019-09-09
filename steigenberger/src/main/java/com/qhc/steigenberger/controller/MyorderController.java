package com.qhc.steigenberger.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.service.SapSalesOfficeService;
import com.qhc.steigenberger.util.PageHelper;


@Controller
@RequestMapping("/myOrder")
public class MyorderController extends BaseController{
	
	@Autowired
	SapSalesOfficeService sapSalesOfficeService;
	
	@RequestMapping("/orderManageList1")
	@ResponseBody
	public PageHelper<SapSalesOffice> getUserListPage(SapSalesOffice sapSalesOffice,HttpServletRequest request) {
		System.out.println("aaaaaaa");
	     PageHelper<SapSalesOffice> pageHelper = new PageHelper<SapSalesOffice>();
		// 统计总记录数
		//Integer total = sapSalesOfficeService.getTotal(sapSalesOffice);
		pageHelper.setTotal(11);
	 
		// 查询当前页实体对象
		List<SapSalesOffice> list = sapSalesOfficeService.getList();
		pageHelper.setRows(list);
	 
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
