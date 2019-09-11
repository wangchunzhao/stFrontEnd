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
    	return "loginSuccess";
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
      
    @RequestMapping("/todo")
  	public String todo() {
  		return "todo/mytask";
  	}
    
    @RequestMapping("/newOrder")
  	public String newOrder() {
  		return "newOrder/orderForm";
  	}
    
    @RequestMapping("/orderManage")
  	public String orderManage() {
  		return "orderManage/myOrder";
  	}
    
    @RequestMapping("/contract")
  	public String contract() {
  		return "contract/myContract";
  	}
    
    @RequestMapping("/report1")
  	public String report1() {
  		return "report/orderDetail";
  	}
    
    @RequestMapping("/report2")
  	public String report2() {
  		return "report/biddingDetail";
  	}
    
    @RequestMapping("/newSpecial")
  	public String newSpecial() {
  		return "newOrder/specialOrder";
  	}
    
    
    @RequestMapping("/noAuthorization")
    public String noAuthorization() {
    	return "noAuthorization";
    }
    
    
    
    
    
}
