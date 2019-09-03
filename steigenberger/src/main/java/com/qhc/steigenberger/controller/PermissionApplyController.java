package com.qhc.steigenberger.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.ApplicationOfRolechangeService;
import com.qhc.steigenberger.service.RoleServiceI;
import com.qhc.steigenberger.service.SapSalesOfficeServicel;
import com.qhc.steigenberger.service.UserServiceI;




@Controller
@RequestMapping("/permission")
public class PermissionApplyController extends BaseController{
	
	@Autowired
	RoleServiceI roleServiceImpl;
	
	@Autowired
	UserServiceI userServiceImpl;
	
	@Autowired
	SapSalesOfficeServicel sapSalesOfficeServicel;
	
	@Autowired
	ApplicationOfRolechangeService applicationOfRolechangeServiceImpl;
	
	@RequestMapping("/permissionApply")
	public String permissionApply() {

		return "permission/permissionApply";
	}
	
	@PostMapping("/sapSalesOfficelist")
	@ResponseBody
	public JsonResult roleList() {
		List<SapSalesOffice> role = sapSalesOfficeServicel.findAll();
		return JsonResult.build(200,"success", role);
	}
	
	
	//新增用户
	@PostMapping("/adduser")
	@ResponseBody
	public JsonResult adduser(HttpServletRequest request) {
		//获取表单数据
		String userName = request.getParameter("username");
		String useremil = request.getParameter("useremil");
		String userid = request.getParameter("userid");
		String roleName = request.getParameter("roleName");
		String area = request.getParameter("area");
		String isactive = request.getParameter("status");
		//获取域用户
//		User usersession = getAccount(request);
		
		User user = new User();
		user.setUserMail(useremil);
		user.setIsActive(Integer.parseInt(isactive));
		user.setUserName(userName);
		user.setUserIdentity(userid);
		
		ApplicationOfRolechange applicationOfRolechange = new ApplicationOfRolechange();
		
		
//		System.out.println(usersession.getUserIdentity());
		System.out.println("12345678");
		String msg = "";
		int status = 0;
		Boolean result = addStatus(user,applicationOfRolechange);
//		String result = userServiceImpl.updateUserInfo(user);
		if (result) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "角色" + msg, "");
	}
	
	public Boolean addStatus(User user,ApplicationOfRolechange applicationOfRolechange) {
		Boolean bol = false;
		try {
			String result = userServiceImpl.updateUserInfo(user);
			if (result != null && !"".equals(result)) {
				String addresult = applicationOfRolechangeServiceImpl.add(applicationOfRolechange);
				if (addresult != null && !"".equals(addresult)) {
					bol = true;
				}else {
					bol = false;
				}
			} else {
				bol = false;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return bol;
	}
	
	
}
