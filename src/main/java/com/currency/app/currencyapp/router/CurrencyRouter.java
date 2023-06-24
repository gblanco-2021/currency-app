package com.currency.app.currencyapp.router;

import com.currency.app.currencyapp.handler.CurrencyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CurrencyRouter {

    @Bean
    public RouterFunction<ServerResponse> routerCurrency(CurrencyHandler currencyHandler) {
        return RouterFunctions.route()
                .GET("pichincha/currency/all", currencyHandler::all)
                .POST("pichincha/currency/save", currencyHandler::create)
                .PUT("pichincha/currency/update", currencyHandler::update)
                .POST("pichincha/currency-exchange", currencyHandler::exchange)
                .GET("pichincha/currency-exchange/history", currencyHandler::showHistory)
                .build();
    }

}
