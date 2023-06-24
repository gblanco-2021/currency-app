package com.currency.app.currencyapp.handler;

import com.currency.app.currencyapp.dto.CurrencyExchangeDto;
import com.currency.app.currencyapp.model.CurrencyExchange;
import com.currency.app.currencyapp.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CurrencyHandler {

    @Autowired
    private CurrencyService currencyService;

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CurrencyExchangeDto> currencyExchangeDtoMono = request.bodyToMono(CurrencyExchangeDto.class);
        return currencyExchangeDtoMono
                .map(dto -> CurrencyExchange
                        .builder()
                        .currencyFrom(dto.getFrom())
                        .currencyTo(dto.getTo())
                        .conversion(dto.getConversion())
                        .build())
                .flatMap(dto -> currencyService.store(dto))
                .flatMap(currencyExchange -> {
                    CurrencyExchangeDto responseDto = CurrencyExchangeDto
                            .builder()
                            .id(currencyExchange.getId())
                            .from(currencyExchange.getCurrencyFrom())
                            .to(currencyExchange.getCurrencyTo())
                            .conversion(currencyExchange.getConversion())
                            .build();
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(responseDto);
                }).doOnError(throwable -> {
                    System.out.println("My error: " + throwable.getMessage());
                    throwable.printStackTrace();
                });
    }
}
