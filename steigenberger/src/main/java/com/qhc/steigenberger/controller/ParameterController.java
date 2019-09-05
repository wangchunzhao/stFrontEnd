package com.qhc.steigenberger.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Parameter;
import com.qhc.steigenberger.service.ParameterService;


@RequestMapping("parameterSetting")
@Controller
public class ParameterController {
	@Autowired
	ParameterService parameterService;
	

	@RequestMapping("/index")
	public String index(Model model) {
		
		List<Parameter> parameters = parameterService.getList();
		model.addAttribute("parameters", parameters);
		return "systemManage/userManage";
	}

	
	
	@RequestMapping("/update")
	@ResponseBody
	public JsonResult update( @RequestBody Parameter parameter, HttpServletRequest request) {
		
		String msg = "";
		int status = 0;
		Parameter result = parameterService.updateInfo(parameter);
		if (result != null) {
			status = 200;
			msg = "更新操作成功!";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "参数设置" + msg, "");

	}


}
