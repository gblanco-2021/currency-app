package com.currency.app.currencyapp.controller;

import com.currency.app.currencyapp.model.CurrencyExchange;
import com.currency.app.currencyapp.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pichincha")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/currencies")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CurrencyExchange> getAllTutorials() {
        return currencyService.findAll();
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CurrencyExchange> save(@RequestBody CurrencyExchange currencyExchange){
        return currencyService.store(currencyExchange);
    }

}
