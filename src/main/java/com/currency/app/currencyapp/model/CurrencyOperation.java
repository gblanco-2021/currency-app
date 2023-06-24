package com.currency.app.currencyapp.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table(name = "currency_operation")
public class CurrencyOperation {

    @Id
    private String id;
    @Column("user_id")
    private Long userId;
    @Column("currency_from")
    private Double currencyFrom;
    @Column("currency_to")
    private String currencyTo;
    @Column("conversion")
    private Double conversion;
    @Column("amount")
    private Double amount;
    @Column("amount_converted")
    private Double amountConverted;


}
