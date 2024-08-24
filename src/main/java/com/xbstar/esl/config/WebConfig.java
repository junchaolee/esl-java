package com.xbstar.esl.config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xbstar.esl.filter.RequestLoggingFilter;
 
@Configuration
public class WebConfig {
 
    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestLoggingFilter());
        registrationBean.addUrlPatterns("/*"); // 匹配所有URL
        registrationBean.setOrder(1); // 设置顺序
        return registrationBean;
    }
}