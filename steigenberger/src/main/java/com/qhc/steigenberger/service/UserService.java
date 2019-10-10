package com.qhc.steigenberger.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qhc.steigenberger.domain.ApplicationOfRolechange;
import com.qhc.steigenberger.domain.RestPage;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.domain.User;

@Service
public class UserService{
	
	@Autowired
	FryeService<User> fryeService;
	
	@Autowired
	FryeService<RestPage> pageFryeService;
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
		String url = URL_USER+"?userIdentity"+userIdentity+"&userMail="+userMail+"&isActive="+isActive;
		return  fryeService.getListInfo(url,User.class);
	}
	
	public RestPage<User> selectAndPage(int pageNum, int pageSize, User user) {
		int isActive = user.getIsActive();
		String userMail = user.getUserMail()==null?"":user.getUserMail();
		String userIdentity = user.getUserIdentity()==null?"":user.getUserIdentity();

		String url = URL_USER+"/"+pageNum+"/"+pageSize+"?isActive="+
		isActive+"&userIdentity="+userIdentity+"&userMail="+userMail;
		
		return pageFryeService.getInfo(url, RestPage.class);
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
		List<Role> roleList = user.getRoles();
		List<Role> rolesAll = roleService.getListInfo(new Role());
		if(!rolesAll.isEmpty()) {
			for(Role r:rolesAll) {
				if(roleList!=null&&roleList.size()>0) {
					for(Role role: roleList) {
						if(role.getId()==r.getId()) {
							r.setSelected(true);
						}
					}
				}
			}
		}
		user.setRoles(rolesAll);
		map.put("user", user);				
		return map;
	}

}
