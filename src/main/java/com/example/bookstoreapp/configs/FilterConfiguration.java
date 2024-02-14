package com.example.bookstoreapp.configs;

import com.example.bookstoreapp.auth.AuthService;
import com.example.bookstoreapp.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

  @Bean
  public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(@Autowired AuthService authService) {
    FilterRegistrationBean<AuthenticationFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(new AuthenticationFilter(authService));
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(2);

    return registrationBean;
  }
}
