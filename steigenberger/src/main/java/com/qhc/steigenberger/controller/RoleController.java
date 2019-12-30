package com.qhc.steigenberger.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

	/**
	 * 根据角色名查找角色
	 * @param page
	 * @param pageSize
	 * @param entity
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping(value = "index/{name}")
	public String index(@RequestParam(defaultValue = "0", name = "page") Integer page,
			@RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize,
			Role entity, 
			Model model,
			HttpServletRequest request) {
		model.addAttribute("role1", entity);
		model.addAttribute("datas", roleService.getPageableListByName(page, pageSize, entity));
		model.addAttribute("currentPath", "/role/index/" + entity.getName());
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
		Role result = roleService.updateRoleOperation(role);
		
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
