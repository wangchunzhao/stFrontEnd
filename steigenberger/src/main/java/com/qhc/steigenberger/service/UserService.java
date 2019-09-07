package com.qhc.steigenberger.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.domain.Operations;
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
	@Autowired
	RoleService roleService;
	
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
		int isActive = user.getIsActive();
		String userMail = user.getUserMail()==null?"":user.getUserMail();
		String userIdentity = user.getUserIdentity()==null?"":user.getUserIdentity();

		String url = URL_USER_PAGEABLELIST+"?pageNo="+pageNum+"&pageSize="+pageSize+"&isActive="+
		isActive+"&userMail="+userMail+"&userIdentity="+userIdentity;
		
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

	public Map<String, Object> findInfos(String userIdentity) {
		Map<String, Object> map=new HashMap<>();
		User user = selectUserIdentity(userIdentity);
		Set<ApplicationOfRolechange> apps = user.getApps();
		List<Role> rolesAll = roleService.getListInfo(new Role());
		if(!rolesAll.isEmpty()) {
			for(Role r:rolesAll) {
				if(apps!=null&&apps.size()>0) {
					for(ApplicationOfRolechange app: apps) {
						if(app.getbRolesId()==r.getId()) {
							r.setSelected(true);
						}
					}
				}
			}
		}

		map.put("userId", user.getId());				
		map.put("user", user);				
		map.put("rolesAll", rolesAll);
		return map;
	}

}
