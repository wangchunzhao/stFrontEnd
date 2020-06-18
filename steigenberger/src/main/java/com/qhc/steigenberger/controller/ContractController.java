package com.qhc.steigenberger.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.Contract;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.service.ContractService;
import com.qhc.steigenberger.service.UserService;

@Controller
@RequestMapping("contract")
public class ContractController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ContractController.class);

	@Autowired
	ContractService contractService;

	@Value("${contract.bestsign.contractDir}")
	private String contractDir;

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

	@GetMapping("last/{customerCode}")
	@ResponseBody
	public Result getLast(@PathVariable("customerCode") String customerCode) {
		Result r = null;
		r = contractService.findLastContract(customerCode);
		return r;
	}

	@PostMapping("/")
	@ResponseBody
	public Result save(@RequestBody Contract contract, HttpServletRequest request) {
		Result r = null;
		try {
			String identityName = request.getSession().getAttribute(Constants.IDENTITY).toString();
			contract.setCreater(identityName);
			contract.setCreateTime(new Date());
			// 设置操作人
			r = contractService.save(contract);
		} catch (Exception e) {
			logger.error("Save Contract", e);
			r = Result.error("保存失败！");
		}
		return r;
	}

	@PostMapping("/send")
	@ResponseBody
	public Result saveAndSend(@RequestBody Contract contract) {
		Result r = null;
		try {
			String identityName = this.getUserIdentity();
			contract.setCreateTime(new Date());
			// 设置状态为已制作
//			contract.setStatus("01");
			String status = contract.getStatus();
			// 重新编辑的要删除生成的文档
			if (status != null && !status.equals("01")) {
				contractService.deletePdf(contract);
			}
			// 设置操作人
//		contract.seto
			r = contractService.save(contract);
			if (r.getStatus().equals("ok")) {
				contractService.sendMailToCustomer(identityName, (Contract)r.getData());
			}
			r = Result.ok("");
		} catch (Exception e) {
			logger.error("Save and send Contract", e);
			r = Result.error("保存并发送合同给客户失败！" + e.getMessage());
		}
		return r;
	}

	@PutMapping("/{id}/send")
	@ResponseBody
	public Result send(@PathVariable("id") Integer contractId) {
		Result<String> result = new Result<String>();
		try {
			String identityName = this.getUserIdentity();
			Contract contract = this.contractService.sendMailToCustomer(identityName, Integer.valueOf(contractId));
			if (contract != null) {
				result = Result.ok("");
			} else {
				result = Result.error("Contract is null");
			}
		} catch (Exception e) {
			logger.error("send contract", e);
			result = Result.error("发送合同给客户失败！");
		}
		return result;
	}

	/**
	 * 预览pdf文档
	 * @param contractId
	 * @param request
	 * @param response
	 */
	@GetMapping("/{id}/preview")
	@ResponseBody
	public void preview(@PathVariable("id") Integer contractId, HttpServletRequest request,
			HttpServletResponse response) {
		File pdfFile = null;
		InputStream pis = null;
		try {
			pdfFile = this.contractService.exportToPDF(contractId);
//			String pdfFileName = new String(pdfFile.getName().getBytes("gb2312"), "ISO8859-1");
//			response.setContentType("application/x-download;charset=GB2312");
//			response.setHeader("Content-disposition", "attachment;filename=\"" + pdfFileName + "\"");

			pis = new FileInputStream(pdfFile);
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = pis.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pis != null) {
					pis.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 
	 * 导出PDF格式合同文档
	 * 
	 * @param contractId
	 * @param request
	 * @param response
	 */
	@GetMapping({ "/{id}/export" })
	public void exportContract(@PathVariable("id") Integer contractId, HttpServletRequest request,
			HttpServletResponse response) {
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pis != null) {
					pis.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 
	 * 手工刷新电子签约合同状态
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	@PutMapping(value = { "/refreshState" })
	@ResponseBody
	public Result<?> refreshState() throws JsonProcessingException {
		Result<?> r = null;
		boolean flag = this.contractService.doRefreshContractState();
		if (flag) {
			r = Result.ok("");
		} else {
			r = Result.error("");
		}
		return r;
	}

	/**
	 * 签署合同
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@PutMapping(value = { "{id}/sign" })
	@ResponseBody
	public Result<?> signContract(@PathVariable("id") Integer contractId) throws JsonProcessingException {
		Result<?> r = null;
		String identityName = this.getUserIdentity();
		r = this.contractService.doSignContract(identityName, contractId);
		return r;
	}

	/**
	 * 从上上签下载电子签约合同文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping({ "{id}/download" })
	public void downloadContract(@PathVariable("id") Integer contractId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Contract contract = (Contract)this.contractService.find(contractId).getData(); 

		String signContractId = contract.getSignContractid();
		byte[] zipBytes = this.contractService.doDownloadFromSignSystem(signContractId);

		String time = String.valueOf(System.currentTimeMillis());
		String path = this.contractDir + contract.getSequenceNumber() + "_" + contract.getCustomerName() + "_" + time
				+ ".zip";
		ZipFile zf = null;
		InputStream in = null;
		ZipInputStream zin = null;
		InputStream pin = null;
		try {
			File p = new File(this.contractDir);
			if (!p.exists()) {
				p.mkdirs();
			}
			Files.write(Paths.get(path, new String[0]), zipBytes, new java.nio.file.OpenOption[0]);
			zf = new ZipFile(path);
			in = new BufferedInputStream(new FileInputStream(path));
			zin = new ZipInputStream(in);
			ZipEntry ze;
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.toString().endsWith(".pdf")) {
					pin = new BufferedInputStream(zf.getInputStream(ze));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String customerName = "";
		if (contract.getCustomerName() != null) {
			customerName = new String(contract.getCustomerName().getBytes("gb2312"), "ISO8859-1");
		}
		String pdfFileName = contract.getSequenceNumber() + "-" + customerName + "(" + contract.getContractNumber()
				+ ").pdf";
		if (contract.getVersion() != null && !contract.getVersion().isEmpty())
			pdfFileName = contract.getSequenceNumber() + "-" + customerName + "(" + contract.getContractNumber() + "-"
					+ contract.getVersion() + ").pdf";
		response.setContentType("application/x-download;charset=GB2312");
		response.setHeader("Content-disposition", "attachment;filename=" + pdfFileName);

		byte[] b = new byte[1024];
		int len = -1;
		while ((len = pin.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}

		try {
			if (in != null)
				pin.close();
			if (zin != null)
				zin.closeEntry();
			if (in != null)
				in.close();
			if (zf != null)
				zf.close();
			(new File(path)).delete();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
