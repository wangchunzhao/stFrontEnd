package com.qhc.steigenberger.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.service.OperationServiceI;
import com.qhc.steigenberger.service.RoleServiceI;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	RoleServiceI roleServiceImpl;
	@Autowired
	OperationServiceI operationServiceImpl;


	@RequestMapping("/index")
	public String index(@RequestParam(defaultValue = "0") Integer one,
			@RequestParam(defaultValue = "1", name = "number") Integer number,
			@RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize,
			Role entity, 
			Model model,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		// 有存在session的情况
		if (entity == null && session.getAttribute("entity") != null) {

			if (one == 1) {
				session.removeAttribute("entity");
			} else {
				entity = (Role) session.getAttribute("entity");
			}
		}
		// 有存在带查询条件其session中没有值
		else if (entity != null && session.getAttribute("entity") == null) {
			session.setAttribute("entity", entity);
		}
		// 有存在带查询条件其session中有值
		else if (entity != null && session.getAttribute("entity") != null) {
			session.setAttribute("entity", entity);
		}

		model.addAttribute("pageInfo", roleServiceImpl.selectAndPage(number, pageSize, entity));
		model.addAttribute("operationList", operationServiceImpl.findAll());
		
		return "systemManage/roleManage";
	}

	@PostMapping("/add")
	@ResponseBody
	public JsonResult add(@RequestParam(defaultValue = "0") Integer one, 
			@RequestBody Role role,
			HttpServletRequest request) {
		if(one==1) {
            request.getSession().removeAttribute("entity");
	     }
		// 判断是否有ID ,
		// 1.没有就是新增操作
		// 2.如果存在，就是更新操作
		String msg = "";
		int status = 0;
		String result = roleServiceImpl.saveRoleInfo(role);
		if (result != null && !"".equals(result)) {
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
	public JsonResult update(@RequestBody Role role,HttpServletRequest request) {
		String msg = "";
		int status = 0;
		String result = roleServiceImpl.saveRoleInfo(role);
	
		if (result != null && !"".equals(result)) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "角色" + msg, "");

	}
	
	 @GetMapping("/authorization")
	 public String authorization(Model model,int id) {
		   Map<String, Object>map= roleServiceImpl.findInfos(id);
		    model.addAttribute("map", map);
		    return "systemManage/roleAuthorization"; 
	  }

	@PostMapping("/updateAuthorization")
	@ResponseBody
	public JsonResult updateAuthorization(@RequestBody Role role,HttpServletRequest request) {
		//role中的name实际上是operations，
		
		String operationIds=role.getName();
		String[] operationArr = operationIds.split(",");
		
		String msg = "";
		int status = 0;
		String result = roleServiceImpl.updateRoleOperation(role);//其中name存放的是权限的code字符串
		
		if (result != null && !"".equals(result)) {
			
			status = 200;
			msg = "授权操作成功";
		} else {
			status = 500;
			msg = "授权操作失败";
		}
		return JsonResult.build(status, "角色" + msg, "");

	}
	 
	@RequestMapping("/remove")
	@ResponseBody
	public  JsonResult remove(int id) {
		String msg="";
		int st=0;
		
		boolean flag = roleServiceImpl.remove(id);
		if(flag) {
			msg="操作成功";
			st=200;
		}else {
			msg="操作失败";
			st=500;
		}
	   return JsonResult.build(st,msg, null); 
	}
	
	@PostMapping("/list")
	@ResponseBody
	public JsonResult roleList() {
		List<Role> role = roleServiceImpl.findAll();
		return JsonResult.build(200,"success", role);
	}
}
