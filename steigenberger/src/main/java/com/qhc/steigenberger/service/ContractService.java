/**
 * 
 */
package com.qhc.steigenberger.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.Contract;
import com.qhc.steigenberger.domain.Item;
import com.qhc.steigenberger.domain.Mail;
import com.qhc.steigenberger.domain.Order;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.util.NumberToCNUtil;
import com.qhc.steigenberger.util.PageHelper;
import com.qhc.steigenberger.util.Word2PdfUtil;
import com.qhc.steigenberger.util.XwpfUtil;

/**
 * @author Walker
 *
 */
@Service
public class ContractService {
	private Logger logger = LoggerFactory.getLogger(ContractService.class);

	private ObjectMapper mapper = new ObjectMapper()
			.setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM));

	@Value("${contract.dir}")
	private String contractDir;

	@Autowired
	private FryeService fryeService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private MailService mailService;

	@Autowired
	private BestsignService bestsignService;

	public Result find(Map<String, Object> params) {
		Result result = (Result) fryeService.getInfo("/contract", params, Result.class);
		if (result.getStatus().equals("ok")) {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(PageHelper.class, Contract.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}

		return result;
	}

	public Result find(Integer contractId) {
		Result result = (Result) fryeService.getInfo("/contract/" + contractId, Result.class);
		if (result.getStatus().equals("ok")) {
			Contract contract = new ObjectMapper().convertValue(result.getData(), Contract.class);
			result.setData(contract);
		}
		return result;
	}

	public Result findByOrderInfoId(Integer orderInfoId) {
		Map<String, Object> params = new HashMap<>();
		params.put("orderInfoId", orderInfoId);
		Result result = (Result) fryeService.getInfo("/contract", params, Result.class);
		if (result.getStatus().equals("ok")) {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(PageHelper.class, Contract.class);
			PageHelper<Contract> page = mapper.convertValue(result.getData(), javaType);
			if (page.getRows() != null && page.getRows().size() > 0) {
				result.setData(page.getRows().get(0));
			} else {
				result.setData(null);
			}
		}

		return result;
	}

	/**
	 * 查询客户的最后一份合同信息
	 * @param customerCode
	 * @return
	 */
	public Result findLastContract(String customerCode) {
		// 设置分页信息
		int pageNo = 0;
		int pageSize =1;
		
		Map<String, Object> params = new HashMap<>();
		params.put("customerCode", customerCode);
		Result result = this.find(params);
		
		if (result.getData() instanceof PageHelper) {
			PageHelper pageHelper = (PageHelper)result.getData();
			if (pageHelper.getRows()  != null && pageHelper.getRows().size() > 0 ) {
				result.setData(pageHelper.getRows().get(0));
			} else {
				result.setData(null);
			}
		}

		return result;
	}

	/**
	 * 新增或修改合同信息
	 * 
	 * @param contract
	 * @return
	 */
	public Result save(Contract contract) {
		Result result;
		contract.setStatus("01");
		result = (Result) fryeService.postForm("/contract", contract, Result.class);
		if (result.getStatus().equals("ok")) {
			result = mapper.convertValue(result,
					mapper.getTypeFactory().constructParametricType(Result.class, Contract.class));
			deletePdf((Contract) result.getData());
		}
		
		if (!result.getStatus().equals("ok")) {
			throw new RuntimeException(result.getMsg());
		}

		return result;
	}

	public byte[] doDownloadFromSignSystem(String signContractId) {
//		Contract contract = (Contract) this.find(contractId).getData();
//		if (contract == null)
//			return null;
//
//		String signContractId = contract.getSignContractId();
		byte[] zipBytes = bestsignService.downloadContract(signContractId);

		return zipBytes;
	}

	/**
	 * 
	 * 导出合同PDF格式文档
	 * 
	 * @param contractId 合同ID
	 * @return PDF文件
	 * @throws Exception
	 */
	public File exportToPDF(Integer contractId) throws Exception {
		Contract contract = (Contract) this.find(contractId).getData();
		return exportToPDF(contract);
	}

	/**
	 * 导出合同PDF格式文档
	 * 
	 * @param contract 合同对象
	 * @return PDF文件
	 * @throws Exception
	 */
	public File exportToPDF(Contract contract) throws Exception {
		File wordFile = getWordFile(contract);
		File pdfFile = getPdfFile(contract);

		if (pdfFile.exists() && pdfFile.length() > 0) {
			return pdfFile;
		}

		Order order = null;
		OrderQuery query = new OrderQuery();
		query.setSequenceNumber(contract.getSequenceNumber());
		query.setId(contract.getOrderInfoId());
		query.setLast(false);
		query.setIncludeDetail(true);
		Result result = orderService.findOrders(query);
		PageHelper page = null; 
		if(result.getStatus().equals("ok")) {
			page = (PageHelper)result.getData();
		}
		List rows = page.getRows();
		if (rows != null && rows.size() > 0) {
			Object data = rows.get(0);
			order = mapper.convertValue(data, Order.class);
		}

		List<Item> allList = order.getItems();
		Map<String, Object> paramSum = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> paraTable2 = new HashMap<>();
		List<Map<String, String>> detailList = new ArrayList<>();
		Double retailSum = new Double(0.0D);
		Double finalSum = new Double(0.0D);
		Double b2cSum = new Double(0.0D);
		Double optionSum = new Double(0.0D);
		Double transferPrice = new Double(0.0D);
		DecimalFormat df = new DecimalFormat("######0.00");
		if (order != null) {
			params.put("customerName", order.getCustomerName());
			params.put("companyAddress", contract.getCompanyAddress());
			params.put("agentName", contract.getBroker());
			params.put("companyPhone", contract.getCompanyTel());
			params.put("bankName", contract.getBankName());
			params.put("bankAccount", contract.getAccountNumber());
			params.put("companyZipcode", contract.getInvoicePostCode());
			paraTable2.put("customerName", order.getCustomerName());
			paraTable2.put("companyAddress", contract.getCompanyAddress());
			paraTable2.put("agentName", contract.getBroker());
			paraTable2.put("companyPhone", contract.getCompanyTel());
			paraTable2.put("bankName", contract.getBankName());
			paraTable2.put("bankAccount", contract.getAccountNumber());
			// 公司邮编
			paraTable2.put("companyZipcode", contract.getInvoicePostCode());
		}

		if (allList != null && allList.size() > 0) {
			for (Item item : allList) {
				String itemName = "";
				if ("BG1P7E00000".equals(item.getMaterialCode())) {
					// 产品实卖金额
					Double saleAmount = new Double(0.0D);
					if (item.getActualPrice() != 0) {
						saleAmount = item.getActualPrice() * item.getQuantity();
					}
					transferPrice = Double.valueOf(transferPrice.doubleValue() + saleAmount.doubleValue());
					continue;
				}
				String name = item.getMaterialGroupCode();
				if ("3101".equals(name) || "3102".equals(name)) {
					itemName = "展示柜";
				} else if ("3109".equals(name) || "3103".equals(name)) {
					itemName = "机组";
				} else if ("1000".equals(name)) {
					itemName = "配件";
				}
				Map<String, String> mapTemp = new HashMap<>();
				Double wholesaleAmount = new Double(0.0D);
				// 市场零售金额
				if (item.getRetailPrice() != 0) {
					wholesaleAmount = item.getRetailPrice() * item.getQuantity();
				}
				Double saleAmount = new Double(0.0D);
				// // 产品实卖金额
				if (item.getActualPrice() != 0) {
					saleAmount = item.getActualPrice() * item.getQuantity();
				}
				Double B2cAmount = new Double(0.0D);
				// B2C预估金额
				if (item.getB2cEstimatedPrice() != 0) {
					B2cAmount = item.getB2cEstimatedPrice() * item.getQuantity();
				}
				b2cSum = Double.valueOf(b2cSum.doubleValue() + B2cAmount.doubleValue());
				Double optionSaleAmount = new Double(0.0D);
				// 可选项实卖金额
				if (item.getOptionalActualPrice() != 0) {
					optionSaleAmount = item.getOptionalActualPrice() * item.getQuantity();
				}
				optionSum = Double.valueOf(optionSum.doubleValue() + optionSaleAmount.doubleValue());
				// 计量单位
				String unitNameCN = "";
				unitNameCN = item.getUnitName();
				double needCount = (new Double(0.0D)).doubleValue();
				// 数量
				if (item.getQuantity() != 0) {
					needCount = item.getQuantity();
				}
				wholesaleAmount = Double
						.valueOf((new BigDecimal(wholesaleAmount.doubleValue())).setScale(2, 4).doubleValue());
				saleAmount = Double.valueOf((new BigDecimal(saleAmount.doubleValue())).setScale(2, 4).doubleValue());
				mapTemp.put("prodName", itemName);
				// 规格型号
				mapTemp.put("prodModel", item.getMaterialName());
				// 计量单位
				mapTemp.put("unitName", unitNameCN);
				mapTemp.put("needCount", "" + (int) needCount);
				mapTemp.put("wholesaleAmount", df.format(wholesaleAmount));
				mapTemp.put("discountAmount", df.format(saleAmount));
				retailSum = Double.valueOf(retailSum.doubleValue() + wholesaleAmount.doubleValue());
				finalSum = Double.valueOf(finalSum.doubleValue() + saleAmount.doubleValue() + B2cAmount.doubleValue()
						+ optionSaleAmount.doubleValue());
				detailList.add(mapTemp);
			}
		}

		paramSum.put("B2C", df.format(b2cSum));
		paramSum.put("transferPrice", df.format(transferPrice));
		paramSum.put("optionAmount", df.format(optionSum));
		finalSum = Double.valueOf(finalSum.doubleValue() + transferPrice.doubleValue());
		retailSum = Double.valueOf((new BigDecimal(retailSum.doubleValue())).setScale(2, 4).doubleValue());
		finalSum = Double.valueOf((new BigDecimal(finalSum.doubleValue())).setScale(2, 4).doubleValue());
		paramSum.put("retailSum", df.format(retailSum));
		paramSum.put("finalSum", df.format(finalSum));
		paramSum.put("finalSumCN", NumberToCNUtil.number2CNMontrayUnit(new BigDecimal(finalSum.doubleValue())));

		params.put("contractCode", contract.getContractNumber());
		params.put("deliveryIndays", "" + contract.getDeliveryDays());
		// 接货方式-盖章接货
		params.put("receiveWaysSeal", XwpfUtil.map.get(contract.getReceiveType().equals("01")));
		// 接货方式-授权人签字
		params.put("receiveWaysSign", XwpfUtil.map.get(contract.getReceiveType().equals("02")));
		// 接货方式-其他
		params.put("receiveWaysOther", XwpfUtil.map.get(contract.getReceiveType().equals("03")));
		// 接货人1姓名
		params.put("receiverUser1", contract.getContactor1Tel());
		// 接货人1身份证号
		params.put("receiverIdcard1", contract.getContactor1Id());
		// 终端客户名称
		params.put("terminalCustname", contract.getClientName());
		// 实际安装地点
		params.put("installPlace", contract.getInstallLocation());

		// 质量标准-其他
		params.put("qualityStandardOther",
				XwpfUtil.map.get(contract.getInstallType() == null || contract.getInstallType().equals("01")));
		// 质量标准-自安自保
		params.put("qualityStandardSelfHode",
				XwpfUtil.map.get(!(contract.getInstallType() == null || contract.getInstallType().equals("02"))));
		// 验收标准（方法）-需方负责安装调试
		params.put("acceptanceStandardBuyer", XwpfUtil.map.get(contract.getAcceptanceCriteriaCode().equals("1001")));
		// 验收标准（方法）-供方负责安装调试
		params.put("acceptanceStandardSupplier", XwpfUtil.map.get(contract.getAcceptanceCriteriaCode().equals("1002")));
		// 结算方式
		params.put("accountWay", contract.getPaymentType());

		// 发票邮寄地址
		params.put("invoiceToaddress", contract.getInvoiceAddress());
		// 邮编
		params.put("postCode", contract.getInvoicePostCode());
		// 发票接收人
		params.put("invoiceToperson", contract.getInvoiceReceiver());
		// 联系电话
		params.put("phoneNo", contract.getInvoiceTel());

		XwpfUtil xwpfUtil = new XwpfUtil();

		InputStream is = getClass().getClassLoader().getResourceAsStream("contract_template.docx");
		XWPFDocument doc = new XWPFDocument(is);

		xwpfUtil.replacePara(doc, params);
		xwpfUtil.exportTable1(doc, detailList, paramSum, 0, Boolean.valueOf(false));
		xwpfUtil.replaceTable2(doc, paraTable2, 1);

		FileOutputStream vos = new FileOutputStream(wordFile);
		doc.write(vos);

		xwpfUtil.close(vos);
		xwpfUtil.close(is);

		boolean seccess = true;

//		seccess = xwpfUtil.transferToPDF(new FileInputStream(wordFile), new FileOutputStream(pdfFile));

		// 使用jacob包转换word为pdf
		seccess = Word2PdfUtil.word2pdf(wordFile, pdfFile);

		if (!seccess) {
			pdfFile.delete();
		}

//		wordFile.delete();

		return pdfFile;
	}

	/**
	 * 
	 * 合同修改后删除已生成的pdf文件
	 * 
	 * @param contract
	 */
	public void deletePdf(Contract contract) {
		File pdfFile = getPdfFile(contract);
		if (pdfFile.exists()) {
			pdfFile.delete();
		}
	}

	private File getPdfFile(Contract contract) {
		Calendar c = Calendar.getInstance();
		c.setTime(contract.getCreateTime());
		File path = new File(contractDir, String.valueOf(c.get(Calendar.YEAR)));
		if (!path.exists()) {
			path.mkdirs();
		}

		// 需求流水号-签约单位-合同号(核算号)
		String pdfFileName = "";
		if (contract.getVersion() != null && !contract.getVersion().isEmpty()) {
			pdfFileName = contract.getSequenceNumber() + "-" + contract.getCustomerName() + "("
					+ contract.getContractNumber() + "-" + contract.getVersion() + ").pdf";
		} else {
			pdfFileName = contract.getSequenceNumber() + "-" + contract.getCustomerName() + "("
					+ contract.getContractNumber() + ").pdf";
		}
		File pdfFile = new File(path, pdfFileName);
		return pdfFile;
	}

	private File getWordFile(Contract contract) {
		Calendar c = Calendar.getInstance();
		c.setTime(contract.getCreateTime());
		File path = new File(contractDir, String.valueOf(c.get(Calendar.YEAR)));
		if (!path.exists()) {
			path.mkdirs();
		}

		// 需求流水号-签约单位-版本号
		String fileName = contract.getSequenceNumber() + "-" + contract.getCustomerName() + "-"
				+ contract.getVersion() + ".docx";
		// 需求流水号-签约单位-合同号(核算号)
		File wordFile = new File(path, fileName);
		return wordFile;
	}

	public Contract sendMailToCustomer(Integer contractId) {
		Contract contract = (Contract) this.find(contractId).getData();
		return sendMailToCustomer(contract);
	}

	public Contract sendMailToCustomer(Contract contract) {
		Integer contractId = contract.getId();
		try {
			File docFile = exportToPDF(contract);

			Map<String, Object> valMap = new HashMap<>();
			// 签约单位
			valMap.put("customerName", contract.getCustomerClazzName());
			// 合同号(核算号)
			valMap.put("contractCode", contract.getContractNumber());
			// 版本号，XX_fXX_yyyy.MM.dd
			valMap.put("versionNo", contract.getVersion());
			// 创建日期
			valMap.put("createDate", contract.getCreateTime());

			String filename = contract.getSequenceNumber() + "-" + contract.getCustomerCode() + "-"
					+ contract.getVersion() + ".pdf";

			Mail mail = new Mail();
			mail.setId(UUID.randomUUID().toString());
			mail.setFrom(null);
			mail.setTo(contract.getCustomerEmail());
			// LinkedHashMap<String, String> attachments = createAttachments(docFile,
			// fileName);
			LinkedHashMap<String, File> attachments = new LinkedHashMap<String, File>();
			attachments.put(filename, docFile);
			mail.setAttachments(attachments);
//			String body = mailService.render("contract-notice", "HTML5", valMap);
			String body = mailService.render("/contract-notice.html", "HTML5", valMap);
			mail.setBody(body);
			mail.setSubject("【" + valMap.get("contractCode") + "】" + valMap.get("versionNo") + "版合同已制作请上传签署");

			logger.info("contract mail body: " + body);
			boolean sendStatus = mailService.send(mail);
			
			if (!sendStatus) {
				throw new RuntimeException("发送邮件失败！");
			}

			String fileHashCode = bestsignService.generateShA1Code(docFile);
			// update contract file_hash_code
//			contract.setFileHashCode(fileHashCode);
			this.updateFileHashCode(contractId, fileHashCode);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return contract;
	}

	/**
	 * 查询电子签约合同状态并更新本地合同状态及电子签约合同id
	 * 
	 * @return
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	public boolean doRefreshContractState() throws JsonMappingException, JsonProcessingException {
		Result r = (Result)fryeService.putForm("/contract/refreshState", "", Result.class);
		return r.getStatus().equals("ok");
		
//		String states = "03,04,05,06";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("statusList", "3,4,5,6");
//		List<Contract> contractList = (List<Contract>)((PageHelper)this.find(params).getData()).getRows();
//		List<ContractSignSys> signList = bestsignService.syncContractSignSysData();
//		if (signList == null || signList.size() <= 0) {
//			logger.info(System.currentTimeMillis() + ":--update contracts' state--Failed");
//			return false;
//		}
//
//		for (Contract contract : contractList) {
//			String fileHashCode = contract.getFileHashCode();
//			if (fileHashCode == null || fileHashCode.isEmpty())
//				continue;
//
//			Optional<ContractSignSys> signed = signList.stream().filter(s -> fileHashCode.equals(s.getFileHashCode()))
//					.findFirst();
//			String signContractId = signed.isPresent() ? signed.get().getSignContractId() : null;
//			String state = "03";
//			if (signContractId != null) {
//				state = bestsignService.getContractStatus(signContractId, contract.getContractorName());
//			}
//			if (!contract.getStatus().equals(Integer.parseInt(state))) {
//				contract.setSignContractId(signContractId);
//				contract.setStatus(Integer.parseInt(state));
//
//				// 更新合同状态及电子签约合同ID
//				this.updateSignId(contract.getId(), signContractId, Integer.parseInt(state));
////				this.updateStatus(contract.getId(), Integer.parseInt(state));
//			}
//		}
//		return true;
	}

	/**
	 * 
	 * 签署合同
	 * 
	 * @param contractId 合同ID
	 * @return
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	public boolean doSignContract(int contractId) throws JsonMappingException, JsonProcessingException {
		Result r = (Result)fryeService.putForm("/contract/" + contractId + "/sign", "", Result.class);
		return r.getStatus().equals("ok");
		
//		Contract contract = (Contract) this.find(Integer.valueOf(contractId)).getData();
//
//		if (contract == null)
//			return false;
//
//		// 电子签约中合同Id
//		String signContractId = contract.getSignContractId();
//		boolean result = bestsignService.doSignContract(signContractId);
//		String state = bestsignService.getContractStatus(signContractId, contract.getContractorName());
//		System.out.println("state:" + state);
//		if (result || state.equals("06")) {
////			contract.setState("06");
//			contract.setStatus(6);
//
//			// 使用上上签电子合同状态更新数据库更新合同状态
//			this.updateStatus(contractId, 6);
//		}
//
//		return false;
	}
	
	public Result updateStatus(Integer contractId, Integer status) {
		logger.info("updateStatus({}, {})", contractId, status);
		Result result = null;
		result = (Result) fryeService.putForm("/contract/" + contractId + "/status/" + status, "", Result.class);
		if (!result.getStatus().equals("ok")) {
			throw new RuntimeException(result.getMsg());
		}

		return result;
	}
	
	public Result updateSignId(Integer contractId, String signContractId, Integer status) {
		logger.info("updateSignId({}, {})", contractId, signContractId);
		Result result = null;
		result = (Result) fryeService.putForm("/contract/" + contractId + "/signid/" + signContractId + "/" + status, "", Result.class);
		if (!result.getStatus().equals("ok")) {
			throw new RuntimeException(result.getMsg());
		}

		return result;
	}
	
	public Result updateFileHashCode(Integer contractId, String fileHashCode) {
		logger.info("updateFileHashCode({}, {})", contractId, fileHashCode);
		Result result = null;
		result = (Result) fryeService.putForm("/contract/" + contractId + "/hashcode/" + fileHashCode, "", Result.class);
		if (!result.getStatus().equals("ok")) {
			throw new RuntimeException(result.getMsg());
		}

		return result;
	}
}
