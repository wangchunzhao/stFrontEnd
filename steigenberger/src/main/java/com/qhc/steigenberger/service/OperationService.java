package com.qhc.steigenberger.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qhc.steigenberger.domain.MenusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.Operations;

@Service
public class OperationService{
	
	@Autowired
	FryeService fryeService;
	
	/**
	 * 查询所有菜单
	 * 
	 * @return
	 */
	public List<Operations> getList() {
		return fryeService.getListInfo("operations", Operations.class);
	}
	
	/**
	 * 查询用户所有权限
	 * 
	 * @return
	 */
	public List<Operations> findOperations(String user) {
		// TODO 缺少后端接口
		return fryeService.getListInfo("operations/" + user, Operations.class);
	}
	
	/**
	 * 查询用户所有所有菜单
	 * 
	 * @return
	 */
	/**
	 * 查询用户所有所有菜单
	 *
	 * @return
	 */
	public Map<String, MenusDto> findMenus(String user) {
		//List<MenusDto> list = getList();
		Map<String, MenusDto> menus = fryeService.findMenus("queryAllAserMenus/" + user);
		//Map<String, MenusDto> menus = null;
		return menus;
	}
}

