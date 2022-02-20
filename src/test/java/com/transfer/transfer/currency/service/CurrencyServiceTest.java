package com.transfer.transfer.currency.service;

import com.transfer.transfer.currency.validation.exception.CurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @Test
    public void shouldThrowExceptionOnInvalidCurrency() {
        Assertions.assertThrows(CurrencyException.class, () -> currencyService.getCurrencyExchangeRates("Abd"));
        Assertions.assertThrows(CurrencyException.class, () -> currencyService.getCurrencyExchangeRates("USP"));
        Assertions.assertThrows(CurrencyException.class, () -> currencyService.getCurrencyExchangeRates(" "));
    }

    @Test
    public void shouldNotThrowExceptionOnValidCurrency() {
        Assertions.assertDoesNotThrow(() -> currencyService.getCurrencyExchangeRates("USD"));
        Assertions.assertDoesNotThrow(() -> currencyService.getCurrencyExchangeRates("PLN"));
        Assertions.assertDoesNotThrow(() -> currencyService.getCurrencyExchangeRates("EUR"));
    }

    @Test
    public void shouldReturnExchangeRates() {
        Assertions.assertTrue(currencyService.getCurrencyExchangeRates("USD").size() > 0);
        Assertions.assertTrue(currencyService.getCurrencyExchangeRates("EUR").size() > 0);
        Assertions.assertTrue(currencyService.getCurrencyExchangeRates("PLN").size() > 0);
    }
}
