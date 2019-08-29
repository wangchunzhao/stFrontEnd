package com.qhc.steigenberger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qhc.steigenberger.domain.Role;




@Controller
@RequestMapping("/permission")
public class PermissionApplyController {
	

	@RequestMapping("/permissionApply")
	public String roleList(Model model) {
		
	
		return "permission/permissionApply";
	}
	
	
}
