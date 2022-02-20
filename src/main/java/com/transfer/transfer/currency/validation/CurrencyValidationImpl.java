package com.transfer.transfer.currency.validation;

import com.transfer.transfer.currency.validation.exception.CurrencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CurrencyValidationImpl implements CurrencyValidation {

    private static final int SUCCESS_HTTP_REQUEST_CODE = 200;

    private static final String EXCHANGE_RATES_NOT_FOUND_MESSAGE = "Exchange rates could not be found!";
    private static final String CURRENCY_ERROR_RESPONSE_TEXT = "Currency exchange rates could not be extracted for: ";
    private static final String CURRENCY_CANNOT_BE_EMPTY = "Currency cannot be empty!";
    private static final String RESULT_KEY_EXPECTED = "success";

    @Override
    public void validateStatusCode(int statusCode) throws CurrencyException {
        if (statusCode != SUCCESS_HTTP_REQUEST_CODE) {
            log.error("Could not get exchange rates for currency, status code: {}", statusCode);
            throw new CurrencyException(HttpStatus.NOT_FOUND, EXCHANGE_RATES_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void validateGetResultIsCorrect(Optional<String> result) {
        if (result.isEmpty() || !result.get().equals(RESULT_KEY_EXPECTED)) {
            throw new CurrencyException(HttpStatus.NOT_FOUND, EXCHANGE_RATES_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public Map<String, Double> validateExchangeRatesAreReturned(Optional<Map<String, Double>> exchangeRates) throws CurrencyException {
        return exchangeRates
                .orElseThrow(() -> new CurrencyException(HttpStatus.NOT_FOUND, EXCHANGE_RATES_NOT_FOUND_MESSAGE));
    }

    @Override
    public double validateExchangeRateExists(Map<String, Double> exchangeRates, String currency) throws CurrencyException {
        return Optional.ofNullable(exchangeRates.get(currency))
                .orElseThrow(() -> new CurrencyException(HttpStatus.NOT_FOUND, CURRENCY_ERROR_RESPONSE_TEXT + currency));
    }

    @Override
    public void validateCurrencyIsNotEmpty(Optional<String> currency) throws CurrencyException {
        if (currency.isEmpty() || currency.get().trim().isEmpty()) {
            throw new CurrencyException(HttpStatus.NOT_ACCEPTABLE, CURRENCY_CANNOT_BE_EMPTY);
        }
    }
}
