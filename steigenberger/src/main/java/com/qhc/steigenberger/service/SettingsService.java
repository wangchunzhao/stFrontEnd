package com.qhc.steigenberger.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.Settings;

/**
 * 配置service
 * @author lizuoshan
 *
 */
@Service
public class SettingsService{
	
	@Autowired
	FryeService<Settings> fryeService;
	
	private final static String URL_SETTINGS = "settings";

	
	/**
	 * 查询全部设置
	 * @return
	 */
	public List<Settings> getList() {
		return  fryeService.getListInfo(URL_SETTINGS,Settings.class);
	}

	
	
	/**
	 *  修改配置信息
	 * @param settings
	 * @return
	 */
	public Settings updateInfo(Settings settings) {
		return fryeService.postInfo(settings, URL_SETTINGS, Settings.class);
	}
}
