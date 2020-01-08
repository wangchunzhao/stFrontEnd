package com.qhc.steigenberger.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.qhc.steigenberger.domain.Freight;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.service.AreaService;
import com.qhc.steigenberger.service.CityService;
import com.qhc.steigenberger.service.ProvinceService;
import com.qhc.steigenberger.util.ExcelUtil;
import com.qhc.steigenberger.util.ObjectConvertUtils;
import com.qhc.steigenberger.util.PageHelper;



@Controller
@RequestMapping("/freight")
public class FreightController {
	
	@Autowired
	ProvinceService bProvinceService;
	
	@Autowired
	CityService bCityService;
	
	@Autowired
	AreaService bAreaService;
	
	
	@RequestMapping("/index")
  	public String todo() {
  		return "systemManage/freight";
  	}
	
	 @RequestMapping(value = "/upload")
	 @ResponseBody
	 public JsonResult importExcel(@RequestParam(value = "excelFile", required = false) MultipartFile file,HttpServletRequest request) {
	     try {
	         MultipartRequest multipartRequest=(MultipartRequest) request;
	         MultipartFile excelFile=multipartRequest.getFile("excelFile");
	         if(excelFile!=null){

	        	 //2007版本以下的excel用这个
//	             List<List<String>> datas = ExcelUtil.readXls(excelFile.getInputStream());
	        	 List<List<String>> datas = ExcelUtil.readXlsx(excelFile.getInputStream());
	        	 bAreaService.add(datas);
/*	        	 
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
	 
	 @RequestMapping("/List")
	@ResponseBody
	public PageHelper<Freight> getUserListPage(Freight freight,HttpServletRequest request) {
		PageHelper<Freight> pageHelper = new PageHelper<Freight>();
		
		try {
			List<List<Object>> list = bAreaService.getList(freight.getPage()-1, freight.getLimit(),freight).getRows();
			List<Object[]> list1 = new ArrayList<Object[]>();
			for (int i = 0; i < list.size(); i++) {
				Object[] a= list.get(i).toArray();
				list1.add(a);
			}
			List<Freight> testViews = ObjectConvertUtils.objectToBean(list1, Freight.class);
			pageHelper.setRows(testViews);
			pageHelper.setTotal(bAreaService.getList(freight.getPage()-1, freight.getLimit(),freight).getTotal());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageHelper;
	}
	 
	 

}
