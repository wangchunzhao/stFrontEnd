package com.qhc.steigenberger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class RoleController {

	public String roleList() {
		
		return "/systemManage/roleList";
	}
}
