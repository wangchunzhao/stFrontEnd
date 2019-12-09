package com.qhc.steigenberger.filter;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.service.UserService;

@Component
@WebFilter(urlPatterns="/**",filterName="loginFilter")
public class LoginFilter implements Filter{
	Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    //排除不拦截的url
    private List<String> excludeLocations = Arrays.asList(new String[]  { "/steigenberger/loginIn", 
    		"/steigenberger/", 
    		"/steigenberger", 
    		"/steigenberger/menu/nologin"});
    private List<String> excludePrefixs = Arrays.asList(new String[] {"/steigenberger/assets",
    		"/steigenberger/bootstrap-date",
    		"/steigenberger/bootstrap-fileinput-master",
    		"/steigenberger/bootstrap-table-master",
    		"/steigenberger/css",
    		"/steigenberger/drag",
    		"/steigenberger/fonts",
    		"/steigenberger/images",
    		"/steigenberger/index.html",
    		"/steigenberger/js",
    		"/steigenberger/lib",
    		"/steigenberger/page",
    		"/steigenberger/pageJs",
    		"/steigenberger/view"});

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        // 获取请求url地址，不拦截excludePathPatterns中的url
        String url = req.getRequestURI();
        logger.debug("request uri : " + url);
        boolean isStaticAssets = false;
        for (String prefix : excludePrefixs) {
        	if(url.startsWith(prefix)) {
        		isStaticAssets = true;
        		break;
        	}
		}
        
        if (excludeLocations.contains(url) || isStaticAssets) {
            //放行，相当于第一种方法中LoginInterceptor返回值为true
//            chain.doFilter(request, response);
//            return;
        } else {
	        logger.info("开始拦截.........");
	        boolean isNotJson = !(req.getHeader("accept").indexOf("application/json") > -1 
	        		|| (req.getHeader("X-Requested-With") != null 
	        			&& req.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1));
	        
	        logger.debug("Header Accept : {}", req.getHeader("accept"));
	        logger.debug("Header X-Requested-With : {}", req.getHeader("X-Requested-With"));

	        //业务代码
	        HttpSession session = req.getSession();
	        if (session == null || session.getAttribute(UserService.SESSION_USERIDENTITY) == null) {
	        	logger.info("No Authorization info.");
	        	
	        	if (isNotJson) {
	        		res.sendRedirect("/steigenberger/menu/nologin");
	        	} else {
	        		res.setStatus(HttpStatus.OK.value());
	    			// 设置ContentType
	        		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    			// 避免乱码
	        		res.setCharacterEncoding("UTF-8");
	        		res.setHeader("Cache-Control", "no-cache, must-revalidate");
	    			
	        		PrintWriter writer = res.getWriter();
	        		Result r = Result.logout();
	        		writer.write(new ObjectMapper().writeValueAsString(r));
					writer.flush();
					writer.close();
	        	}
	        	return;
	        }
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}