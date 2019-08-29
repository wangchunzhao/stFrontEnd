package com.qhc.steigenberger.controller;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.service.RoleServiceI;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/operation")
public class OperationController {
	
	@Autowired
//	RoleServiceI roleService;
	
	public static String BASE_URL = "http://127.0.0.1:8801/frye/";
	public static String URL = "role/roleList/";
	
//	@RequestMapping("/index")
//    public String index(@RequestParam(defaultValue="0")Integer one,@RequestParam(defaultValue="1",name="number")Integer number,@RequestParam(defaultValue="8",name="pageSize")Integer pageSize,Role entity, Model model,HttpServletRequest request) {		
//	   
//		
//		model.addAttribute("pageInfo",roleServiceImpl.selAndPage(number, pageSize, entity));
//    	return "roleManage";
//     }	
//	
//	
//
//	@RequestMapping("/roleList")
//	public String roleList(Model model) {
//		System.out.println("---------in roleController----------------");
//		
//		Integer id = 0;
//		List<Role> list = wsService.getAll(BASE_URL, URL, id, Role.class);
//		model.addAttribute("list", list);
//		
////		System.out.println(list.size()+"-------------------------");		
//		return "systemManage/roleList2";
//	}
	
//	
//	@RequestMapping(value="/roleInfo",method = RequestMethod.POST)
//	@ResponseBody
//	public String roleInfo(HttpServletRequest request) {
//		
//		Integer id = Integer.valueOf(request.getAttribute("role_id").toString());
//		List<Role> list = roleService.getAll(BASE_URL, URL, id, Role.class);
//		Role r = new Role();
//		if(!list.isEmpty()) {
//			r=list.get(0);
//		}
//		String json="";
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			json = mapper.writeValueAsString(r);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//	
//		return json.toString();
//	}
	
	@RequestMapping(value="/submitRole",method = RequestMethod.POST)
	@ResponseBody
	public String submitRole(HttpServletRequest request) {
		String baseUrl = "http://127.0.0.1:8801/frye/";
//		WebClient webClient = WebClient.create(baseUrl);
		
		WebClient webClient = WebClient.builder()
	            .baseUrl(baseUrl)
	            .defaultHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)")
	            .defaultCookie("ACCESS_TOKEN", "test_token").build();
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		map.add("name", name);
		map.add("id", id);
		Mono<Role> response = webClient.post().uri("role/addRole")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(map))
                .retrieve()
                .bodyToMono(Role.class).timeout(Duration.of(10, ChronoUnit.SECONDS));

        Role demoObj = response.block();
//		
        System.out.println(demoObj.getName());
	
		return "success";
	}
	
}
