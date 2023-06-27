package com.currency.app.currencyapp.service;

import com.currency.app.currencyapp.dto.ExchangeDto;
import com.currency.app.currencyapp.exception.CustomErrorResponse;
import com.currency.app.currencyapp.exception.CustomException;
import com.currency.app.currencyapp.model.CurrencyExchange;
import com.currency.app.currencyapp.model.CurrencyOperation;
import com.currency.app.currencyapp.repository.CurrencyOperationRepository;
import com.currency.app.currencyapp.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyOperationRepository currencyOperationRepository;


    public Flux<CurrencyExchange> findAll() {
        return currencyRepository.findAll();
    }

    public Mono<CurrencyExchange> store(CurrencyExchange currencyExchange) {
        Mono<Boolean> exists = currencyRepository.findByCurrencyFromAndCurrencyTo(currencyExchange.getCurrencyFrom(), currencyExchange.getCurrencyTo())
                .hasElement();

        return exists.flatMap(bool -> bool
                ? Mono.error(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Ya existe este registro"))
                : currencyRepository.save(currencyExchange));
    }

    public Mono<CurrencyExchange> update(Mono<CurrencyExchange> currencyExchange) {
        return currencyExchange.flatMap(currency -> {
            Mono<Boolean> exists = currencyRepository.findByNotIdAndCurrency(currency.getId(), currency.getCurrencyFrom(), currency.getCurrencyTo())
                    .hasElements();
            return exists.flatMap(bool -> bool
                    ? Mono.error(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Ya existe este registro"))
                    : currencyRepository.save(currency));
        });
    }

    public Mono<CurrencyExchange> findCurrencyData(ExchangeDto exchangeDto) {
        Mono<CurrencyExchange> mono = currencyRepository.findByCurrencyFromAndCurrencyTo(exchangeDto.getFrom(), exchangeDto.getTo());
        return mono.hasElement().flatMap(bool -> bool
                ? mono
                : Mono.error(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "No existe estos tipos de moneda en nuestra BD"))
        );
    }

    public Mono<CurrencyOperation> saveOperation(CurrencyOperation currencyOperation) {
        return currencyOperationRepository.save(currencyOperation);
    }

    public Flux<CurrencyOperation> history(){
        return currencyOperationRepository.findAll();
    }

}
