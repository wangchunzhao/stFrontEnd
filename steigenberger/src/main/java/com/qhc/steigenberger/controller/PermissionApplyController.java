package com.qhc.steigenberger.controller;

import java.sql.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.ApplicationOfRolechangeService;
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
	
	@Autowired
	ApplicationOfRolechangeService applicationOfRolechangeService;
	
	@RequestMapping("/permissionApply")
	public String permissionApply() {

		return "permission/permissionApply";
	}
	
	@PostMapping("/sapSalesOfficelist")
	@ResponseBody
	public JsonResult roleList() {
		List<SapSalesOffice> role = sapSalesOfficeService.getList();
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
		String roleId = request.getParameter("roleName");
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
		applicationOfRolechange.setCreator("admin");
		applicationOfRolechange.setCreateTime(new Date(System.currentTimeMillis()));
		applicationOfRolechange.setApprovalTime(new Date(System.currentTimeMillis()));
		applicationOfRolechange.setApproverFact("admin");
		applicationOfRolechange.setApproverRequired("admin");
		applicationOfRolechange.setAttachedCode(area);
		applicationOfRolechange.setIsactive(0);
		applicationOfRolechange.setbRolesId(Integer.valueOf(roleId));
		
//		System.out.println(usersession.getUserIdentity());
		System.out.println("12345678");
		String msg = "";
		int status = 0;
		Boolean result = addStatus(user,applicationOfRolechange);
//		String result = userService.updateUserInfo(user);
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
			User result = userService.add(user);
			if (result != null && !"".equals(result)) {
				applicationOfRolechange.setbUsersId(result.getId());
				
				ApplicationOfRolechange addresult = applicationOfRolechangeService.add(applicationOfRolechange);
				if (addresult != null) {
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
