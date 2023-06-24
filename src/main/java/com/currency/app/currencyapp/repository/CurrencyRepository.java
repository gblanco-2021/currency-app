package com.currency.app.currencyapp.repository;

import com.currency.app.currencyapp.model.CurrencyExchange;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends R2dbcRepository<CurrencyExchange, Long> {
}
