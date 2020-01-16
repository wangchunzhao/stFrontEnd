package com.qhc.steigenberger.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.SalesOffice;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.RoleService;
import com.qhc.steigenberger.service.SapSalesOfficeService;
import com.qhc.steigenberger.service.UserService;




@Controller
@RequestMapping("/permission")
public class PermissionApplyController extends BaseController{
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SapSalesOfficeService sapSalesOfficeService;
	
	@RequestMapping("/permissionApply")
	public String permissionApply() {

		return "permission/permissionApply";
	}
	
	@PostMapping("/sapSalesOfficelist")
	@ResponseBody
	public JsonResult sapSalesOfficelist() {
		List<SalesOffice> role = sapSalesOfficeService.getList();
		return JsonResult.build(200,"success", role);
	}
	
	@PostMapping("/roleList")
	@ResponseBody
	public JsonResult roleList() {
		Role role = new Role();
		role.setIsActive(1);
		List<Role> rolelist = roleService.getListInfo(role);
		return JsonResult.build(200,"success", rolelist);
	}
	
	
	//新增用户
	@PostMapping("/adduser")
	@ResponseBody
	public JsonResult adduser(HttpServletRequest request) {
		//获取表单数据
		String userName = request.getParameter("username");
		String useremil = request.getParameter("useremil");
		String userid = request.getParameter("userid");
		String roleId = request.getParameter("roleName");
		String area = request.getParameter("area");
		Integer isactive = 1;//全是启用
		String tel = request.getParameter("telnum");
		String creater = this.getUserIdentity();
		String updater = creater;
		//获取域用户
		String msg = "";
		int status = 0;
		User userch = userService.selectUserIdentity(userid);
		if(userch != null) {
			status = 500;
			msg = "该用户已存在";
		}else {
			User user = new User();
			user.setUserName(userName);
			user.setUserMail(useremil);
			user.setIsActive(1);
			user.setName(userName);
			user.setUserIdentity(userid);
			user.setOfficeCode(area);
			user.setCreater(creater);
			user.setUpdater(updater);
			user.setTel(tel);
			List<Role> roleList = new ArrayList<>();
			Role role = new Role();
			role.setId(Integer.parseInt(roleId));
			roleList.add(role);
			user.setRoles(roleList);
			User u = userService.add(user);
			if (u != null && !"".equals(u)) {
				status = 200;
				msg = "操作成功！";
			} else {
				status = 500;
				msg = "保存失败";
			}
		}
		return JsonResult.build(status, msg, "");
	}
	
}
