package com.transfer.transfer.currency.service;

import com.transfer.transfer.currency.validation.exception.CurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    CurrencyService currencyService;

    @Test
    public void shouldThrowExceptionOnInvalidCurrency() {
        Assertions.assertThrows(CurrencyException.class, () -> currencyService.getCurrencyExchangeRates("Abd"));
        Assertions.assertThrows(CurrencyException.class, () -> currencyService.getCurrencyExchangeRates("USP"));
        Assertions.assertThrows(CurrencyException.class, () -> currencyService.getCurrencyExchangeRates(" "));
    }

    @Test
    public void shouldNotThrowExceptionOnValidCurrency() {
    }
}
