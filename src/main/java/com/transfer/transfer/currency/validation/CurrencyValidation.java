package com.transfer.transfer.currency.validation;

import com.transfer.transfer.currency.validation.exception.CurrencyException;

import java.util.Map;
import java.util.Optional;

public interface CurrencyValidation {
    /**
     * Validates of status code from external API is correct. Throws CurrencyException otherwise.
     * @param statusCode Number representation of status code received from external API.
     * @throws CurrencyException Throws CurrencyException if status code is invalid.
     */
    void validateStatusCode(int statusCode) throws CurrencyException;

    /**
     * Validates if String result parameter of GET request response body is valid.
     * @param result String result parameter from request response body.
     */
    void validateGetResultIsCorrect(String result);

    /**
     * This method validates and returns exchange rates if such exists, or throws CurrencyException if Optional is empty
     * @param exchangeRates Optional value of exchange rates. Either empty Optional, or Map.
     * @return Returns the Map of exchange rates.
     * @throws CurrencyException Throws CurrencyException if rates could not be extracted.
     */
    Map<String, Double> validateExchangeRatesAreReturned(Optional<Map<String, Double>> exchangeRates) throws CurrencyException;
}
