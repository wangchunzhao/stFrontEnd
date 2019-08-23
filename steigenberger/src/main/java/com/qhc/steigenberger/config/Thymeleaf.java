/**
 * 
 */
package com.qhc.steigenberger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author wang@dxc.com
 *
 */
@Configuration
public class Thymeleaf extends WebMvcConfigurationSupport  {
	 @Override
	 public void addViewControllers(ViewControllerRegistry registry) {
		 
		 registry.addViewController( "/" ).setViewName( "forward:/login.html" );
	        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
	        super.addViewControllers( registry );
		 
	 }
}
