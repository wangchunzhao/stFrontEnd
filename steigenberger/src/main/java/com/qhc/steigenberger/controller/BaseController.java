package com.qhc.steigenberger.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.UserService;

/**
 * 	用户session
 * @author lizuoshan
 *
 */
public abstract class BaseController{

	
    
    @Autowired
    UserService userService;
 
 
    /**
          * 得到账户对象
     * @param request
     * @return
     */
    public User getAccount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(userService.ACCOUNT);
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
    public void setAccount(HttpServletRequest request,String identityName) {
//    	User account = userService.selectUserIdentity(identityName);
        HttpSession session = request.getSession();
        session.setAttribute(userService.SESSION_USERIDENTITY, identityName);
//        if (account != null) {
//            session.setAttribute(userService.ACCOUNT, account);
//            session.setAttribute("userName", account.getUserName());
//            session.setAttribute("isActive", account.getIsActive());
//            session.setAttribute("region", account.getRegion());
//            session.setAttribute("userIdentity", account.getUserIdentity());
//            session.setAttribute("userMail", account.userMail);
//            session.setAttribute("roleNames", account.getRolesName());
//            session.setAttribute("operationNames", account.getOperationNames());
//        }
    }
    
    

}
