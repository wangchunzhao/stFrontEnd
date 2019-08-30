package com.qhc.steigenberger.service;

import java.util.List;

import com.qhc.steigenberger.domain.Settings;

/**
 * 配置service
 * @author lizuoshan
 *
 */
public interface IsettingsService {

	/**
	 * 查询全部设置
	 * @return
	 */
	List<Settings> findAllInfo();
	
	/**
	 *  修改配置信息
	 * @param settings
	 * @return
	 */
	String updateInfo(Settings settings);
}
