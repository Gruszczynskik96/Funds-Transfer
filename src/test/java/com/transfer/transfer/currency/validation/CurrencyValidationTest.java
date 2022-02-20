package com.transfer.transfer.currency.validation;

import com.transfer.transfer.currency.validation.exception.CurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class CurrencyValidationTest {

    private static final String RESULT_KEY_EXPECTED = "success";

    @Autowired
    private CurrencyValidation currencyValidation;

    @Test
    public void shouldThrowExceptionWhenIncorrectStatus() {
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateStatusCode(0));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateStatusCode(404));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateStatusCode(500));
    }

    @Test
    public void shouldNotThrowExceptionWhenCorrectState() {
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateStatusCode(200));
    }

    @Test
    public void shouldThrowExceptionWhenResultParameterIsIncorrect() {
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateGetResultIsCorrect(Optional.empty()));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateGetResultIsCorrect(Optional.of("error")));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateGetResultIsCorrect(Optional.of("failure")));
    }

    @Test
    public void shouldNotThrowExceptionWhenResultParameterIsCorrect() {
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateGetResultIsCorrect(Optional.of(RESULT_KEY_EXPECTED)));
    }
}
