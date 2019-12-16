/**
 * 
 */
package com.qhc.steigenberger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author wang@dxc.com
 *
 */
@Configuration
public class Thymeleaf extends WebMvcConfigurationSupport  {
	 @Override
	 protected void addViewControllers(ViewControllerRegistry registry) {
		 
//		 registry.addViewController( "/" ).setViewName( "login" );
	     registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
	     super.addViewControllers( registry );
		 
	 }
	 @Override
	 protected void addResourceHandlers(ResourceHandlerRegistry registry) {
	      registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	      super.addResourceHandlers( registry );
	 }
	 @Override
	 protected void addInterceptors(InterceptorRegistry registry) {
	     //  registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/emp/login","/js/**","/css/**","/images/**");
	     //   super.addInterceptors(registry);
	 }
}
