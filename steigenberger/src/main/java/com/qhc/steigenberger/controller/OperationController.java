package com.qhc.steigenberger.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.service.OperationService;


@Controller
@RequestMapping("operation")
public class OperationController {
	
	@Autowired
	OperationService operationService;
	public static String BASE_URL = "http://127.0.0.1:8801/frye/";
	public static String URL = "role/roleList/";
	

	
	@RequestMapping("/findAll")
	@ResponseBody
	public Map<String,Object> findAll(){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("list", operationService.findAll());
		return m;
	}
	
}
