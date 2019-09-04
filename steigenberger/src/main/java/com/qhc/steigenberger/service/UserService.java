package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

@Service
public class UserService extends WebServcieTool<User>{
	
	public PageInfo<User> selectAndPage(int pageNum, int pageSize, User user) {
		PageHelper.startPage(pageNum, pageSize);
		Integer isActive = user.getIsActive();
		String userName = user.getUserName()==null?"":user.getUserName();
		String userMail = user.getUserMail()==null?"":user.getUserMail();
		String rolesName = user.getRolesName()==null?"":user.getRolesName();
		
//		String url = "user/findAll?userMail="+userMail+"&isActive="+isActive+"&userIdentity="+userIdentity;
//		List<User> list=findAll(CommonConstant.BASEURL, url,User.class);
		String url = "users?isActive="+isActive+"&userName="+userName+"&rolesName="+rolesName+"&userMail="+userMail;
		List<User> list=findAll(CommonConstant.BASEURL, url,User.class);
		PageInfo<User> pageInfo=new PageInfo<User>(list);
		return pageInfo;
	}

	
	public User selectUserInfo(int id) {
		
		return findOne(CommonConstant.BASEURL+"users/"+id,User.class);
	}

	
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
	
	
	public User updateUserInfo(User user) {
		
		User result = null;
		try {
			result = addInfo(user,CommonConstant.BASEURL+"users",User.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	
	public User add(User user) {
		
		User result = null;
		try {
			result = addInfo(user,CommonConstant.BASEURL+"users",User.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	
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

	
	public User selectUserIdentity(String str) {
		return findOne(CommonConstant.BASEURL+"users/"+str,User.class);
	}

}
