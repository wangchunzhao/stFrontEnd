package com.qhc.steigenberger.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.domain.Order;
import com.qhc.steigenberger.domain.ReportFormsInfo;
import com.qhc.steigenberger.domain.SalesOffice;
import com.qhc.steigenberger.service.ReportFormsInfoService;
import com.qhc.steigenberger.service.SapSalesOfficeService;
import com.qhc.steigenberger.util.ExportExcel;
import com.qhc.steigenberger.util.FileUploadAndDown;
import com.qhc.steigenberger.util.PageHelper;

@Controller
@RequestMapping("/report")
public class ReportFormsInfoController{
	
	@Autowired
	ReportFormsInfoService reportFormsInfoService;

	@Autowired
	SapSalesOfficeService sapSalesOfficeService;
	
	
	@RequestMapping("/index")
	@ResponseBody
	public PageHelper<ReportFormsInfo> index(ReportFormsInfo reportFormsInfo,HttpServletRequest request) {
		PageHelper<ReportFormsInfo> pageHelper = new PageHelper<ReportFormsInfo>();
		//获取页面时间段的查询条件
		String createTime = request.getParameter("createTime1");
		if(createTime !=null && !"".equals(createTime)) {
			String startTime = createTime.substring(0, 10);
			String endTime = createTime.substring(createTime.length()-10);
			reportFormsInfo.setStartTime(startTime);
			reportFormsInfo.setEndTime(endTime);
		}
		// 查询当前页实体对象
		pageHelper = reportFormsInfoService.findByConditions(reportFormsInfo.getPage()-1, reportFormsInfo.getLimit(), reportFormsInfo);
		return pageHelper;
	}
	
	@RequestMapping("/purchaseAndSale")
  	public String purchaseAndSale() {
  		return "report/purchaseAndSale";
  	}
	
	@RequestMapping("/biddingDetail")
	public ModelAndView biddingDetail() {
		ModelAndView mv = new ModelAndView();
		//投标客户    TODO
		
		List<SalesOffice> areaList = sapSalesOfficeService.getList();
		mv.addObject("areaList", areaList);
		mv.setViewName("report/biddingDetail");
		return mv;
	}
	
	@RequestMapping("/biddingDetailList")
	@ResponseBody
	public PageHelper<ReportFormsInfo> biddingDetailList(ReportFormsInfo reportFormsInfo,HttpServletRequest request) {
		PageHelper<ReportFormsInfo> pageHelper = new PageHelper<ReportFormsInfo>();
		//获取页面时间段的查询条件
		String createTime = request.getParameter("createTime1");
		if(createTime !=null && !"".equals(createTime)) {
			String startTime = createTime.substring(0, 10);
			String endTime = createTime.substring(createTime.length()-10);
			reportFormsInfo.setStartTime(startTime);
			reportFormsInfo.setEndTime(endTime);
		}
		// 查询当前页实体对象
		pageHelper = reportFormsInfoService.findByConditions(reportFormsInfo.getPage()-1, reportFormsInfo.getLimit(), reportFormsInfo);
		return pageHelper;
	}
	
	@RequestMapping("/exportExcel")
	@ResponseBody
	public void exportExcel(Order order,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String customer = request.getParameter("customer");
		String area = request.getParameter("area");
		String createTime = request.getParameter("createTime1");
		if(createTime !=null && !"".equals(createTime)) {
			String startTime = createTime.substring(0, 10);
			String endTime = createTime.substring(createTime.length()-10);
//			order.setStartTime(startTime);
//			order.setEndTime(endTime);
		}
		
		List<HashMap<String, Object>> listMap = new ArrayList<>();
        HashMap<String,Object> dataMap = new HashMap<>();
        
        for(int i=0;i<30;i++){
            dataMap.put("datetime", "2017-12-13 10:43:00");
            dataMap.put("person", "张翠山");
            dataMap.put("type", "文本");
            dataMap.put("content", "工作一定要认真，态度要端正，作风要优良，行事要效率，力争打造一个完美的产品出来。");
            listMap.add(dataMap);
        }
        
        String title = "张翠山的发言记录";
        String[] rowsName = new String[]{"序号","时间","发言人","类型","消息"};
        List<Object[]>  dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < listMap.size(); i++) {
            HashMap<String, Object> data = listMap.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i;
            objs[1] = data.get("datetime");
            objs[2] = data.get("person");
            objs[3] = data.get("type");
            objs[4] = data.get("content");
            dataList.add(objs);
        }
        
        
        ExportExcel ex = new ExportExcel("orderInfo","报表数据（按订单查询）", rowsName, dataList);
        ex.export(response);
		
	}
	
	@RequestMapping("/down")
	@ResponseBody
	public void down(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		
		FileUploadAndDown fud = new FileUploadAndDown();
		
		fud.Download("frye.sql", response, session);
	}
	
	
	@RequestMapping("/toUpload")
	public String toUpload(){
		
		return "report/upload";
	}
	
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file, HttpSession session){
		
		FileUploadAndDown fud = new FileUploadAndDown();
		fud.upload(file, session);
		return null;
	}
	
	@RequestMapping(value="/upload3",method = RequestMethod.POST)
	@ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest req){
        try {
            String fileName = System.currentTimeMillis()+file.getOriginalFilename();
            String destFileName=req.getServletContext().getRealPath("")+"upload"+ File.separator+fileName;

            File destFile = new File(destFileName);
            destFile.getParentFile().mkdirs();
            System.out.println(destFile);
            file.transferTo(destFile);
//            model.addAttribute("fileName",fileName);
//            model.addAttribute("path",destFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }
        return "OK";
    }

}
