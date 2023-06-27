package com.currency.app.currencyapp.service;

import com.currency.app.currencyapp.exception.CustomException;
import com.currency.app.currencyapp.model.CurrencyExchange;
import com.currency.app.currencyapp.repository.CurrencyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {
    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void store_NewCurrencyExchange_ReturnsSavedCurrencyExchange() {
        CurrencyExchange expectedCurrencyExchange = new CurrencyExchange(1L, "USD", "PEN", 1.5);

        Mockito.when(currencyRepository.save(new CurrencyExchange(1L, "USD", "PEN", 1.5)))
                .thenReturn(Mono.just(expectedCurrencyExchange));

        Mockito.when(currencyRepository.findByCurrencyFromAndCurrencyTo(Mockito.any(String.class), Mockito.any(String.class)))
                .thenReturn(Mono.empty());

        Mono<CurrencyExchange> result = currencyService.store(new CurrencyExchange(2L, "DROP", "PEN", 1.5));

        StepVerifier.create(result)
                .expectNextMatches(actualCurrencyExchange -> {
                    System.out.println(actualCurrencyExchange.getId() + " - " + expectedCurrencyExchange.getId());
                    assertEquals(expectedCurrencyExchange.getId(), actualCurrencyExchange.getId());
                    assertEquals(expectedCurrencyExchange.getCurrencyFrom(), actualCurrencyExchange.getCurrencyFrom());
                    assertEquals(expectedCurrencyExchange.getCurrencyTo(), actualCurrencyExchange.getCurrencyTo());
                    return true;
                })
                .verifyComplete();
    }

//    @Test
//    void store_ExistingCurrencyExchange_ReturnsError() {
//        // Arrange
//        CurrencyExchange currencyExchange = new CurrencyExchange(1L, "USD", "PEN", 1.5);
//        Mockito.when(currencyRepository.findByCurrencyFromAndCurrencyTo(currencyExchange.getCurrencyFrom(), currencyExchange.getCurrencyTo()))
//                .thenReturn(Mono.just(currencyExchange));
//
//        // Act
//        Mono<CurrencyExchange> result = currencyService.store(currencyExchange);
//
//        // Assert
//        StepVerifier.create(result)
//                .expectErrorMatches(throwable -> throwable instanceof CustomException
//                        && ((CustomException) throwable).getStatus() == HttpStatus.INTERNAL_SERVER_ERROR
//                        && throwable.getMessage().equals("Ya existe este registro"))
//                .verify();
//        Mockito.verify(currencyRepository, Mockito.times(1)).findByCurrencyFromAndCurrencyTo(currencyExchange.getCurrencyFrom(), currencyExchange.getCurrencyTo());
//        Mockito.verify(currencyRepository, Mockito.never()).save(any());
//    }
}