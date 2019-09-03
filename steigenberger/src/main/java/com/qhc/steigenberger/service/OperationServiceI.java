package com.qhc.steigenberger.service;

import java.util.List;

import com.qhc.steigenberger.domain.Operations;


public interface OperationServiceI{
	
	/**
	 * 查询列表
	 * @return
	 */
	List<Operations> findAll();
	
}