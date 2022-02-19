package com.transfer.transfer.currency.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.money.UnknownCurrencyException;

@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    CurrencyService currencyService;

    @Test
    public void shouldThrowExceptionOnInvalidCurrency() {
        Assertions.assertThrows(UnknownCurrencyException.class, () -> currencyService.getCurrencyUnit("ABC"));
        Assertions.assertThrows(UnknownCurrencyException.class, () -> currencyService.getCurrencyUnit("EURO"));
        Assertions.assertThrows(UnknownCurrencyException.class, () -> currencyService.getCurrencyUnit("UDP"));
    }

    @Test
    public void shouldNotThrowExceptionOnValidCurrency() {
        Assertions.assertDoesNotThrow(() -> currencyService.getCurrencyUnit("USD"));
        Assertions.assertDoesNotThrow(() -> currencyService.getCurrencyUnit("PLN"));
        Assertions.assertDoesNotThrow(() -> currencyService.getCurrencyUnit("EUR"));
    }
}
