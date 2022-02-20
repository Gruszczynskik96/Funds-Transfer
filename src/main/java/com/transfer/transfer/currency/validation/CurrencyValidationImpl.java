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

    private static final String CURRENCY_ERROR_RESPONSE_TEXT = "Currency code could not be extracted!";
    private static final String EXCHANGE_RATES_NOT_FOUND_MESSAGE = "Exchange rates could not be found!";
    private static final String ERROR_RESULT_KEY = "error";

    @Override
    public void validateStatusCode(int statusCode) throws CurrencyException {
        if (statusCode != SUCCESS_HTTP_REQUEST_CODE) {
            log.error("Could not get exchange rates for currency, status code: {}", statusCode);
            throw new CurrencyException(HttpStatus.NOT_FOUND, CURRENCY_ERROR_RESPONSE_TEXT);
        }
    }

    @Override
    public void validateGetResultIsCorrect(String result) {
        if (result.equals(ERROR_RESULT_KEY)) {
            log.error(CURRENCY_ERROR_RESPONSE_TEXT);
            throw new CurrencyException(HttpStatus.NOT_FOUND, CURRENCY_ERROR_RESPONSE_TEXT);
        }
    }

    @Override
    public Map<String, Double> validateExchangeRatesAreReturned(Optional<Map<String, Double>> exchangeRates) throws CurrencyException {
        return exchangeRates
                .orElseThrow(() -> new CurrencyException(HttpStatus.NOT_FOUND, EXCHANGE_RATES_NOT_FOUND_MESSAGE));
    }
}
