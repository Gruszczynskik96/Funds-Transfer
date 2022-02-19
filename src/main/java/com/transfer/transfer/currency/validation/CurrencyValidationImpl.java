package com.transfer.transfer.currency.validation;

import com.transfer.transfer.currency.service.CurrencyService;
import com.transfer.transfer.currency.validation.exception.CurrencyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.money.UnknownCurrencyException;

@Service
public class CurrencyValidationImpl implements CurrencyValidation {

    private static final String CURRENCY_NOT_FOUND_RESPONSE = "Currency not found: ";

    private final CurrencyService currencyService;

    public CurrencyValidationImpl(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public void validateCurrency(String currency) {
        try {
            currencyService.getCurrencyUnit(currency);
        } catch (UnknownCurrencyException unknownCurrencyException) {
            throw new CurrencyException(HttpStatus.NOT_FOUND, CURRENCY_NOT_FOUND_RESPONSE + currency);
        }
    }
}
