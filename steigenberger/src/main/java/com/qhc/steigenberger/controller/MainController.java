package com.qhc.steigenberger.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("main")
@Controller
public class MainController {
	
	@RequestMapping("/main")
    public String index(Model model,HttpServletRequest request) {		
		 HttpSession session =request.getSession();			
    	return "main";
     }	
	
	@RequestMapping("/mainPlat")
    public String mainPlat(Model model,HttpServletRequest request) {		
    	return "systemManage/mainPlat";
     }	
      
      @GetMapping("/")
	  public String   hello() {
		  return "login";
	  }
      
      @GetMapping("/exit")
	  public String   exit(HttpServletRequest request) {
    	  HttpSession session=request.getSession();
    	  session.removeAttribute("users");
		  return "redirect:/";
	  }
      @GetMapping("/index")
	  public String   index() {
		  return "index";
	  }
      @GetMapping("/main")
      public String tomain() {
    	  return "main";
      }
}
