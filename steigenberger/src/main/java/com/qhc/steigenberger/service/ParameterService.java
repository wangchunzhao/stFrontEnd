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
	FryeService<Parameter> fryeService;
	
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
	 *  修改配置信息
	 * @param Parameter
	 * @return
	 */
	public Parameter updateInfo(Parameter Parameter) {
		Parameter p = null;
		try {
			p =fryeService.postInfo(Parameter, URL_SETTINGS, Parameter.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
}
