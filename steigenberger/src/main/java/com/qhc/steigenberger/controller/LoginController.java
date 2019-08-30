package com.qhc.steigenberger.controller;

import java.util.Date;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	
	@RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login() {
    	return new ModelAndView("login");
    }
	
    @RequestMapping(value = "loginIn")
    public ModelAndView loginIn(HttpServletRequest request) {

    	ModelAndView modelAndView = new ModelAndView();
    	String userName = request.getParameter("username");//AD域认证，用户的登录UserName
    	String password = request.getParameter("password");//AD域认证，用户的登录PassWord

    	if(userName=="" || password=="") {
    		modelAndView.addObject("msg","用户名或密码不能为空!");
    		modelAndView.setViewName("login");
    	}else {
    		String host = "utccgl.com";//AD域IP，必须填写正确
        	String domain = "utccgl\\";//域名后缀，
        	String url = new String("ldap://" + host );//
        	String user = userName.indexOf(domain) > 0 ? userName : domain+userName;//网上有别的方法，但是在我这儿都不好使，建议这么使用
        	Hashtable env = new Hashtable();//实例化一个Env
        	DirContext ctx = null;
        	env.put(Context.SECURITY_AUTHENTICATION, "simple");//LDAP访问安全级别(none,simple,strong),一种模式，这么写就行
        	env.put(Context.SECURITY_PRINCIPAL, user); //用户名
        	env.put(Context.SECURITY_CREDENTIALS, password);//密码
        	env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");// LDAP工厂类
        	env.put(Context.PROVIDER_URL, url);//Url
        	try {
        		ctx = new InitialDirContext(env);// 初始化上下文
        		modelAndView.setViewName("loginSucess");
        		System.out.println("身份验证成功!");
        		} catch (AuthenticationException e) {
        			modelAndView.addObject("msg","身份验证失败!");
        			modelAndView.setViewName("login");
        			e.printStackTrace();
        		} catch (javax.naming.CommunicationException e) {
        			modelAndView.addObject("msg","AD域连接失败!");
        			modelAndView.setViewName("login");
        			e.printStackTrace();
        			} catch (Exception e) {
        				modelAndView.addObject("msg","身份验证未知异常!");
        				modelAndView.setViewName("login");
        				e.printStackTrace();
        			} finally{
        				if(null!=ctx){
        				try {
        					ctx.close();
        					ctx=null;
        				} catch (Exception e){
        					e.printStackTrace();
        				}
        				}
        			}
        	
    	}
       return modelAndView;
    }
    
   
	
}
