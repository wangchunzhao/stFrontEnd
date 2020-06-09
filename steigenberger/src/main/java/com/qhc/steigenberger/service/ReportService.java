package com.qhc.steigenberger.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.Result;

@Service
public class ReportService {

	@Autowired
	FryeService fryeService;

	public Result findOrderPurchaseSale(Map<String, Object> params) {
		StringBuilder url = new StringBuilder();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			Object value = entry.getValue();
			value = value == null ? "" : value.toString();
			url.append("&").append(entry.getKey()).append("=").append(value);
		}
		String u = "report/orderdetail" + (url.length() > 0 ? "?" + url.substring(1) :  "");
		return fryeService.getInfo(u, Result.class);
	}

	public Result findBiddingReport(Map<String, Object> params) {
		StringBuilder url = new StringBuilder();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			Object value = entry.getValue();
			value = value == null ? "" : value.toString();
			url.append("&").append(entry.getKey()).append("=").append(value);
		}
		String u = "report/bidding" + (url.length() > 0 ? "?" + url.substring(1) :  "");
		return fryeService.getInfo(u, Result.class);
	}

	public Result findOrderSummaryReport(Map<String, Object> params) {
		StringBuilder url = new StringBuilder();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			Object value = entry.getValue();
			value = value == null ? "" : value.toString();
			url.append("&").append(entry.getKey()).append("=").append(value);
		}
		String u = "report/ordersummary" + (url.length() > 0 ? "?" + url.substring(1) :  "");
		return fryeService.getInfo(u, Result.class);
	}
	
}
