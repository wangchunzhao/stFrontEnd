package com.qhc.steigenberger.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.RestPageRole;
import com.qhc.steigenberger.domain.RestPageUser;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.SapSalesOffice;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.util.CommonConstant;

@Service
public class UserService{
	
	@Autowired
	FryeService<User> fryeService;
	
	@Autowired
	FryeService<RestPageUser> pageFryeService;
	
	private final static String URL_USERS_PAGEABLELIST = "users/paging";
	
	private final static String URL_USERS = "users";

	public List<User> getList() {
		return  fryeService.getListInfo(URL_USERS,User.class);
	}
	
	public RestPageUser selectAndPage(int pageNum, int pageSize, User user) {
		String url = URL_USERS_PAGEABLELIST+"?pageNo="+pageNum+"&pageSize="+pageSize;
		
		return pageFryeService.getInfo(url, RestPageRole.class);
	}


	public User saveUserInfo(User user) {
		
		return fryeService.postInfo(user,URL_USERS, User.class);
	}
	
	
	public User updateUserInfo(User user) {
		
		return fryeService.postInfo(user,URL_USERS, User.class);
	}
	
	
	public User add(User user) {
		
		return fryeService.postInfo(user,URL_USERS, User.class);
	}
	
	
	public User selectUserIdentity(String str) {
		String url = URL_USERS+"/"+str;
		return fryeService.getInfo(url, User.class);
	}

}
