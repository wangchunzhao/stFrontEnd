package com.qhc.steigenberger.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.Operations;
import com.qhc.steigenberger.domain.OrderOption;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.OrderService;
import com.qhc.steigenberger.service.UserService;

/**
 * 提供Controller公共的方法
 * 
 * @author Walker
 *
 */
public abstract class BaseController {

	@Autowired
	UserService userService;
	
	@Autowired
	OrderService orderService;

	/**
	 * 得到当前用户对象
	 * 
	 * @param request
	 * @return
	 */
	public User getAccount() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpServletResponse response = servletRequestAttributes.getResponse();
	    
		HttpSession session = request.getSession();
		return (User) session.getAttribute(Constants.ACCOUNT);
	}

	/**
	 * 得到当前用户identity
	 * 
	 * @param request
	 * @return
	 */
	public String getUserIdentity() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpServletResponse response = servletRequestAttributes.getResponse();
	    
		HttpSession session = request.getSession();
		return (String) session.getAttribute(Constants.IDENTITY);
	}

	/**
	 * 从Session获取属性
	 * 
	 * @param request
	 * @param attribute
	 * @return
	 */
	public String getValueByAttribute(String attribute) {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpServletResponse response = servletRequestAttributes.getResponse();

		HttpSession session = request.getSession();
		return (String) session.getAttribute(attribute);
	}
	
	/**
	 * 获取当前用户的所有权限
	 * 
	 * @return
	 */
	public List<Operations> getPermissions() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpServletResponse response = servletRequestAttributes.getResponse();
	    HttpSession session = request.getSession();
		
	    List<Operations> list = (List<Operations>)session.getAttribute("permissions");
	    if (list == null) {
// TODO	    	oo = orderService.getOrderOption();
	    	session.setAttribute("permissions", list);
	    }
	    
	    return list;
	}
	
	/**
	 * 获取当前用户的所有菜单
	 * 
	 * @return
	 */
	public Map<String, Operations> getMenus() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpServletResponse response = servletRequestAttributes.getResponse();
	    HttpSession session = request.getSession();
		
	    Map<String, Operations> menus = (Map<String, Operations>)session.getAttribute("menus");
	    if (menus == null) {
// TODO	    	oo = orderService.getOrderOption();
	    	session.setAttribute("menus", menus);
	    }
	    
	    return menus;
	}
	
	/**
	 * 获取订单所有选项
	 * 
	 * @return
	 */
	public Result getOrderOption() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    HttpServletResponse response = servletRequestAttributes.getResponse();
	    HttpSession session = request.getSession();
		
	    OrderOption oo = (OrderOption)session.getAttribute("options");
	    Result result = null;
	    if (oo == null) {
	    	result = orderService.getOrderOption();
	    	if("ok".equals(result.getStatus())) {
	    		oo = (OrderOption)result.getData();
	    		session.setAttribute("options", oo);
	    	}    	
	    }else {
	    	result = new Result();
	    	result.setData(oo);
	    	result.setStatus("ok");
	    }
	    
	    return result;
	}

}
