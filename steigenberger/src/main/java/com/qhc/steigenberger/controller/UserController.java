package com.qhc.steigenberger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.SalesOffice;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.RoleService;
import com.qhc.steigenberger.service.SapSalesOfficeService;
import com.qhc.steigenberger.service.UserService;

@RequestMapping("user")
@Controller
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	SapSalesOfficeService sapSalesOfficeService;

	@RequestMapping("/index")
	public String index(@RequestParam(defaultValue = "0", name = "page") Integer page,
			@RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize,
			User entity, 
			Model model,
			HttpServletRequest request) {
		
		model.addAttribute("user1", entity);
		//result list
		model.addAttribute("datas", userService.selectAndPage(page, pageSize, entity));
		String userMail = entity.getUserMail()==null?"":entity.getUserMail();
		String userIdentity = entity.getUserIdentity()==null?"":entity.getUserIdentity();
		String pp = "/user/index?isActive="+entity.getIsActive()+"&userMail="+userMail+"&userIdentity="+userIdentity;
		model.addAttribute("currentPath", pp);
		
		return "systemManage/userManage";
	}

	
	@PostMapping("/update")
	@ResponseBody
	public JsonResult update( @RequestBody User user, HttpServletRequest request) {
		
		String msg = "";
		int status = 0;
		ApplicationOfRolechange app = new ApplicationOfRolechange();
		String creator = (String) request.getSession().getAttribute(Constants.IDENTITY);
		app.setCreator(creator);
		List<ApplicationOfRolechange> list = new ArrayList<ApplicationOfRolechange>();
		list.add(app);
		user.setApps(list);
		User result = userService.updateUserInfo(user);
		if (result != null) {
			status = 200;
			msg = "更新操作成功!";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "角色" + msg, result);

	}

	@RequestMapping("/getOperations")
	@ResponseBody
	public JsonResult getOperations(HttpServletRequest request) {
		String userIdentity = (String)request.getSession().getAttribute(Constants.IDENTITY);
		String msg = "";
		int status = 0;
		User result = userService.selectUserIdentity(userIdentity);
		result.setUserName(result.getName());
		if (result.getOperations() != null&&result.getOperations().size()>0) {
			status = 200;
			msg = "查询成功!";
		} else {
			status = 500;
			msg = "查询失败";
		}
		return JsonResult.build(status,msg,result);

	}
	
	
	 @RequestMapping("/toUpdate")
	public String toUpdate(Model model, String userIdentity) {
		List<SalesOffice> officeList = sapSalesOfficeService.getList();
		Map<String, Object> map = userService.findInfos(userIdentity);
		model.addAttribute("map", map);
		model.addAttribute("officeList", officeList);
		return "systemManage/editUser";
	}
}
