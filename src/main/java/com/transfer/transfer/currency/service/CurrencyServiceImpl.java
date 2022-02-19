package com.transfer.transfer.currency.service;

import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Override
    public CurrencyUnit getCurrencyUnit(String currency) {
        return Monetary.getCurrency(currency);
    }
}
