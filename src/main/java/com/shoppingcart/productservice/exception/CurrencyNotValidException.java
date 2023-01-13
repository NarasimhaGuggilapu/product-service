package com.shoppingcart.productservice.exception;

public class CurrencyNotValidException extends RuntimeException {
    public CurrencyNotValidException(String s) {
        super(s);
    }
}
