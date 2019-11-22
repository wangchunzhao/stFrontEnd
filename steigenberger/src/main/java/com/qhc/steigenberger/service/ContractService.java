/**
 * 
 */
package com.qhc.steigenberger.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.Contract;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.form.AbsOrder;
import com.qhc.steigenberger.domain.form.BaseItem;
import com.qhc.steigenberger.domain.form.BaseOrder;
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
	private ObjectMapper mapper = new ObjectMapper().setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM));
	
	@Value("${contract.dir}")
	private String contractDir;
	
	@Autowired
	private FryeService fryeService;
	
	@Autowired
	private OrderService orderService;
	
	public Result find(Map<String, Object> params) {
		Result result = (Result)fryeService.getInfo("/contract", params, Result.class);
		if (result.getStatus().equals("ok")) {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(Result.class, mapper.getTypeFactory().constructParametricType(ArrayList.class, Contract.class));
			// result.setData(mapper.convertValue(result.getData(), );
			result = mapper.convertValue(result, javaType);
		}
		
		return result;
	}
	
	public Result<Contract> find(Integer contractId) {
		Result result = (Result)fryeService.getInfo("/contract/" + contractId, Result.class);
		if (result.getStatus().equals("ok")) {
			Contract contract = new ObjectMapper().convertValue(result.getData(), Contract.class);
			result.setData(contract);
		}
		return result;
	}
	
	public Result save(Contract contract) {
		return (Result)fryeService.postForm("/contract", contract, Result.class);
	}

	public File exportToPDF(Integer contractId) throws Exception {
		Contract contract = (Contract)this.find(contractId).getData();
		// 需求流水号-签约单位-版本号
		String fileName = contract.getSequenceNumber() + "-" + contract.getContractorName() + "-" + contract.getVersion()
				+ ".docx";
		// 需求流水号-签约单位-合同号(核算号)
		String pdfFileName = "";
		if (contract.getVersion() != null && !contract.getVersion().isEmpty()) {
			pdfFileName = contract.getSequenceNumber() + "-" + contract.getContractorName() + "(" + contract.getContractNumber()
					+ "-" + contract.getVersion() + ").pdf";
		} else {
			pdfFileName = contract.getSequenceNumber() + "-" + contract.getContractorName() + "(" + contract.getContractNumber()
			+ ").pdf";
		}
		
		AbsOrder order = null;
		OrderQuery query = new OrderQuery();
		query.setSequenceNumber(contract.getSequenceNumber());
		query.setVersionId(contract.getVersionId());
		query.setLast(false);
		query.setIncludeDetail(true);
		PageHelper page = orderService.findOrders(query);
		List rows = page.getRows();
		if (rows != null && rows.size() > 0) {
			Object data = rows.get(0);
			order = mapper.convertValue(data, BaseOrder.class);
		}

		List<BaseItem> allList = order.getItems();
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
			params.put("customerName", order.getContracterName());
			params.put("companyAddress", contract.getPartyaAddress());
			params.put("agentName", contract.getBroker());
			params.put("companyPhone", contract.getCompanyTel());
			params.put("bankName", contract.getBankName());
			params.put("bankAccount", contract.getAccountNumber());
			params.put("companyZipcode", contract.getInvoicePostCode());
			paraTable2.put("customerName", order.getContracterName());
			paraTable2.put("companyAddress", contract.getPartyaAddress());
			paraTable2.put("agentName", contract.getBroker());
			paraTable2.put("companyPhone", contract.getCompanyTel());
			paraTable2.put("bankName", contract.getBankName());
			paraTable2.put("bankAccount", contract.getAccountNumber());
			// 公司邮编
			paraTable2.put("companyZipcode", contract.getInvoicePostCode());
		}

		if (allList != null && allList.size() > 0) {
			for (BaseItem item : allList) {
				String itemName = "";
				if ("BG1P7E00000".equals(item.getMaterialCode())) {
					// 产品实卖金额
					Double saleAmount = new Double(0.0D);
					if (item.getActuralPrice() != 0) {
						saleAmount = item.getActuralPrice() * item.getQuantity();
					}
					transferPrice = Double.valueOf(transferPrice.doubleValue() + saleAmount.doubleValue());
					continue;
				}
				String name = item.getGroupCode();
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
				if (item.getActuralPrice() != 0) {
					saleAmount = item.getActuralPrice() * item.getQuantity();
				}
				Double B2cAmount = new Double(0.0D);
				// B2C预估金额
				if (item.getB2CPriceEstimated() != 0) {
					B2cAmount = item.getB2CPriceEstimated() * item.getQuantity();
				}
				b2cSum = Double.valueOf(b2cSum.doubleValue() + B2cAmount.doubleValue());
				Double optionSaleAmount = new Double(0.0D);
				// 可选项实卖金额
				if (item.getActuralPricaOfOptional() != 0) {
					optionSaleAmount = item.getActuralPricaOfOptional() * item.getQuantity();
				}
				optionSum = Double.valueOf(optionSum.doubleValue() + optionSaleAmount.doubleValue());
				// 计量单位
//				MeasurementUnit measurementUnit = this.measurementUnitService.findByMsehi(item.getUnitName());
				String unitNameCN = "";
//				if (measurementUnit != null) {
//					unitNameCN = measurementUnit.getMsehl();
//				}
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
		params.put("deliveryIndays", "" + contract.getDeliveryDaysAfterPrepay());
		// 接货方式-盖章接货
		params.put("receiveWaysSeal", XwpfUtil.map.get(contract.getReceiveTermsCode().equals("2")));
		// 接货方式-盖章接货
		params.put("receiveWaysSign", XwpfUtil.map.get(contract.getReceiveTermsCode().equals("1")));
		// 接货方式-其他
		params.put("receiveWaysOther", XwpfUtil.map.get(contract.getReceiveTermsCode().equals("0")));
		// 接货人1姓名
		params.put("receiverUser1", contract.getContractor1Tel());
		// 接货人1身份证号
		params.put("receiverIdcard1", contract.getContractor1Id());
		// 终端客户名称
		params.put("terminalCustname", contract.getClientName());
		// 实际安装地点
		params.put("installPlace", contract.getInstallLocation());

		// 质量标准-其他
		params.put("qualityStandardOther", XwpfUtil.map.get(contract.getQualityStand().equals("")));
		// 质量标准-自安自保
		params.put("qualityStandardSelfHode", XwpfUtil.map.get(!contract.getQualityStand().equals("")));
		// 验收标准（方法）-需方负责安装调试
		params.put("acceptanceStandardBuyer", XwpfUtil.map.get(contract.getAcceptanceCriteriaCode().equals("1001")));
		// 验收标准（方法）-供方负责安装调试
		params.put("acceptanceStandardSupplier", XwpfUtil.map.get(contract.getAcceptanceCriteriaCode().equals("1002")));
		// 结算方式
		params.put("accountWay", contract.getSettlement());

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

		xwpfUtil.replaceInPara(doc, params);
		xwpfUtil.exportTable1(doc, detailList, paramSum, 0, Boolean.valueOf(false));
		xwpfUtil.updateValueToTable(doc, paraTable2, 1);

		File path = new File(contractDir);
		File wordFile = new File(path, fileName);
		File pdfFile = new File(path, pdfFileName);
		if (!path.exists()) {
			path.mkdirs();
		}
		FileOutputStream vos = new FileOutputStream(wordFile);
		doc.write(vos);

		xwpfUtil.close(vos);
		xwpfUtil.close(is);

//		xwpfUtil.transferToPDF(new FileInputStream(wordFile), new FileOutputStream(pdfFile));
		
		Word2PdfUtil.word2pdf(wordFile, pdfFile);

//		wordFile.delete();

		return pdfFile;
	}


}
