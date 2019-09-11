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
import com.qhc.steigenberger.service.OperationService;
import com.qhc.steigenberger.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	RoleService roleService;
	@Autowired
	OperationService operationService;


	@RequestMapping("/index")
	public String index(@RequestParam(defaultValue = "0", name = "page") Integer page,
			@RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize,
			Role entity, 
			Model model,
			HttpServletRequest request) {
		model.addAttribute("role1", entity);
		model.addAttribute("datas", roleService.getPageableList(page, pageSize, entity));
		model.addAttribute("currentPath", "/role/index?isActive="+entity.getIsActive());
		model.addAttribute("operationList", operationService.getList());
		
		return "systemManage/roleManage";
	}

	
	@RequestMapping("/update")
	@ResponseBody
	public JsonResult update(@RequestParam Integer id,HttpServletRequest request) {
		String msg = "";
		int status = 0;
		Role result = roleService.saveRoleInfo(id);
		if (result != null) {
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
		   Map<String, Object>map= roleService.findInfos(id);
		    model.addAttribute("map", map);
		    return "systemManage/roleAuthorization"; 
	  }

	@RequestMapping("/updateAuthorization")
	@ResponseBody
	public JsonResult updateAuthorization(@RequestBody Role role,HttpServletRequest request) {
		String msg = "";
		int status = 0;
		Role result = roleService.updateRoleOperation(role);//其中name存放的是权限的code字符串
		
		if (result != null) {
			status = 200;
			msg = "授权操作成功";
		} else {
			status = 500;
			msg = "授权操作失败";
		}
		return JsonResult.build(status, "角色" + msg, "");

	}
	 
}
