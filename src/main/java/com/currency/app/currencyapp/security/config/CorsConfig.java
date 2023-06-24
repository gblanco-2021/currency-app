package com.currency.app.currencyapp.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //apply to all endpoints in app
                .allowedMethods("GET","POST", "PUT", "DELETE")
                .allowedOriginPatterns("*")
                .allowCredentials(true);
    }
}
