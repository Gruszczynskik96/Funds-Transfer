package com.transfer.transfer.currency.validation;

import com.transfer.transfer.currency.validation.exception.CurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CurrencyValidationTest {

    @Autowired
    CurrencyValidation currencyValidation;

    @Test
    public void shouldThrowExceptionIfCurrencyIsInvalid() {
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateCurrency("ABC"));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateCurrency("EURO"));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateCurrency("UDP"));
    }

    @Test
    public void shouldNotThrowExceptionIfCurrencyIsValid() {
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateCurrency("EUR"));
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateCurrency("USD"));
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateCurrency("PLN"));
    }
}
