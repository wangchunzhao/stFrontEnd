package com.qhc.steigenberger.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.domain.SpecialDelivery;
import com.qhc.steigenberger.service.KOrderInfoService;
import com.qhc.steigenberger.service.SpecialDeliveryService;
import com.qhc.steigenberger.service.UserService;
import com.qhc.steigenberger.util.FileUploadAndDown;
import com.qhc.steigenberger.util.PageHelper;

@RequestMapping("special")
@Controller
public class SpecialController {
	
	@Autowired
	SpecialDeliveryService specialDeliveryService;
	
	@Autowired
	KOrderInfoService kOrderInfoService;
	
	@Autowired
	UserService userService;
	
	public static String fileName;
	
	

	@RequestMapping("/index")
	public String index() {
		return "special/specialList";
	}
	
	@RequestMapping("/listData")
	@ResponseBody
	public PageHelper<KOrderInfo> getOrderListPage(KOrderInfo kOrderInfo,HttpServletRequest request) {
		PageHelper<KOrderInfo> pageHelper = null;
		//获取页面时间段的查询条件
		String createTime = request.getParameter("createTime1");
		if(createTime !=null && !"".equals(createTime)) {
			String startTime = createTime.substring(0, 10);
			String endTime = createTime.substring(createTime.length()-10);
			kOrderInfo.setStartTime(startTime);
			kOrderInfo.setEndTime(endTime);
		}
		
		try {
			// 查询当前页实体对象
			pageHelper = kOrderInfoService.getList(kOrderInfo.getPage()-1, kOrderInfo.getLimit(), kOrderInfo);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return pageHelper;
	}

	@PostMapping("/add")
	@ResponseBody
	public JsonResult add(@RequestBody SpecialDelivery specialDelivery,HttpServletRequest request) {
		
		// 1.没有就是新增操作
		// 2.如果存在，就是更新操作
		String msg = "";
		int status = 0;
		SpecialDelivery result = specialDeliveryService.saveSpecialDelivery(specialDelivery);
		if (result != null) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "申请" + msg, "");

	}
	
	@RequestMapping("/queryApplyListByOrderId")
	@ResponseBody
	public JsonResult queryApplyListByOrderId(HttpServletRequest request) {
		
		String ordersId = request.getParameter("ordersId");
		// 1.没有就是新增操作
		// 2.如果存在，就是更新操作
		String msg = "";
		int status = 0;
		List<SpecialDelivery> result = specialDeliveryService.findListInfoByOrderId(Integer.valueOf(ordersId));
		if (result != null) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "申请" + msg, result);
		
	}
	
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(@RequestParam String orderId) {
		KOrderInfo order = kOrderInfoService.getInfoById(Integer.valueOf(orderId));
		ModelAndView mv = new ModelAndView();
		mv.addObject("order", order);
		mv.setViewName("special/specialApply");
		return mv;
	}
	
	@RequestMapping("/toShow")
	public ModelAndView toShow(@RequestParam String applyId) {
		SpecialDelivery sd = specialDeliveryService.findInfoById(applyId);
		KOrderInfo order = kOrderInfoService.getInfoById(sd.getOrdersId());
		ModelAndView mv = new ModelAndView();
		mv.addObject("order", order);
		mv.addObject("apply", sd);
		mv.setViewName("special/showApply");
		return mv;
	}
	
	@RequestMapping("/saveApply")
	@ResponseBody
	public JsonResult saveApply(@RequestBody SpecialDelivery specialDelivery,HttpServletRequest request) {
		
		specialDelivery.setApplyer((String)request.getSession().getAttribute(userService.SESSION_USERIDENTITY));
		specialDelivery.setApplyId(0);
		specialDelivery.setApplyStatus(1);
		specialDelivery.setApplyTime(new Date());
		//System.out.println(fileName+"------------------------");
		Long dateLong = (new Date()).getTime();
		specialDelivery.setEnclosureName(dateLong+"_"+fileName);
		
		// 1.没有就是新增操作
		// 2.如果存在，就是更新操作
		String msg = "";
		int status = 0;
		SpecialDelivery result = specialDeliveryService.saveSpecialDelivery(specialDelivery);
		if (result != null) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "申请" + msg, result);
		
	}
	
	 @RequestMapping(value = "/upload")
	 @ResponseBody
	 private JsonResult importExcel(@RequestParam(value = "myupload", required = false) MultipartFile file,HttpServletRequest request) {
	     try {
	         MultipartRequest multipartRequest=(MultipartRequest) request;
	         MultipartFile myfile=multipartRequest.getFile("myupload");
	         if(myfile!=null){
	        	 fileName = myfile.getOriginalFilename();
//	        	 System.out.println(fileName);
	     		
	     		FileUploadAndDown fud = new FileUploadAndDown();
	     		JsonResult result = fud.upload(file, request.getSession());
	        	 if(result.isOK()) {
	        		 return JsonResult.build(200,"success",1);
	        	 }
	         }else{
	             return new JsonResult(false);
	         }
	     } catch (Exception e) {
	         return new JsonResult(false);
	     }
	     return new JsonResult(false);
	 }
}
