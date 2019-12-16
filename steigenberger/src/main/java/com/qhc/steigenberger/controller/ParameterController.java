package com.qhc.steigenberger.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Parameter;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.ParameterService;
import com.qhc.steigenberger.service.UserService;
import com.qhc.steigenberger.util.CacheConfModel;
import com.qhc.steigenberger.util.CacheUtil;


@RequestMapping("parameterSetting")
@Controller
public class ParameterController {
	@Autowired
	ParameterService parameterService;
	@Autowired
	private CacheUtil cacheUtil;
	@Autowired
	private UserService userService;

	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest request) {
		
		List<Parameter> parameters = parameterService.getList();
		model.addAttribute("parameters", parameters);
//		request.getSession().setAttribute("parameterSettings",parameters);
		return "systemManage/parameterSetting";
	}

	
	
	@RequestMapping("/update")
	@ResponseBody
	public JsonResult update( @RequestBody Parameter parameter, HttpServletRequest request) {
		String msg = "";
		int status = 0;
		Parameter result = new Parameter();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		parameter.setOptTime(sdf.format(new Date()));
//		User u = (User) request.getSession().getAttribute(Constants.IDENTITY);
		String identity = (String)request.getSession().getAttribute(Constants.IDENTITY);
		parameter.setOperater(identity);
		try {
			result = parameterService.updateInfo(parameter);
			status = 200;
			msg = "更新操作成功!";
		} catch (Exception e) {
			status = 500;
			msg = "操作失败!";
		}
	
		return JsonResult.build(status, "参数设置" + msg, "");

	}

	@RequestMapping("/toUpdate")
	 public String authorization(Model model,@RequestParam(value="id") Integer id) {
			Parameter parameter = this.parameterService.get(id);
			
		    model.addAttribute("parameter", parameter);
		    return "systemManage/editParameter"; 
	  }

}
