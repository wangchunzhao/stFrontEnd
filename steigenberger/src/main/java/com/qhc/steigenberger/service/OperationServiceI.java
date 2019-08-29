package com.qhc.steigenberger.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.Operations;
import com.qhc.steigenberger.domain.Role;


public interface OperationServiceI{
	List<Operations> findAll();
}