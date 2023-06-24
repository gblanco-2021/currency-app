package com.currency.app.currencyapp.service;

import com.currency.app.currencyapp.model.CurrencyExchange;
import com.currency.app.currencyapp.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Flux<CurrencyExchange> findAll(){
        return currencyRepository.findAll();
    }

    public Mono<CurrencyExchange> store(CurrencyExchange currencyExchange) {
        return currencyRepository.save(currencyExchange);
    }



}
