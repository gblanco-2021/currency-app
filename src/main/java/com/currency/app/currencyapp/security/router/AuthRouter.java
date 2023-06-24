package com.currency.app.currencyapp.security.router;

import com.currency.app.currencyapp.security.handler.AuthHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class AuthRouter {
    @Bean
    public RouterFunction<ServerResponse> routerAuth(AuthHandler authHandler) {
        return RouterFunctions.route()
                .POST("auth/login", authHandler::login)
                .POST("auth/create", authHandler::create)
                .build();
    }
}
