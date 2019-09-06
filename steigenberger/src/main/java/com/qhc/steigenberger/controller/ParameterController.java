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
		List<Parameter> parameters = (List<Parameter>) cacheUtil.getInstance().getValue(parameterService.CATCHE_SETTINGS_NAME);
		if(parameters!=null) {
			
		}else {
			parameters = parameterService.getList();
		}
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
		List<Parameter> parameters = (List<Parameter>) cacheUtil.getInstance().getValue(parameterService.CATCHE_SETTINGS_NAME);
		boolean flag = true;
		if(parameters!=null) {
			for(Parameter p :parameters) {
				if(parameter.getId().equals(p.getId())
						&&parameter.getCode().equals(p.getCode())
						&&parameter.getsValue().equals(p.getsValue())
						&&parameter.getEnableDate().equals(p.getEnableDate())
						) {
					flag = false;
					break;
				}
				
			}
		}
		if(flag) {
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			parameter.setOptTime(sdf.format(new Date()));
			User u = (User) request.getSession().getAttribute(userService.ACCOUNT);
			parameter.setOperater(u.getUserIdentity());
			result = parameterService.updateInfo(parameter);
			if (result != null&&result.getId()!=0) {
				status = 200;
				msg = "更新操作成功!";
				//更新缓存
				cacheUtil.getInstance().removeCache(parameterService.CATCHE_SETTINGS_NAME);
				cacheUtil.getInstance().addCache(parameterService.CATCHE_SETTINGS_NAME, parameterService.getList(), new CacheConfModel());
			} else {
				status = 500;
				msg = "操作失败";
			}
		}else {
			status =403;
			msg = "无效,参数未变更！";
			result = parameter;
		}
		return JsonResult.build(status, "参数设置" + msg, result);

	}

	@RequestMapping("/toUpdate")
	 public String authorization(Model model,int id,HttpServletRequest request) {
			List<Parameter> parameters = (List<Parameter>) request.getSession().getAttribute("parameterSettings");
			
			Parameter parameter = new Parameter();
			if(parameters!=null) {
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
