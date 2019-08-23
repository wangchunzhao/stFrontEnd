package com.qhc.steigenberger.controller;



import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//import io.swagger.annotations.Api;


@Controller
//@Api(value = "User Manager")
public class UserController {

	@RequestMapping(value = "/}", method = RequestMethod.GET)
	public String  index() {

		 return "redirect:/index.html";
    }
	@RequestMapping("login")
	public String  main(Model model) {

		 return "index";
    }
	@RequestMapping("hello")
    public ModelAndView hello(){
        ModelAndView modelAndView = new ModelAndView();
        //1.存数据
         modelAndView.addObject("name","name");
         modelAndView.addObject("time",new Date());
        //2.指定视图
        modelAndView.setViewName("hello");
        return modelAndView;
    }
	
	@GetMapping(value = "home")
    public void homePage(HttpServletResponse response)throws IOException{
        response.sendRedirect("index.html");
//        return "index";
    }

}
