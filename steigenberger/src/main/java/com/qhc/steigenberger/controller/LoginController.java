package com.qhc.steigenberger.controller;

import java.util.Hashtable;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.UserService;

@Controller
public class LoginController extends BaseController {

	@Value("${qhc.ldap}")
	String ldap;

	@Autowired
	UserService userService;

	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(Constants.IDENTITY) == null) {
			return new ModelAndView("login");
		} 
		return new ModelAndView("main");
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(Constants.IDENTITY) != null) {
			return new ModelAndView("index");
		} 
		return new ModelAndView("login");
	}

	// 登陆验证
	@RequestMapping(value = "loginIn")
	public ModelAndView loginIn(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		ModelAndView modelAndView = new ModelAndView();
		String userName = request.getParameter("username");// AD域认证，用户的登录UserName
		String password = request.getParameter("password");// AD域认证，用户的登录PassWord
		try {
			boolean verify = false;
			User user = userService.selectUserIdentity(userName);
			if (user == null) {
				modelAndView.addObject("userName", userName);
				modelAndView.addObject("msg", "无该用户信息!");
				modelAndView.setViewName("login");
				return modelAndView;
			}
			int isActive = user.getIsActive();

			if (userName == "" || password == "") {
				modelAndView.addObject("msg", "用户名或密码不能为空!");
				modelAndView.setViewName("login");
			} else if (isActive == 0) {
				modelAndView.addObject("userName", userName);
				modelAndView.addObject("msg", "用户已被禁用!");
				modelAndView.setViewName("login");
			} else if ("false".equals(ldap)) {
				verify = true;
				modelAndView.setViewName("main");
				System.out.println("身份验证成功!");
			} else {
				String host = "utccgl.com";// AD域IP，必须填写正确
				String domain = "utccgl\\";// 域名后缀，
				String url = new String("ldap://" + host);//
				String userN = userName.indexOf(domain) > 0 ? userName : domain + userName;// 网上有别的方法，但是在我这儿都不好使，建议这么使用
				Hashtable env = new Hashtable();// 实例化一个Env
				DirContext ctx = null;
				env.put(Context.SECURITY_AUTHENTICATION, "simple");// LDAP访问安全级别(none,simple,strong),一种模式，这么写就行
				env.put(Context.SECURITY_PRINCIPAL, userN); // 用户名
				env.put(Context.SECURITY_CREDENTIALS, password);// 密码
				env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");// LDAP工厂类
				env.put(Context.PROVIDER_URL, url);// Url
				try {
					ctx = new InitialDirContext(env);// 初始化上下文
//					setAccount(request, userName);
					verify = true;
					modelAndView.setViewName("main");
					System.out.println("身份验证成功!");
				} catch (AuthenticationException e) {
					modelAndView.addObject("userName", userName);
					modelAndView.addObject("msg", "身份验证失败!");
					modelAndView.setViewName("login");
					e.printStackTrace();
				} catch (javax.naming.CommunicationException e) {
					modelAndView.addObject("userName", userName);
					modelAndView.addObject("msg", "AD域连接失败!");
					modelAndView.setViewName("login");
					e.printStackTrace();
				} catch (Exception e) {
					modelAndView.addObject("userName", userName);
					modelAndView.addObject("msg", "身份验证未知异常!");
					modelAndView.setViewName("login");
					e.printStackTrace();
				} finally {
					if (null != ctx) {
						try {
							ctx.close();
							ctx = null;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (verify) {
				modelAndView.setViewName("main");
				System.out.println("身份验证成功!");
				session.setAttribute(Constants.ACCOUNT, user);
				session.setAttribute(Constants.IDENTITY, userName);
				session.setAttribute(Constants.PERMISSIONS, user.getOperations());
				getMenus();
				getPermissions();
				response.sendRedirect(request.getContextPath());
			}
		} catch (Exception e) {
			modelAndView.addObject("userName", userName);
			modelAndView.addObject("msg", "后台访问异常");
			modelAndView.setViewName("login");
			e.printStackTrace();
		}

		return modelAndView;
	}

	// 登陆验证
	@RequestMapping(value = "loginOut")
	public String loginOut(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("222222222222222222222");
		// 干掉cookie和session
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.IDENTITY);
		session.removeAttribute(Constants.ACCOUNT);
		session.removeAttribute(Constants.PERMISSIONS);

		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if ("autoLogin".equals(c.getName())) {
					// 设置cookie存活时间为0
					c.setMaxAge(0);
					// 将cookie响应到前台
					// response.addCookie(c);
					break;
				}
			}
		}
		return "login";
	}

}
