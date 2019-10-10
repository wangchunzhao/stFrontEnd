package com.qhc.steigenberger.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.domain.SalesOffice;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.service.KOrderInfoService;
import com.qhc.steigenberger.service.SapSalesOfficeService;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.util.PageHelper;

@Controller
@RequestMapping("/kOrderInfo")
public class KOrderInfoController extends BaseController{
	
	@Autowired
	SapSalesOfficeService sapSalesOfficeService;
	
	@Autowired
	KOrderInfoService kOrderInfoService;
	
	@Autowired
	UserOperationInfoService userOperationInfoService;
	
	String allorder = "全部";
	String self = "自己";
	String benqu = "大区";
	
	
	
	@RequestMapping("/kOrderInfoList")
	@ResponseBody
	public PageHelper<KOrderInfo> getUserListPage(KOrderInfo kOrderInfo,HttpServletRequest request) {
		PageHelper<KOrderInfo> pageHelper = null;
		//获取页面时间段的查询条件
		String createTime = request.getParameter("createTime1");
		if(createTime !=null || !"".equals(createTime)) {
			String startTime = createTime.substring(0, 10);
			String endTime = createTime.substring(createTime.length()-10);
			kOrderInfo.setStartTime(startTime);
			kOrderInfo.setEndTime(endTime);
		}
		
		//取得session的用户域账号
//		String identityName = request.getSession().getAttribute(userService.SESSION_USERIDENTITY).toString();
		try {
			User user = userService.selectUserIdentity("wangch");//identityName
			List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);
		
			for (int i = 0; i < userOperationInfoList.size(); i++) {
				String operationName = userOperationInfoList.get(i).getOperationName();
				if(operationName.equals(allorder)) {
					break;
				}else if(operationName.equals(benqu)) {
					kOrderInfo.setArea(Integer.valueOf(userOperationInfoList.get(i).getAttachedCode()));
					break;
				}else {
					kOrderInfo.setCreateId(user.id);
				}
			}
			// 查询当前页实体对象
			pageHelper = kOrderInfoService.getList(kOrderInfo.getPage()-1, kOrderInfo.getLimit(), kOrderInfo);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return pageHelper;
	}
	
	@RequestMapping("/orderManageList")
  	public String todo() {
  		return "orderManage/myOrder";
  	}
	
	
	

}
