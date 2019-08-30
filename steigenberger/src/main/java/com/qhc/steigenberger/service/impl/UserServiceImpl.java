package com.qhc.steigenberger.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.UserServiceI;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

@Service
public class UserServiceImpl extends WebServcieTool<User> implements UserServiceI{
	
	public PageInfo<User> selectAndPage(int pageNum, int pageSize, User user) {
		PageHelper.startPage(pageNum, pageSize);
		Integer isActive = user.getIsActive();
		String userMail = user.getUserMail();
		String userIdentity = user.getUserIdentity();
		
		String url = "user/findAll?userMail="+userMail+"&isActive="+isActive+"&userIdentity="+userIdentity;
		List<User> list=findAll(CommonConstant.BASEURL, url,User.class);
		PageInfo<User> pageInfo=new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public User selectUserInfo(int id) {
		
		return findOne(CommonConstant.BASEURL+"user/findById?id="+id,User.class);
	}

	@Override
	public String saveUserInfo(User user) {
		
		return postInfo(user,CommonConstant.BASEURL+"user/add",User.class);
	}
	
	@Override
	public String updateUserInfo(User user) {
		
		return postInfo(user,CommonConstant.BASEURL+"user/add",User.class);
	}
	
	@Override
	public boolean delete(int id) {
		String str = delete(CommonConstant.BASEURL+"user/delete?id="+id);
		if("false".equals(str)) {
			return false;
		}else {
			return true;
		}
	}

}
