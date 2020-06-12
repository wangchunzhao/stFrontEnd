package com.qhc.steigenberger.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.MaterialGroups;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.ReportService;
import com.qhc.steigenberger.util.JxlsUtils;

@RestController
@RequestMapping("report")
public class ReportController extends BaseController {
	
	@Autowired
	ReportService reportService;

	@GetMapping("/orderitems")
	@ResponseBody
	public Result orderPurchaseSaleReport(@RequestParam(required = false) Map<String, Object> params) throws Exception {
		Result result = null;
		try {
			setQueryScope(params);	
			result = reportService.findOrderPurchaseSale(params);
		} catch (Throwable e) {
			e.printStackTrace();
			result = Result.error(e.getMessage());
		}
		return result;
	}

	@GetMapping("/bidding")
	@ResponseBody
	public Result biddingReport(@RequestParam(required = false) Map<String, Object> params) throws Exception {
		Result result = null;
		try {
			setQueryScope(params);
			result = reportService.findBiddingReport(params);
		} catch (Throwable e) {
			e.printStackTrace();
			result = Result.error(e.getMessage());
		}
		return result;
	}

	@GetMapping("/ordersummary")
	@ResponseBody
	public Result orderSummaryReport(@RequestParam(required = false) Map<String, Object> params) throws Exception {
		Result result = null;
		try {
			setQueryScope(params);			
			result = reportService.findOrderSummaryReport(params);
		} catch (Throwable e) {
			e.printStackTrace();
			result = Result.error(e.getMessage());
		}
		return result;
	}

	@GetMapping("/export")
	@ResponseBody
	public void exportExcel(@RequestParam(required = false) Map<String, Object> params, HttpServletResponse response) throws Exception {
		String reportName = (String) params.get("reportname");
		Result result = null;
		String file = null;
		String fileName = null;
		List orders = new ArrayList();
		switch (reportName) {
			case "orderitems" :
				file = "/orderitemsreport.xlsx";
				fileName = "购销明细报表.xlsx";
				result = this.orderPurchaseSaleReport(params);
				if (result.getStatus().equalsIgnoreCase("ok")) {
					orders = (List)((Map)result.getData()).get("rows");
				}
				break;
			case "bidding" :
				file = "/biddingreport.xlsx";
				fileName = "投标跟踪表.xlsx";
				result = this.biddingReport(params);
				if (result.getStatus().equalsIgnoreCase("ok")) {
					orders = (List)((Map)result.getData()).get("rows");
				}
				break;
			case "ordersummary" :
				file = "/ordersummaryreport.xlsx";
				fileName = "销售订单汇总表.xlsx";
				result = this.orderSummaryReport(params);
				if (result.getStatus().equalsIgnoreCase("ok")) {
					orders = (List)((Map)result.getData()).get("rows");
				}
				break;
			default :
				break;
		}
		
		if (file != null) {
			String excleFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");

			response.setContentType("application/x-download;charset=GB2312");
			response.setHeader("Content-disposition", "attachment;filename=\"" + excleFileName + "\"");

			Map<String, Object> data = new HashMap<>();
			data.put("orders", orders);
			 JxlsUtils.exportExcel(file, response.getOutputStream(), data);
			response.getOutputStream().flush();
		}
	}
	
	/**
	 * 数据访问范围
	 * 客户经理只能查看自己下的订单 0 <br>
	 * 区域经理只能查看本区域订单 1 <br>
	 * 支持经理/B2C审核/工程人员/领导组/管理员，可以查看全部订单 2<br>
	 * 
	 * @return 
	 */
	private void setQueryScope(Map<String, Object> params) {
		// 6 区域经理
		// 2 客户经理
		User user = this.getAccount();
		List<Role> roles = user.getRoles();
		int scope = 0;
		for (Role role : roles) {
			int id = role.getId();
			if (id == 2) {
			} else if (id == 6) {
				if (scope < 1) {
					scope = 1;
				}
			} else {
				if (scope < 2) {
					scope = 2;
				}
			}
		}
		switch (scope) {
			case 0 :
				params.put("scope", 0);
				params.put("userid", user.getUserIdentity());
				break;
			case 1 :
				params.put("scope", 1);
				params.put("region", user.getOfficeCode());
				break;
			case 2 :
				params.put("scope", 2);
				break;

			default :
				break;
		}
	}
}
