package com.transfer.transfer.currency.service;

import java.util.Map;

public interface CurrencyService {
    Map<String, Double> getCurrencyExchangeRates(String currency);
}
