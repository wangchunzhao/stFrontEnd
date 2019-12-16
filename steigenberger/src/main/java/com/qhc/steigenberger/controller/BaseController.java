package com.qhc.steigenberger.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.UserService;

/**
 * 用户session
 * 
 * @author lizuoshan
 *
 */
public abstract class BaseController {

	@Autowired
	UserService userService;

	/**
	 * 得到账户对象
	 * 
	 * @param request
	 * @return
	 */
	public User getAccount(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute(Constants.ACCOUNT);
	}

	/**
	 * 得到单个属性值
	 * 
	 * @param request
	 * @param attribute
	 * @return
	 */
	public String getValueByAttribute(HttpServletRequest request, String attribute) {

		HttpSession session = request.getSession();
		return (String) session.getAttribute(attribute);
	}

}
