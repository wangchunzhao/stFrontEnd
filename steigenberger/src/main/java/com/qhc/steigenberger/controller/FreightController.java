package com.qhc.steigenberger.controller;

import java.io.File;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.util.ExcelUtil;
import com.qhc.steigenberger.util.FileHandleUtil;



@Controller
@RequestMapping("/freight")
public class FreightController {
	
	@RequestMapping("/index")
  	public String todo() {
  		return "systemManage/freight";
  	}
	
	 @RequestMapping(value = "/upload")
	 @ResponseBody
	 private JsonResult importExcel(@RequestParam(value = "excelFile", required = false) MultipartFile file,HttpServletRequest request) {
	     try {
	         MultipartRequest multipartRequest=(MultipartRequest) request;
	         MultipartFile excelFile=multipartRequest.getFile("excelFile");
	         if(excelFile!=null){
	        	 //2007版本以下的excel用这个
//	             List<List<String>> datas = ExcelUtil.readXls(excelFile.getInputStream());
	        	 List<List<String>> datas = ExcelUtil.readXlsx(excelFile.getInputStream());
	     //TODO: 读到的数据都在datas里面，根据实际业务逻辑做相应处理<br>  
	             // .............
	        	 //将文件上传到某个路径
	        	 /*String fileName = excelFile.getOriginalFilename();
	        	 String filePath = "D:\\";
	             File dest = new File(filePath + fileName);
	        	 excelFile.transferTo(dest);*/
	        	 
	        	 
	             if(datas!=null && datas.size()>0){
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
