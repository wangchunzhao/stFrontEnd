package com.qhc.steigenberger.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.qhc.steigenberger.domain.Parameter;
import com.qhc.steigenberger.service.ParameterService;
import com.qhc.steigenberger.util.CacheConfModel;
import com.qhc.steigenberger.util.CacheUtil;

/**
 * @author lizuoshan
  *   前提条件：所依赖服务(frye)已经运行
 */

@Component
public class SettingsCache implements ApplicationRunner{

	@Autowired
	private ParameterService parameterService;
	@Autowired
	private CacheUtil cacheUtil;
	
	public static Map<String,List<Parameter>> map = new HashMap<String,List<Parameter>>();
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<Parameter> ps = parameterService.getList();
		map.put(parameterService.CATCHE_SETTINGS_NAME, ps);
		cacheUtil.addCache(parameterService.CATCHE_SETTINGS_NAME, ps, new CacheConfModel());
		
	}

	

}
