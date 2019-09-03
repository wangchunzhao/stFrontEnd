package com.qhc.steigenberger.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.UserServiceI;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

@Service
public class UserServiceImpl extends WebServcieTool<User> implements UserServiceI{
	
	public PageInfo<User> selectAndPage(int pageNum, int pageSize, User user) {
		PageHelper.startPage(pageNum, pageSize);
		Integer isActive = user.getIsActive();
		String userName = user.getUserName()==null?"":user.getUserName();
		String userMail = user.getUserMail()==null?"":user.getUserMail();
		String rolesName = user.getRolesName()==null?"":user.getRolesName();
		
//		String url = "user/findAll?userMail="+userMail+"&isActive="+isActive+"&userIdentity="+userIdentity;
//		List<User> list=findAll(CommonConstant.BASEURL, url,User.class);
		String url = "user/findByMultipleConditions?userName="+userName+"&userMail="+userMail+"&isActive="+isActive+"&rolesName="+rolesName;
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
		
		String result = null;
		try {
			result = postInfo(user,CommonConstant.BASEURL+"user/add",User.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String updateUserInfo(User user) {
		
		String result = null;
		try {
			result = postInfo(user,CommonConstant.BASEURL+"user/add",User.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public User add(User user) {
		
		User result = null;
		try {
			result = addInfo(user,CommonConstant.BASEURL+"user/add",User.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean delete(int id) {
		
		String result = null;
		try {
			result = delete(CommonConstant.BASEURL+"user/delete?id="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result!=null&&"false".equals(result)) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public User selectUserIdentity(String str) {
		return findOne(CommonConstant.BASEURL+"users/user/"+str,User.class);
	}

}
