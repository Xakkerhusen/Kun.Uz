package com.example.kun_Uz_Lesson_1.controller.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfigFilter {
    @Autowired
    private TokenFilter tokenFilter;

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(tokenFilter);
        bean.addUrlPatterns("/region/adm");
        bean.addUrlPatterns("/region/adm/*");

        bean.addUrlPatterns("/articleType/adm");
        bean.addUrlPatterns("/articleType/adm/*");

        bean.addUrlPatterns("/category/adm");
        bean.addUrlPatterns("/category/adm/*");

        bean.addUrlPatterns("/profile/adm");
        bean.addUrlPatterns("/profile/adm/*");

        bean.addUrlPatterns("/emailHistory/adm");
        bean.addUrlPatterns("/emailHistory/adm/*");

        bean.addUrlPatterns("/smsHistory/adm");
        bean.addUrlPatterns("/smsHistory/adm/*");

        bean.addUrlPatterns("/article/mod");
        bean.addUrlPatterns("/article/mod/*");
        bean.addUrlPatterns("/article/pub");
        bean.addUrlPatterns("/article/pub/*");

        bean.addUrlPatterns("/attach/adm");
        bean.addUrlPatterns("/attach/adm/*");




        return bean;
    }


}
