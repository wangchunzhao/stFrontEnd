package com.qhc.steigenberger.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.service.ReportService;

@RestController
@RequestMapping("report")
public class ReportController extends BaseController {
	
	@Autowired
	ReportService reportService;

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

	@GetMapping("/excel")
	@ResponseBody
	public void exportExcel(Map<String, Object> params) throws Exception {
		String reportName = (String) params.get("reportname");
		switch (reportName) {
		case "bidding":

			break;
		case "saledetail":
			break;
		case "ordersummary":
			String createTime = (String)params.get("createTime");
			if (!StringUtils.isEmpty(createTime)) {
				String[] times = createTime.split(" - ");
				String start = times[0];
				String end = times[1];
				params.put("createStartTime", start);
				params.put("createEndTime", end);
			}
			break;
		default:
			break;
		}
	}
}
