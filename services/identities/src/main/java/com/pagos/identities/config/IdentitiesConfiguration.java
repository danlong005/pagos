package com.pagos.identities.config;

import com.pagos.filters.CorsFilter;
import health.CommitHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class IdentitiesConfiguration {
    @Bean
    public HealthIndicator commitHealthIndicator() {
        return new CommitHealthIndicator();
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CorsFilter());
        registrationBean.setOrder(1);

        registrationBean.addUrlPatterns("/v1/token");
        registrationBean.addUrlPatterns("/v1/health");

        return registrationBean;
    }
}