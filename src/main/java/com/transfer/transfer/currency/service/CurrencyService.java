package com.transfer.transfer.currency.service;

import javax.money.CurrencyUnit;

public interface CurrencyService {
    /**
     * @param currency String representation of currency, abbreviation, for example Dollars are "USD".
     * @return returns CurrencyUnit from org.javamoney.moneta dependency, throws UnknownCurrencyException if currency is not found.
     */
    CurrencyUnit getCurrencyUnit(String currency);
}
