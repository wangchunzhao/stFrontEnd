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
import com.qhc.steigenberger.service.ReportService;
import com.qhc.steigenberger.util.JxlsUtils;

@RestController
@RequestMapping("report")
public class ReportController extends BaseController {
	
	@Autowired
	ReportService reportService;

	@GetMapping("/orderdetail")
	@ResponseBody
	public Result orderPurchaseSaleReport(@RequestParam(required = false) Map<String, Object> params) throws Exception {
		Result result = null;
		try {
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
			case "orderdetail" :
				break;
			case "bidding" :
				break;
			case "ordersummary" :
				file = "/ordersummaryreport.xlsx";
				fileName = "OrderSummary.xlsx";
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
}
