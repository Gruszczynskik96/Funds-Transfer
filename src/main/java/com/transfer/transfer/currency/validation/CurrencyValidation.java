package com.transfer.transfer.currency.validation;

public interface CurrencyValidation {
    /**
     * Validates if currency exists in org.javamoney.moneta dependency, throws CurrencyException if currency is not found.
     * @param currency String representation of currency, abbreviation, for example Dollars are "USD".
     */
    void validateCurrency(String currency);
}
