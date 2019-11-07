package com.qhc.steigenberger.filter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.qhc.steigenberger.service.UserService;

@Component
@WebFilter(urlPatterns="/**",filterName="loginFilter")
public class LoginFilter implements Filter{
	Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    //排除不拦截的url
    private static final String[] excludePathPatterns = { "/steigenberger/loginIn", "/steigenberger/", "/steigenberger", "/steigenberger/menu/nologin"};
    private List<String> excludeLocations = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	excludeLocations = Arrays.asList(excludePathPatterns);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        // 获取请求url地址，不拦截excludePathPatterns中的url
        String url = req.getRequestURI();
        logger.info("request uri : " + url);
        if (excludeLocations.contains(url) || url.toLowerCase().endsWith(".html")  || url.toLowerCase().endsWith(".js") || url.toLowerCase().endsWith(".css")) {
            //放行，相当于第一种方法中LoginInterceptor返回值为true
            chain.doFilter(request, response);
            return;
        }

        logger.info("开始拦截.........");
        //业务代码
        HttpSession session = req.getSession();
        if (session == null || session.getAttribute(UserService.SESSION_USERIDENTITY) == null) {
        	logger.info("Session is null or Session is timeout.");
        	res.sendRedirect("/steigenberger/menu/nologin");
        	return;
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}