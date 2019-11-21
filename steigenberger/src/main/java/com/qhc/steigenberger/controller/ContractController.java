package com.qhc.steigenberger.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.domain.Contract;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.service.ContractService;
import com.qhc.steigenberger.service.OperationService;
import com.qhc.steigenberger.service.UserService;

@Controller
@RequestMapping("contract")
public class ContractController {

	@Autowired
	ContractService contractService;

	@GetMapping(path = "/")
	@ResponseBody
	public Result findAll(@RequestParam Map<String, Object> params) {
		Result r = null;
		r = contractService.find(params);
		return r;
	}

	@GetMapping("{id}")
	@ResponseBody
	public Result get(@PathVariable("id") Integer contractId) {
		Result r = null;
		r = contractService.find(contractId);
		return r;
	}

	@PostMapping("/")
	@ResponseBody
	public Result save(@RequestBody Contract contract, HttpServletRequest request) {
		String identityName = request.getSession().getAttribute(UserService.SESSION_USERIDENTITY).toString();
		Result r = null;
		contract.setProductionTime(new Date());
		// 设置状态为已制作
		contract.setStatus(1);
		// 设置操作人
//		contract.seto
		r = contractService.save(contract);
		return r;
	}

	@PutMapping("/{id}/send")
	@ResponseBody
	public Result send(@PathVariable("id") Integer contractId) {
		Result r = null;
//		contractService.
		return r;
	}

	@GetMapping("/{id}/preview")
	@ResponseBody
	public Result preview(@PathVariable("id") Integer contractId) {
		Result r = null;
//		Map<String,Object> m = new HashMap<String,Object>();
//		m.put("list", operationService.getList());
		return r;
	}

	@RequestMapping({ "/export" })
	public void exportContract(HttpServletRequest request, HttpServletResponse response) {
		Contract contract = null;

		String idStr = request.getParameter("id");
		Integer contractId = Integer.parseInt(idStr);

		File pdfFile = null;
		InputStream pis = null;
		try {
			pdfFile = this.contractService.exportToPDF(contractId);
			String pdfFileName = new String(pdfFile.getName().getBytes("gb2312"), "ISO8859-1");
			response.setContentType("application/x-download;charset=GB2312");
			response.setHeader("Content-disposition", "attachment;filename=\"" + pdfFileName + "\"");

			pis = new FileInputStream(pdfFile);
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = pis.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}

//			pis.close();
//			pdfFile.deleteOnExit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pis != null) {
					pis.close();
				}
			} catch (IOException e) {
			}
			pdfFile.deleteOnExit();
		}
	}

}
