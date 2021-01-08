package com.wordpress.boxofcubes.webappwithuseraccounts.data;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class AppConfiguration {
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
    }

    @Bean
    public ServletRegistrationBean chartServletBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new ChartServlet(), "/data/graph/view/*");
        bean.setLoadOnStartup(1);
        return bean;
    }


}



