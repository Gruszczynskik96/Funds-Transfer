package com.transfer.transfer.currency.validation;

import com.transfer.transfer.currency.validation.exception.CurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Map;
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

    @Test
    public void shouldThrowExceptionWhenExchangeRatesAreEmpty() {
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateExchangeRatesAreReturned(Optional.empty()));
    }

    @Test
    public void shouldNotThrowExceptionWhenExchangeRatesAreReturned() {
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateExchangeRatesAreReturned(Optional.of(Collections.emptyMap())));
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateExchangeRatesAreReturned(Optional.of(Map.ofEntries(Map.entry("v", 0.0)))));
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateExchangeRatesAreReturned(Optional.of(Map.ofEntries(Map.entry("USD", 0.0)))));
    }

    @Test
    public void shouldThrowExceptionWhenExchangeRateCannotBeRetrievedForGivenCurrency() {
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateExchangeRateExists(Collections.emptyMap(), ""));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateExchangeRateExists(Collections.emptyMap(), "ABC"));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateExchangeRateExists(Collections.emptyMap(), "USD"));
    }

    @Test
    public void shouldNotThrowExceptionWhenExchangeRateCanBeRetrievedForGivenCurrency() {
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateExchangeRateExists(Map.ofEntries(Map.entry("USD", 0.0)), "USD"));
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateExchangeRateExists(Map.ofEntries(Map.entry("PLN", 0.0), Map.entry("USD", 0.0)), "PLN"));
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateExchangeRateExists(Map.ofEntries(Map.entry("EUR", 1.0)), "EUR"));
    }

    @Test
    public void shouldThrowExceptionWhenCurrencyStringIsEmpty() {
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateCurrencyIsNotEmpty(Optional.empty()));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateCurrencyIsNotEmpty(Optional.of("")));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateCurrencyIsNotEmpty(Optional.of(" ")));
        Assertions.assertThrows(CurrencyException.class, () -> currencyValidation.validateCurrencyIsNotEmpty(Optional.of("    ")));
    }

    @Test
    public void shouldNotThrowExceptionWhenCurrencyStringIsPresent() {
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateCurrencyIsNotEmpty(Optional.of("s")));
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateCurrencyIsNotEmpty(Optional.of("USD")));
        Assertions.assertDoesNotThrow(() -> currencyValidation.validateCurrencyIsNotEmpty(Optional.of("String")));
    }
}
