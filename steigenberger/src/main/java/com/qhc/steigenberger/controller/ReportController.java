package com.qhc.steigenberger.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qhc.steigenberger.domain.Order;

@RestController
@RequestMapping("/report")
public class ReportController extends BaseController {

	@PostMapping("/")
	@ResponseBody
	public void reportData(Map<String, Object> params) throws Exception {

	}

	@PostMapping("/excel")
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
