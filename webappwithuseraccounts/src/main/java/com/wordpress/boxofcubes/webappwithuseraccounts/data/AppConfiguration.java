package com.wordpress.boxofcubes.webappwithuseraccounts.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.context.embedded.ServletRegistrationBean;

@Configuration
public class AppConfiguration {
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
    }

    @Bean
    public ServletRegistrationBean exampleServletBean() {
    ServletRegistrationBean bean = new ServletRegistrationBean(
      new CustomServlet(), "/exampleServlet/*");
    bean.setLoadOnStartup(1);
    return bean;
}


}



