package com.qhc.steigenberger.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.NotifyInfor;
import com.qhc.steigenberger.domain.Order;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.util.EmailTool;

@Service
public class NotifyInforService {
	
	@Autowired
	FryeService fryeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserOperationInfoService userOperationInfoService;
	
	@Autowired
	EmailTool  emailTool;
	
	String approveOrder = "审批订单";
	
	private final static String URL = "bNotifyInfor";
	
	public NotifyInfor add(NotifyInfor bNotifyInfor) {
		
		return fryeService.postInfo(bNotifyInfor,URL, NotifyInfor.class);
		
	}
	
	//订单编号，客户经理，订单类型，签约人，发送人员组
	public void sendEmail(Order order) {
		
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
		String title = "您好:"+order.getCreaterName()+"申请的"+order.getCustomerName()+"的"+order.getOrderType()+"（需求流水号："+order.getId()+"）需批准";
		String content = "您好:"+order.getCreaterName()+"申请的"+order.getCustomerName()+"的"+order.getOrderType()+"（需求流水号："+order.getId()+"）需要您的批准，谢谢。请勿回复此邮件";
		Map<String, Object> map = new HashMap<>();
		map.put("to", array);
		map.put("title", title);
		map.put("content", content);
		emailTool.sendSimpleMail(map);
		//存记录
		NotifyInfor bNotifyInfor = new NotifyInfor();
		bNotifyInfor.setId(order.getId());
		bNotifyInfor.setHasSend(1);
		bNotifyInfor.setMsg_from("111");
		bNotifyInfor.setMsg_to(join);
		bNotifyInfor.setMessage(content);
		this.add(bNotifyInfor);
	}
	//签署合同发送邮件
	public void sendContractEmail(int orderId,String versionId,String name,String emailTo) {
		String[] array = new String[1];
		array[0] = emailTo;
		
		//准备信息发邮件
		String title = versionId+"版合同已制作请上传签署";
		String content = "尊敬的"+name+"版本号为"+versionId+"的合同于今日已制作完成，请查看附件；确认无误后请尽快上传到上上签平台，发起合同签署，请勿回复此邮件，谢谢";
		Map<String, Object> map = new HashMap<>();
		map.put("to", array);
		map.put("title", title);
		map.put("content", content);
		emailTool.sendSimpleMail(map);
		//存记录
		NotifyInfor bNotifyInfor = new NotifyInfor();
		bNotifyInfor.setId(orderId);
		bNotifyInfor.setHasSend(1);
		bNotifyInfor.setMsg_from("111");
		bNotifyInfor.setMsg_to(emailTo);
		bNotifyInfor.setMessage(content);
		this.add(bNotifyInfor);
	}
	
	
}
