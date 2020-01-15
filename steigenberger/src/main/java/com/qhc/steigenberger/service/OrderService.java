/**
 * 
 */
package com.qhc.steigenberger.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.Attachment;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.Material;
import com.qhc.steigenberger.domain.Order;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.OrderVersion;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.util.PageHelper;

/**
 * @author wang@dxc.com
 *
 */
@Service
public class OrderService {
	private ObjectMapper mapper = new ObjectMapper()
			.setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM))
			// 忽略不存在的字段
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@Value("${qhc.order.path}")
	private String orderFilePath;

	@Autowired
	private FryeService fryeService;

	@Autowired
	FryeService dOrderervice;

	public Order getOrderBySequenceNumber(String sqNumber) {
		return null;
	}

	public Result getOrderOption() {
		
		Result result = fryeService.getInfo("order/option", Result.class);
		if ("ok".equals(result.getStatus())) {
			JavaType javaType = mapper.getTypeFactory().constructType(OrderOption.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}
		return result;
	}

	/**
	 * 
	 * @param name customer name
	 * @return customer list
	 */
	public Result findCustomer(String clazzCode, String name, int pageNo, int pageSize) {
		Result result = (Result) fryeService
				.getInfo("customer/" + clazzCode + "," + name + "," + pageNo + "," + pageSize, Result.class);
		if ("ok".equals(result.getStatus())) {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(PageHelper.class, Customer.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}
		return result;
	}

	public Result findMaterialsByName(String name, String industoryCode, int pageNo, int pageSize) {
		Map<String, String> pars = new HashMap<String, String>();
		pars.put("name", name);
		pars.put("industryCode", industoryCode);
		pars.put("pageNo", String.valueOf(pageNo));
		pars.put("pageSize", String.valueOf(pageSize));
		Result result = (Result) fryeService.postForm("material", pars, Result.class);
		if ("ok".equals(result.getStatus())) {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(PageHelper.class, Material.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}
		return result;
	}

	public Material getMaterial(String code) {
		return (Material) fryeService.getInfo("material/" + code, Material.class);
	}

	public List<OrderVersion> findOrderVersions(String sequenceNumber) {
		String url = "order/" + sequenceNumber + "/version";
		return (List<OrderVersion>) fryeService.getInfo(url, List.class);
	}

	public Result findOrders(OrderQuery query) {
		String url = "order/query";
		Result result = fryeService.postInfo(query, url, Result.class);
		if ("ok".equals(result.getStatus())) {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(PageHelper.class, Order.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}
		return result;
	}

	public Result findOrderDetail(Integer orderInfoId) {
		String url = "order/" + orderInfoId;
		Result result = fryeService.getInfo(url, Result.class);
		if ("ok".equals(result.getStatus())) {
			JavaType javaType = mapper.getTypeFactory().constructType(Order.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}

		return result;
	}

	/**
	 * 
	 * @param form : order
	 */
	public Result saveOrder(String user, Order order) {
		Result result = fryeService.postForm("order/" + user, order, Result.class);

		return result;
	}

	/**
	 * 
	 * @param form : order
	 */
	public Result submitOrder(String user, Order order) {
		String url = "order/submit/" + user;
		Result result = fryeService.postForm(url, order, Result.class);

		return result;
	}

	/**
	 * 
	 * @param form : order
	 */
	public Result submitbpmOrder(String user, Order order) {
		String url = "order/submitbpm/" + user;
		Result result = fryeService.postForm(url, order, Result.class);

		return result;
	}

	/**
	 * 
	 * @param form : order
	 */
	public Result upgradeOrder(String user, Integer orderInfoId) {
		String url = "order/" + orderInfoId + "/upgrade/" + user;
		Result result = fryeService.postForm(url, (Object) new HashMap(), Result.class);

		return result;
	}

	public Result sendToSap(String user, Integer orderInfoId) {
		String url = "order/" + orderInfoId + "/sap/" + user;
		Result result = (Result) fryeService.postForm(url, "", Result.class);

		return result;
	}

	public Result getCharactersByClazzCode(String clazzCode, String materialCode) {
		String url = "material/configurations/" + clazzCode + ',' + materialCode;
		Result result =  fryeService.getInfo(url, Result.class);
		if ("ok".equals(result.getStatus())) {
			JavaType javaType = mapper.getTypeFactory().constructType(List.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}
		return result;
	}

	public Result findBomPrice(Map<String, String> pars) {
		return (Result) fryeService.postInfo(pars, "material/optionalbom", Result.class);
	}

	public Object calcGrossProfit(Order order) {
		String url = "order/grossprofit";
		String json = fryeService.postForm(url, order, String.class);
		try {
			return new ObjectMapper().readTree(json);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 保存附件
	 * 
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public Attachment writeAttachment(String fileName, InputStream inputStream) {
		// 文件存储地址，相对路径，存储目录由配置文件指定
		String fileUrl = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + "/" + System.currentTimeMillis()
				+ "_" + fileName;

		File f = new File(orderFilePath, fileUrl);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}

		try (FileOutputStream fout = new FileOutputStream(f)) {
			IOUtils.copy(inputStream, fout);
			Attachment attachment = new Attachment();
			attachment.setFileName(fileName);
			attachment.setFileUrl(fileUrl);

			return attachment;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

		return null;
	}

	/**
	 * 读取附件文件流
	 * 
	 * @param attachment
	 * @return
	 */
	public InputStream readAttachment(String fileUrl) {
		try {
			// 文件存储地址，相对路径，存储目录由配置文件指定
			File f = new File(orderFilePath, fileUrl);
			
			return new FileInputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 修改订单报价状态
	 * 
	 * @param identity
	 * @param orderInfoId
	 * @param quoteStatus
	 * @return
	 */
	public Result updateQuoteStatus(String identity, Integer orderInfoId, String quoteStatus) {
		String url = "order/quote/" + identity + ',' + orderInfoId + ',' + quoteStatus;
		Result result =  fryeService.getInfo(url, Result.class);
		return result;
	}

}
