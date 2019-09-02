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
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.RoleServiceI;
import com.qhc.steigenberger.service.UserServiceI;




@Controller
@RequestMapping("/permission")
public class PermissionApplyController extends BaseController{
	
	@Autowired
	RoleServiceI roleServiceImpl;
	
	@Autowired
	UserServiceI userServiceImpl;

	public static String BASE_URL = "http://127.0.0.1:8801/frye/";
	
	
	@RequestMapping("/permissionApply")
	public String roleList(Model model) {
		
//		List<Role> role = roleServiceImpl.findAll();
	 
//		System.out.println(role.size()+"aaa12345");
		return "permission/permissionApply";
	}
	
	@PostMapping("/adduser")
	@ResponseBody
	public JsonResult adduser(HttpServletRequest request) {
		String userName = request.getParameter("username");
		String useremil = request.getParameter("useremil");
		String userid = request.getParameter("userid");
		String roleName = request.getParameter("roleName");
		String area = request.getParameter("area");
		String isactive = request.getParameter("status");
		
		User usersession = getAccount(request);
		
		User user = new User();
		user.setUserMail(useremil);
		user.setIsActive(1);
		user.setUserName(userName);
		user.setUserIdentity(usersession.getUserIdentity());
		
		System.out.println(usersession.getUserIdentity());
		System.out.println("12345678");
		String msg = "";
		int status = 0;
		String result = "";
		if (result != null && !"".equals(result)) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "角色" + msg, "");
	}
	
	
}
