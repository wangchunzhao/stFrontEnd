/**
 * 
 */
package com.qhc.steigenberger.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.BomExplosion;
import com.qhc.steigenberger.domain.Characteristic;
import com.qhc.steigenberger.domain.Customer;
import com.qhc.steigenberger.domain.Material;
import com.qhc.steigenberger.domain.MaterialGroups;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.OrderQuery;
import com.qhc.steigenberger.domain.OrderVersion;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.form.Order;
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
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

	@Autowired
	private FryeService fryeService;

	@Autowired
	FryeService dOrderervice;

	public Order getOrderBySequenceNumber(String sqNumber) {
		return null;
	}

	public OrderOption getOrderOption() {
		Object obj = fryeService.getInfo("order/option", OrderOption.class);

		return (OrderOption) obj;
	}

	/**
	 * 
	 * @param name customer name
	 * @return customer list
	 */
	public Result findCustomer(String clazzCode, String name, int pageNo, int pageSize) {
		Result result = (Result) fryeService.getInfo("customer/" + clazzCode
				+ "," + name + "," + pageNo+","+pageSize, Result.class);
		if("ok".equals(result.getStatus())){
			JavaType javaType = mapper.getTypeFactory().constructParametricType(PageHelper.class, Customer.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}
		return result;
	}

	public Result findMaterialsByName(String name,String industoryCode, int pageNo,int pageSize) {
		Map<String, String> pars = new HashMap<String, String>();
		pars.put("name", name);
		pars.put("industoryCode", industoryCode);
		pars.put("pageNo", String.valueOf(pageNo));
		pars.put("pageSize", String.valueOf(pageSize));
		Result result = (Result)  fryeService.postForm("material", pars, Result.class);
		if("ok".equals(result.getStatus())) {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(PageHelper.class, Material.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}
		return result;
	}

	public Material getMaterial(String code) {
		return (Material) fryeService.getInfo("material/" + code, Material.class);
	}

	public List<OrderVersion> findOrderVersions(String sequenceNumber) {
		String url = "order/" + sequenceNumber +"/version";
		return (List<OrderVersion>) fryeService.getInfo(url, List.class);
	}

	public Result findOrders(OrderQuery query) {
		String url = "order/query";
		Result result = fryeService.postInfo(query, url, Result.class);
		if("ok".equals(result.getStatus())){
			JavaType javaType = mapper.getTypeFactory().constructParametricType(PageHelper.class, Order.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}
		return result;
	}

	public Result findOrderDetail(Integer orderInfoId) {
		String url = "order/" + orderInfoId;
		Result result = fryeService.getInfo(url, Result.class);
		if("ok".equals(result.getStatus())){
			JavaType javaType = mapper.getTypeFactory().constructType(Order.class);
			result.setData(mapper.convertValue(result.getData(), javaType));
		}

		return result;
	}
	/**
	 * 
	 * @param form : order
	 */
	public Result saveOrder(String user, Order form) {
		Result result = fryeService.postForm("order/" + user, form, Result.class);

		return result;
	}
	/**
	 * 
	 * @param form : order
	 */
	public Result submitOrder(String user, Order form) {
		String url = "order/submit/" + user;
		Result result = fryeService.postForm(url, form, Result.class);

		return result;
	}
	/**
	 * 
	 * @param form : order
	 */
	public Result submitbpmOrder(String user, Order form) {
		String url = "order/submitbpm/" + user;
		Result result = fryeService.postForm(url, form, Result.class);

		return result;
	}
	/**
	 * 
	 * @param form : order
	 */
	public Result upgradeOrder(String user, Integer orderInfoId) {
		String url = "order/" + orderInfoId + "/upgrade/" + user;
		Result result = fryeService.postForm(url, (Object)new HashMap(), Result.class);

		return result;
	}

	public Result sendToSap(String user, Integer orderInfoId) {
		String url = "order/sap/" + user;
		Result result = (Result)fryeService.postForm(url, (Object)new HashMap(), Result.class);

		return result;
	}

	public List<Characteristic> getCharactersByClazzCode(String clazzCode, String materialCode) {
		String url = "material/configurations/" + clazzCode + ',' + materialCode;
		return (List<Characteristic>) fryeService.getInfo(url, List.class);
	}

	public BomExplosion findBOMWithPrice(Map<String, String> pars) {
		return (BomExplosion) fryeService.postInfo(pars, "material/configuration", HashMap.class);
	}

	public List<MaterialGroups> calcGrossProfit(Order order) {
		String url = "order/grossprofit";
		return (List<MaterialGroups>) fryeService.postForm(url, order, ArrayList.class);
	}

	public List<MaterialGroups> calcWtwGrossProfit(String sequenceNumber, String version) {
		String url = "order/" + sequenceNumber + "/" + version + "/wtwgrossprofit";
		return (List<MaterialGroups>) fryeService.postForm(url, "", ArrayList.class);
	}

	public List<MaterialGroups> calcGrossProfit(String sequenceNumber, String version) {
		String url = "order/" + sequenceNumber + "/" + version + "/grossprofit";
		return (List<MaterialGroups>) fryeService.postForm(url, "", ArrayList.class);
	}

}
