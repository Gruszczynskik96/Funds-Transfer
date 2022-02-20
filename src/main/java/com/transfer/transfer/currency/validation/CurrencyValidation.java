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
     * @param result Optional of String result parameter from request response body.
     */
    void validateGetResultIsCorrect(Optional<String> result);

    /**
     * Validates and returns exchange rates if such exists, or throws CurrencyException if Optional is empty
     * @param exchangeRates Optional value of exchange rates. Either empty Optional, or Map.
     * @return Returns the Map of exchange rates.
     * @throws CurrencyException Throws CurrencyException if rates could not be extracted.
     */
    Map<String, Double> validateExchangeRatesAreReturned(Optional<Map<String, Double>> exchangeRates) throws CurrencyException;

    /**
     * Validates and returns exchange rate for given currency from map of exchange rates, or throws CurrencyException if exchange rate cannot be retrieved.
     * @param exchangeRates Map representation of exchange rates.
     * @param currency Currency ID searched in map of exchange rates.
     * @return Returns exchange rate of given currency.
     * @throws CurrencyException Throws CurrencyException if rate cannot be retrieved.
     */
    double validateExchangeRateExists(Map<String, Double> exchangeRates, String currency) throws CurrencyException;

    /**
     * Validates given currency is not empty or null
     * @param currency Optional of String
     */
    void validateCurrencyIsNotEmpty(Optional<String> currency) throws CurrencyException;
}
