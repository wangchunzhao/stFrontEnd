package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.Settings;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

/**
 * 配置service
 * @author lizuoshan
 *
 */
@Service
public class SettingsService extends WebServcieTool<Settings>{

	/**
	 * 查询全部设置
	 * @return
	 */
	public List<Settings> findAllInfo(){
		return findAll(CommonConstant.BASEURL, CommonConstant.URL_SETTINGS_FINDALL, Settings.class);
	}
	
	/**
	 *  修改配置信息
	 * @param settings
	 * @return
	 */
	public String updateInfo(Settings settings) {
		return postInfo(settings, CommonConstant.URL_SETTINGS_UPDATE, Settings.class);
	}
}
