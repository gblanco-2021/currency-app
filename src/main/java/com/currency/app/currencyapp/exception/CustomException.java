package com.currency.app.currencyapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public class CustomException extends Exception{
    private HttpStatus status;

    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
