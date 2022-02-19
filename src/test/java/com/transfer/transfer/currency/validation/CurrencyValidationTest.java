package com.transfer.transfer.currency.validation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CurrencyValidationTest {

    @Autowired
    CurrencyValidation currencyValidation;

    @Test
    public void shouldThrowExceptionIfCurrencyIsInvalid() {
    }

    @Test
    public void shouldNotThrowExceptionIfCurrencyIsValid() {
    }
}
