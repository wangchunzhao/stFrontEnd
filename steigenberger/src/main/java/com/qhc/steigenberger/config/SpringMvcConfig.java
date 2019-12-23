package com.qhc.steigenberger.config;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
 
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	DateFormat format = new ObjectMapperDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置转换器格式，和时区。
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(format)
//                .timeZone("Asia/Shanghai")
                .modulesToInstall(new ParameterNamesModule());
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
}