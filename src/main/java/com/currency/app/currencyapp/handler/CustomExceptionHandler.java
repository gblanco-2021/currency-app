package com.currency.app.currencyapp.handler;

import com.currency.app.currencyapp.exception.CustomErrorResponse;
import com.currency.app.currencyapp.exception.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof CustomException) {
            CustomException customException = (CustomException) ex;
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    customException.getStatus().value(),
                    customException.getMessage()
            );

            exchange.getResponse().setStatusCode(customException.getStatus());
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return exchange.getResponse().writeWith(Mono.just(
                    exchange.getResponse().bufferFactory().wrap(
                            serializeErrorResponse(errorResponse)
                    )
            ));
        }

        // Si no es una excepción personalizada, puedes manejar otros tipos de excepciones aquí

        // Delega el manejo de otras excepciones a otros manejadores de excepciones globales
        return Mono.error(ex);
    }

    private byte[] serializeErrorResponse(CustomErrorResponse errorResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException e) {
            return new byte[0];
        }
    }
}
