package com.favorites;

import com.favorites.comm.filter.SecurityFilter;
import org.assertj.core.util.Lists;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean filterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecurityFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        List<String> list = Lists.newArrayList();
        list.add("/swagger-resources/**");
        list.add("/webjars/**");
        list.add("/v2/**");
        list.add("/swagger-ui.html/**");
        list.add("/configuration/**");
        registration.setUrlPatterns(list);
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    } 


}



