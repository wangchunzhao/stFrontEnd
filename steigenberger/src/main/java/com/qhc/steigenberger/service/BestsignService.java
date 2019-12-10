package com.qhc.steigenberger.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.ContractSignSys;

import cn.bestsign.ultimate.delta.api.client.BestSignClient;
import cn.bestsign.ultimate.delta.api.domain.contract.sign.SignContractVO;
import cn.bestsign.ultimate.delta.api.encrypt.HexUtils;

/**
 * 
 * 上上签接口服务
 * 
 * @author Walker
 *
 */
@Service
public class BestsignService {
	private static Logger logger = LoggerFactory.getLogger(BestsignService.class);

	private ObjectMapper mapper = new ObjectMapper();

	@Value("${contract.bestsign.privatekey}")
	private String privateKey;
	@Value("${contract.bestsign.url}")
	private String accessURL;
	@Value("${contract.bestsign.clientid}")
	private String clientId;
	@Value("${contract.bestsign.secrete}")
	private String secrete;
	// 图章名称
	@Value("${contract.bestsign.sealname}")
	private String sealName;
	@Value("${contract.bestsign.entername}")
	private String enterName;
	@Value("${contract.bestsign.accountname}")
	private String accountName;

//	@Resource
//	private ContractSignSysRepository contractSignSysRepository;
	// 签约系统合同列表，缓存，模拟数据库表Repository
	private List<ContractSignSys> signList = new ArrayList<ContractSignSys>();

	/**
	 * 
	 * 下载合同文档
	 * 
	 * @param contractId 电子签约中合同Id
	 * @return 电子签约中合同文件字节数组
	 */
	public byte[] downloadContract(String contractId) {
		SignContractVO signContractVO = new SignContractVO();
		List<Long> contractIds = Arrays.asList(new Long[] { Long.valueOf(Long.valueOf(contractId).longValue()) });
		signContractVO.setContractIds(contractIds);
		String base64 = getBestSignClient().executeRequest("/api/contracts/download-file", "POST", signContractVO);
		byte[] bytes = Base64.getDecoder().decode(base64);
		return bytes;
	}

