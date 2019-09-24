package com.qhc.steigenberger.controller;

import java.io.File;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.qhc.steigenberger.domain.BCityFreight;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.service.BCityFreightService;
import com.qhc.steigenberger.util.ExcelUtil;
import com.qhc.steigenberger.util.FileHandleUtil;
import com.qhc.steigenberger.util.PageHelper;



@Controller
@RequestMapping("/freight")
public class FreightController {
	
	@Autowired
	BCityFreightService BCityFreightService;
	
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
	        	 BCityFreight bCityFreight = new BCityFreight();
	        	 //2007版本以下的excel用这个
//	             List<List<String>> datas = ExcelUtil.readXls(excelFile.getInputStream());
	        	 List<List<String>> datas = ExcelUtil.readXlsx(excelFile.getInputStream());
	        	 for (int i = 0; i < datas.size(); i++) {
	        		 List<String> data = datas.get(i);
	        		 for(int j = 0; j < data.size(); j++) {
	        			bCityFreight.setProvinceName(data.get(0));
	        			bCityFreight.setProvinceCode(data.get(1));
        				bCityFreight.setCityName(data.get(2));
        				bCityFreight.setCityCode(data.get(3));
        				bCityFreight.setCountyName(data.get(4));
        				bCityFreight.setCountyCode(data.get(5));
        				bCityFreight.setPrice(Double.valueOf(data.get(6)));
        				BCityFreightService.add(bCityFreight);
	        		 }
				}
	     
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
	 
	 @RequestMapping("/bCityFreightList")
	 @ResponseBody
	 public PageHelper<BCityFreight> getUserListPage(BCityFreight bCityFreight,HttpServletRequest request) {
		 PageHelper<BCityFreight> pageHelper = null;
		 pageHelper = BCityFreightService.getList(bCityFreight.getPage()-1, bCityFreight.getLimit(), bCityFreight);
		 return pageHelper;
		 
	 }

}
