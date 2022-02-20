package com.transfer.transfer.currency.service;

import java.util.Map;

public interface CurrencyService {
    /**
     * Returns the Currency exchange rates.
     * @param currency String ID representation of currency, Dollars are "USD", etc.
     * @return Map of exchange rates for given currency.
     */
    Map<String, Double> getCurrencyExchangeRates(String currency);
}