	/**
	 * 
	 * 签署合同
	 * 
	 * @param contractId 电子签约中合同Id
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public boolean doSignContract(String contractId) throws JsonMappingException, JsonProcessingException {
		SignContractVO signContractVO = new SignContractVO();
		signContractVO.setSealName(this.sealName);
		List<Long> contractIds = Arrays.asList(new Long[] { Long.valueOf(Long.valueOf(contractId).longValue()) });
		signContractVO.setContractIds(contractIds);
		
		String result = getBestSignClient().executeRequest("/api/contracts/sign", "POST", signContractVO);
		logger.debug("Auto sign info:" + result);
		Map resultMap = (Map) mapper.readValue(result, HashMap.class);
		if (resultMap.get("code").toString().equals("0")) {
	//		String data = resultMap.get("data").toString();
			// data":{"totalAmount":1,"successAmount":0,"failureAmount":1,"errorInfos":["[自动签署-出现异常：contract must not be null]"]}
	//		Map dataMap = (Map) mapper.readValue(data, HashMap.class);
			Map dataMap = (Map) resultMap.get("data");
			if (dataMap.get("successAmount").equals(dataMap.get("totalAmount")) ) {
	//				&& (dataMap.get("errorInfos") == null || dataMap.get("errorInfos").toString().equalsIgnoreCase("[]"))) {
				return true;
			}
			
			logger.error("Auto sign : {}", dataMap.get("errorInfos"));
		}
		return false;
	}

	/**
	 * 
	 * 同步合同状态
	 * 
	 * @return 上上签中所有未删除的电子签约合同
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public List<ContractSignSys> syncContractSignSysData() throws JsonMappingException, JsonProcessingException {
		Map<String, Object> paramsMap = new HashMap<>();
		Map<String, String> parameters = new HashMap<>();
		parameters.put("account", this.accountName);
		parameters.put("enterpriseName", this.enterName);
		paramsMap.put("operator", parameters);
		paramsMap.put("pageIndex", 1);
		paramsMap.put("pageSize", 50);
		
		List<String> list = new ArrayList<String>();
		
		boolean hasmore = false;
		do {
			String result = getBestSignClient().executeRequest("/api/contracts/search", "post", paramsMap);
			logger.debug("Search result:" + result);
			Map resultMap = (Map) mapper.readValue(result, HashMap.class);
			if (resultMap.get("code").equals("0")) {
				Map data = (Map)resultMap.get("data");
				Integer pageIndex = Integer.valueOf(String.valueOf(data.get("pageIndex")));
				Integer totalPages = Integer.valueOf(String.valueOf(data.get("totalPages")));
				hasmore = pageIndex < totalPages;
				List<Map> results = (List<Map>)data.get("results");
				if (results != null && results.size() > 0) {
					for (Map map : results) {
						list.add((String)map.get("contractId"));
					}
				}
			}
			paramsMap.put("pageIndex", (Integer)paramsMap.get("pageIndex") + 1);
		} while (hasmore);
		
		if (list.isEmpty()) {
			return new ArrayList<ContractSignSys>();
		}

		// 从数据库查询删除标志为0的合同签署信息，现有系统用缓存代替
//		List<ContractSignSys> signList = this.contractSignSysRepository.findContractByIsDelete("0");
		for (int i = list.size() - 1; i >= 0; i--) {
			String contractId = list.get(i);

			boolean flag = false;
			for (ContractSignSys one : signList) {
				if (one.getSignContractId().equalsIgnoreCase(contractId)) {
					one.setCurHave(Boolean.valueOf(true));
					flag = true;
					break;
				}
			}
			// 如果不存在则添加新的对象
			if (!flag) {
				ContractSignSys newOne = getNewContractSignSys(contractId);
				if (newOne != null)
					signList.add(newOne);
			}
		}

		// 过滤删除状态为1的合同签署信息
		List<ContractSignSys> resultList = new ArrayList<ContractSignSys>();
		for (ContractSignSys one : signList) {
			if (one.getCurHave() == null || !one.getCurHave().booleanValue()) {
				one.setIsDelete("1");
				continue;
			}
			resultList.add(one);
		}

//		this.contractSignSysRepository.save(signList);
		// 模拟保存，过滤掉isdelete为1的数据
		signList = resultList;
		// 初始化CurHave为false
		for (ContractSignSys one : signList) {
			one.setCurHave(false);
		}

		return resultList;
	}

	/**
	 * 
	 * 获取电子签署合同的状态
	 * 
	 * @param contractId 电子签约中合同Id
	 * @param agentName 客户名称
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public String getContractStatus(String contractId, String agentName)
			throws JsonMappingException, JsonProcessingException {
		String status = "03";
		Map contractMap = getContractInfo(contractId);
		if (contractMap == null) {
			return status;
		}
		String vAgentName = agentName;
//		String receivers = contractMap.get("receivers").toString();
//		List<Map> receiversMap = (List<Map>) mapper.readValue(receivers, ArrayList.class);
		List<Map> receiversMap = (List<Map>) contractMap.get("receivers");
		boolean flag1 = false, flag2 = false;
		for (Map receiver : receiversMap) {
			String enterpriseName = receiver.get("enterpriseName").toString();
			if ((enterpriseName.indexOf(vAgentName) >= 0 || vAgentName.indexOf(enterpriseName) >= 0)
					&& receiver.get("receiverType") != null
					&& receiver.get("receiverType").toString().equalsIgnoreCase("SIGNER")) {
				if (receiver.get("signStatus").toString().equalsIgnoreCase("COMPLETE"))
					flag1 = true;
			}
			if ((enterpriseName.indexOf(this.enterName) >= 0 || this.enterName.indexOf(enterpriseName) >= 0)
					&& receiver.get("receiverType") != null
					&& receiver.get("receiverType").toString().equalsIgnoreCase("SIGNER")) {
				if (receiver.get("signStatus").toString().equalsIgnoreCase("COMPLETE"))
					flag2 = true;
			}
		}
		if (flag1) {
			status = "05";
			if (flag2) {
				status = "06";
			}
		} else {
			status = "04";
		}
		return status;
	}

	/**
	 * 
	 * 为合同文件生成SHA1密文
	 * 
	 * @param file 合同文件(PDF)
	 * @return SHA1密文
	 */
	public String generateShA1Code(File file) {
		String code = "";
		try {
			if (file.exists())
				code = sha1(file);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * 
	 * 查询合同详情接口
	 * 
	 * @param contractId 电子签约中合同Id
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public Map getContractInfo(String contractId) {
		String accountName = this.accountName;
		String enterpriseName = this.enterName;
		try {
			accountName = URLEncoder.encode(accountName, "UTF-8");
			enterpriseName = URLEncoder.encode(enterpriseName, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String url = "/api/contracts/" + contractId + "?account=" + accountName + "&enterpriseName=" + enterpriseName;
//		url = "/api/contracts/" + contractId;
		String result = getBestSignClient().executeRequest(
				 url,
				"get", null);
		logger.info("getContractInfo(" + contractId + ") : " + result);

		Map maps = null;
		try {
			maps = (Map) mapper.readValue(result, HashMap.class);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
		if (maps.get("data") == null) {
			return null;
		}
//		String item = maps.get("data").toString();
//		Map itemMap = (Map) mapper.readValue(item, HashMap.class);
		Map itemMap = (Map)maps.get("data");
		return itemMap;
	}

	/**
	 * 
	 * 合同详情中是否包含SHA1 hash值的合同文件
	 * 
	 * @param contractId  电子签约中合同Id
	 * @param fileSha1Code 合同文件的SHA1 hash值
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public boolean containContractFileSha(String contractId, String fileSha1Code) {
		boolean flag = false;
		Map itemMap = getContractInfo(contractId);
		if (itemMap == null || itemMap.isEmpty()) {
			return flag;
		}
//		String docArray = itemMap.get("documents").toString();
//		List<Map> docmaps = (List<Map>) mapper.readValue(docArray, ArrayList.class);
		List<Map> docmaps = (List<Map>) itemMap.get("documents");
		for (Map doc : docmaps) {
			if (doc.get("sourceFileSHA1Hash") == null)
				continue;
			String filesha = doc.get("sourceFileSHA1Hash").toString();
			if (filesha.equalsIgnoreCase(fileSha1Code)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 
	 * 生成新的电子合同签署记录
	 * 
	 * @param contractId
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	private ContractSignSys getNewContractSignSys(String contractId)
			throws JsonMappingException, JsonProcessingException {
		Map itemMap = getContractInfo(contractId);
		if (itemMap == null || itemMap.isEmpty()) {
			return null;
		}
		ContractSignSys newOne = new ContractSignSys();
		newOne.setSignContractId(contractId);
		newOne.setIsDelete("0");
		newOne.setCreateDate(new Timestamp(System.currentTimeMillis()));
		newOne.setCurHave(Boolean.valueOf(true));

//		String docArray = itemMap.get("documents").toString();
//		List<Map> docmaps = (List<Map>) mapper.readValue(docArray, ArrayList.class);
		List<Map> docmaps = (List<Map>)itemMap.get("documents");
		for (Map doc : docmaps) {
			if (doc.get("sourceFileSHA1Hash") == null)
				continue;
			String filesha = doc.get("sourceFileSHA1Hash").toString();
			newOne.setFileHashCode(filesha);
		}
		return newOne;
	}

	private BestSignClient getBestSignClient() {
		return new BestSignClient(this.accessURL, this.clientId, this.secrete, this.privateKey);
	}

	private static String sha1(File file) throws IOException, NoSuchAlgorithmException {
		MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
		messagedigest.update(byteBuffer);
		return HexUtils.byteArrayToHex(messagedigest.digest());
	}
}
