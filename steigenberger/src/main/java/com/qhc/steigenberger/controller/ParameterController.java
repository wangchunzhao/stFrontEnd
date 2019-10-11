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
		request.getSession().setAttribute("parameterSettings",parameters);
		return "systemManage/parameterSetting";
	}

	
	
	@RequestMapping("/update")
	@ResponseBody
	public JsonResult update( @RequestBody Parameter parameter, HttpServletRequest request) {
		String msg = "";
		int status = 0;
		Parameter result = new Parameter();
		Parameter pp = new Parameter();
		pp.setId(0);
		pp.setCode(parameter.getCode());
		pp.setEnableDate(parameter.getEnableDate());
		pp.setsValue(parameter.getsValue());
		pp.setComment(parameter.getComment());
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		pp.setOptTime(sdf.format(new Date()));
		User u = (User) request.getSession().getAttribute(userService.ACCOUNT);
//		parameter.setOperater(u.getUserIdentity());
		pp.setOperater("adsf");
		result = parameterService.updateInfo(pp);
		if (result != null&&result.getId()!=0) {
			status = 200;
			msg = "更新操作成功!";
		} else {
			status = 500;
			msg = "操作失败";
		}
	
		return JsonResult.build(status, "参数设置" + msg, "");

	}

	@RequestMapping("/toUpdate")
	 public String authorization(Model model,@RequestParam(value="id") Integer id,HttpServletRequest request) {
			List<Parameter> parameters = (List<Parameter>) request.getSession().getAttribute("parameterSettings");
			
			Parameter parameter = new Parameter();
			if(parameters!=null&&parameters.size()>0) {
				for(Parameter p :parameters) {
					if(id==p.getId()) {
						parameter = p;
						break;
					}
					
				}
			}
			
		    model.addAttribute("parameter", parameter);
		    return "systemManage/editParameter"; 
	  }

}
