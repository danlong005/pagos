package com.pagos.vault.config;

import com.pagos.filters.AuthorizationFilter;
import com.pagos.filters.CorsFilter;
import health.CommitHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaultConfiguration {
    @Bean
    public HealthIndicator commitHealthIndicator() {
        return new CommitHealthIndicator();
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthorizationFilter());
        registrationBean.setOrder(1);

        registrationBean.addUrlPatterns("/v1/paymentMethods");
        registrationBean.addUrlPatterns("/v1/paymentMethods/*");

        return registrationBean;
    }

    @Bean FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CorsFilter());
        registrationBean.setOrder(2);

        registrationBean.addUrlPatterns("/v1/paymentMethods");
        registrationBean.addUrlPatterns("/v1/paymentMethods/*");
        registrationBean.addUrlPatterns("/v1/health");

        return registrationBean;
    }
}
