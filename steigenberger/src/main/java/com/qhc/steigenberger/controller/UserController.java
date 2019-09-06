package com.qhc.steigenberger.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.SapSalesOffice;
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
	public String index(@RequestParam(defaultValue = "1",name="number") Integer number,
			@RequestParam(defaultValue = "5",name="pageSize") Integer pageSize, 
			User entity, 
			Model model, 
			HttpServletRequest request) {
		
		model.addAttribute("user1", entity);
		
		//result list
//		RestPageUser pageInfo = userService.selectAndPage(number, pageSize, entity);
		List<User> list = userService.getList(entity);
		PageInfo<User> pageInfo=new PageInfo<>(list);
		model.addAttribute("pageInfo", pageInfo);
		
		//roles list
		List<Role> roleList = roleService.getListInfo(new Role());
		model.addAttribute("roleList", roleList);
		return "systemManage/userManage";
	}

	@PostMapping("/add")
	@ResponseBody
	public JsonResult add(@RequestParam(defaultValue = "0",name="one") Integer one,
			@RequestBody User user,
			HttpServletRequest request) {
		
		if(one==1) {
            request.getSession().removeAttribute("entity");
	       }	
		// 判断是否有ID ,
		// 1.没有就是新增操作
		// 2.如果存在，就是更新操作
		String msg = "";
		int status = 0;
		User result = userService.updateUserInfo(user);
		if (result != null) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "角色" + msg, "");

	}
	
	@PostMapping("/update")
	@ResponseBody
	public JsonResult update( @RequestBody User user, HttpServletRequest request) {
		
		String msg = "";
		int status = 0;
		User result = userService.updateUserInfo(user);
		if (result != null) {
			status = 200;
			msg = "更新操作成功!";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "角色" + msg, "");

	}

	@RequestMapping("/getOperations")
	@ResponseBody
	public JsonResult getOperations(HttpServletRequest request) {
		String userIdentity = (String)request.getSession().getAttribute(userService.SESSION_USERIDENTITY);
		String msg = "";
		int status = 0;
		User result = userService.selectUserIdentity(userIdentity);
		if (result.getOperationNames() != null) {
			status = 200;
			msg = "查询成功!";
		} else {
			status = 500;
			msg = "查询失败";
		}
		return JsonResult.build(status,msg, result.getOperationNames());

	}
	
	
	 @RequestMapping("/toUpdate")
	public String toUpdate(Model model, String userIdentity) {
		List<SapSalesOffice> officeList = sapSalesOfficeService.getList();
		Map<String, Object> map = userService.findInfos(userIdentity);
		model.addAttribute("map", map);
		model.addAttribute("officeList", officeList);
		return "systemManage/editUser";
	}
}
