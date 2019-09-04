package com.qhc.steigenberger.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.RoleServiceI;
import com.qhc.steigenberger.service.UserServiceI;

//import io.swagger.annotations.Api;

@RequestMapping("user")
@Controller
//@Api(value = "User Manager")
public class UserController {
	@Autowired
	UserServiceI userServiceImpl;
	@Autowired
	RoleServiceI roleServiceImpl;

	@RequestMapping("/index")
	public String index(@RequestParam(defaultValue = "0") int one, 
			@RequestParam(defaultValue = "1",name="number") Integer number,
			@RequestParam(defaultValue = "5",name="pageSize") Integer pageSize, 
			User entity, 
			Model model, 
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (one == 1) {
			session.removeAttribute("entity");
		}
		// 有存在session的情况
		if (entity == null && session.getAttribute("entity") != null) {
			entity = (User) session.getAttribute("entity");
		}
		// 有存在带查询条件其session中没有值
		else if (entity != null && session.getAttribute("entity") == null) {
			session.setAttribute("entity", entity);
		}
		// 有存在带查询条件其session中有值
		else if (entity != null && session.getAttribute("entity") != null) {
			session.setAttribute("entity", entity);
		}

		model.addAttribute("user1", entity);
		//result list
		PageInfo<User> pageInfo = userServiceImpl.selectAndPage(number, pageSize, entity);
		model.addAttribute("pageInfo", pageInfo);
		
		//roles list
		List<Role> roleList = roleServiceImpl.findAll();
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
		User result = userServiceImpl.updateUserInfo(user);
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
	public JsonResult update( @RequestBody User user, HttpServletRequest request) {
		
		String msg = "";
		int status = 0;
		User result = userServiceImpl.updateUserInfo(user);
		if (result != null) {
			status = 200;
			msg = "更新操作成功!";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "角色" + msg, "");

	}

	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(int id) {
		String msg = "";
		int st = 0;

		boolean flag = userServiceImpl.delete(id);
		if (flag) {
			msg = "操作成功";
			st = 200;
		} else {
			msg = "操作失败";
			st = 500;
		}
		return JsonResult.build(st, msg, null);
	}

	@RequestMapping("login")
	public String main(Model model) {
		return "login";
	}

	@RequestMapping("hello")
	public ModelAndView hello() {
		ModelAndView modelAndView = new ModelAndView();
		// 1.存数据
		modelAndView.addObject("name", "name");
		modelAndView.addObject("time", new Date());
		// 2.指定视图
		modelAndView.setViewName("hello");
		return modelAndView;
	}

	@GetMapping(value = "home")
	public void homePage(HttpServletResponse response) throws IOException {
		response.sendRedirect("index.html");
//        return "index";
	}

}
