package com.qhc.steigenberger.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.domain.BNotifyInfor;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.KOrderInfo;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.util.EmailTool;

@Service
public class BNotifyInforService {
	
	@Autowired
	FryeService<BNotifyInfor> fryeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserOperationInfoService userOperationInfoService;
	
	@Autowired
	EmailTool  emailTool;
	
	String approveOrder = "审批订单";
	
	private final static String URL = "bNotifyInfor";
	
	public BNotifyInfor add(BNotifyInfor bNotifyInfor) {
		
		return fryeService.postInfo(bNotifyInfor,URL, BNotifyInfor.class);
		
	}
	
	//订单编号，客户经理，订单类型，签约人，发送人员组
	public void sendEmail(KOrderInfo kOrderInfo) {
		
		List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findAll();
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < userOperationInfoList.size(); i++) {
			String operationName = userOperationInfoList.get(i).getOperationName();
			if(operationName.equals(approveOrder)) {
				User user = userService.selectUserIdentity(userOperationInfoList.get(i).getUserIdentity());
				String email = user.getUserMail();
				list.add(email);
			}
		}
		//list转数组
		String[] array=list.toArray(new String[list.size()]);
		//List转String
		String join = String.join(",", list);
		System.out.println(array[0]);
		//准备信息发邮件
		String title = "您好:"+kOrderInfo.getCreateId()+"申请的"+kOrderInfo.getContractUnit()+"的"+kOrderInfo.getOrderType()+"（需求流水号："+kOrderInfo.getId()+"）需批准";
		String content = "您好:"+kOrderInfo.getCreateId()+"申请的"+kOrderInfo.getContractUnit()+"的"+kOrderInfo.getOrderType()+"（需求流水号："+kOrderInfo.getId()+"）需要您的批准，谢谢。请勿回复此邮件";
		Map<String, Object> map = new HashMap<>();
		map.put("to", array);
		map.put("title", title);
		map.put("content", content);
		emailTool.sendSimpleMail(map);
		//存记录
		BNotifyInfor bNotifyInfor = new BNotifyInfor();
		bNotifyInfor.setId(kOrderInfo.getId());
		bNotifyInfor.setHasSend(1);
		bNotifyInfor.setMsg_from("111");
		bNotifyInfor.setMsg_to(join);
		bNotifyInfor.setMessage(content);
		this.add(bNotifyInfor);
		
		
	}
	
	
}
