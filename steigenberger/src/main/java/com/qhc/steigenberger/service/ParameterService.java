package com.qhc.steigenberger.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.Parameter;

/**
 * 配置service
 * @author lizuoshan
 *
 */
@Service
public class ParameterService{
	
	@Autowired
	FryeService fryeService;
	
	private final static String URL_SETTINGS = "parameterSettings";
	public final static String CATCHE_SETTINGS_NAME = "catch_settings_name";

	
	/**
	 * 查询全部设置
	 * @return
	 */
	public List<Parameter> getList() {
		List<Parameter> list = null;
		try {
			list =fryeService.getListInfo(URL_SETTINGS,Parameter.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询全部设置
	 * @return
	 */
	public Parameter get(Integer id) {
		Parameter p = null;
		try {
			p =fryeService.getInfo("parameterSettings/" + id, Parameter.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	/**
	 *  修改配置信息
	 * @param Parameter
	 * @return
	 */
	public Parameter updateInfo(Parameter Parameter) {
		Parameter p = null;
		p =fryeService.postInfo(Parameter, URL_SETTINGS, Parameter.class);
		return p;
	}
}
