package com.pagos.transactor.config;

import com.pagos.filters.AuthorizationFilter;
import com.pagos.filters.CorsFilter;
import com.pagos.health.CommitHealthIndicator;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactorConfiguration {

    @Bean
    public HealthIndicator commitHealthIndicator() {
        return new CommitHealthIndicator();
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthorizationFilter());
        registrationBean.setOrder(1);

        registrationBean.addUrlPatterns("/v1/transactions/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CorsFilter());
        registrationBean.setOrder(2);

        registrationBean.addUrlPatterns("/v1/transactions/*");
        registrationBean.addUrlPatterns("/v1/health");

        return registrationBean;
    }
}
