package com.transfer.transfer.currency.service;

import java.util.Map;
import java.util.Optional;

public interface CurrencyService {
    Optional<Map<String, Double>> getCurrencyExchangeRates(String currency);
}
