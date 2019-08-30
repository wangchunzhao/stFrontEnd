package com.qhc.steigenberger.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

/**
 * 	用户session
 * @author lizuoshan
 *
 */
public abstract class BaseController extends WebServcieTool<User>{

	/**
	  * 当前账号常量
	 */
    private static final String ACCOUNT = "account";
 
 
    /**
          * 得到账户对象
     * @param request
     * @return
     */
    public User getAccount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(ACCOUNT);
    }
    
    /**
         *  得到单个属性值
     * @param request
     * @param attribute
     * @return
     */
    public String getValueByAttribute(HttpServletRequest request,String attribute) {
    	
    	HttpSession session = request.getSession();
        return (String) session.getAttribute(attribute);
    }
 
     /**
         * 设置属性值
     * @param request
     * @param account
     */
    public void setAccount(HttpServletRequest request,String name) {
    	User account = findOne(CommonConstant.BASEURL+"user/findById/"+name,User.class);
        HttpSession session = request.getSession();
        if (account != null) {
            session.setAttribute(ACCOUNT, account);
            
            session.setAttribute("userName", account.getUserName());
            session.setAttribute("isActive", account.getIsActive());
            session.setAttribute("region", account.getRegion());
            session.setAttribute("userIdentity", account.getUserIdentity());
            session.setAttribute("userMail", account.userMail);
            session.setAttribute("roleNames", account.getRolesName());
        }
    }

}
