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
	
	/**
	  * 当前账号session常量
	 */
	public static final String ACCOUNT = "account";
	
	private final static String URL_USER_PAGEABLELIST = "user/paging";
	
	private final static String URL_USER = "user";
	
	public final static String SESSION_USERIDENTITY = "userIdentity";

	public List<User> getList(User user) {
		Integer isActive = user.getIsActive();
		String userIdentity = user.getUserIdentity()==null?"":user.getUserIdentity();
		String userMail = user.getUserMail()==null?"":user.getUserMail();
		String rolesName = user.getRolesName()==null?"":user.getRolesName();
		String url = URL_USER+"?userIdentity"+userIdentity+"&userMail="+userMail+"&isActive="+isActive+"&rolesName="+rolesName;
		return  fryeService.getListInfo(url,User.class);
	}
	
	public RestPageUser selectAndPage(int pageNum, int pageSize, User user) {
		String url = URL_USER_PAGEABLELIST+"?pageNo="+pageNum+"&pageSize="+pageSize;
		
		return pageFryeService.getInfo(url, RestPageUser.class);
	}


	public User saveUserInfo(User user) {
		
		return fryeService.postInfo(user,URL_USER, User.class);
	}
	
	
	public User updateUserInfo(User user) {
		
		return fryeService.postInfo(user,URL_USER, User.class);
	}
	
	
	public User add(User user) {
		
		return fryeService.postInfo(user,URL_USER, User.class);
	}
	
	
	public User selectUserIdentity(String str) {
		String url = URL_USER+"/"+str;
		return fryeService.getInfo(url, User.class);
	}

}
