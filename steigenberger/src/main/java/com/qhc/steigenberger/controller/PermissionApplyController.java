package com.qhc.steigenberger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.service.RoleServiceI;




@Controller
@RequestMapping("/permission")
public class PermissionApplyController {
	
	@Autowired
	RoleServiceI roleServiceImpl;

	public static String BASE_URL = "http://127.0.0.1:8801/frye/";
	

	@RequestMapping("/permissionApply")
	public String roleList(Model model) {
		List<Role> role = roleServiceImpl.findAll();
	 
		System.out.println(role.size()+"aaa12345");
		return "permission/permissionApply";
	}
	
	
}
