package com.transfer.transfer.transfer.service;

import com.transfer.transfer.currency.service.CurrencyService;
import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.transfer.validation.TransferValidation;
import com.transfer.transfer.transfer.validation.exception.TransferException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

@Service
public class TransferServiceImpl implements TransferService {

    private final CurrencyService currencyService;
    private final AccountService accountService;
    private final TransferValidation transferValidation;

    public TransferServiceImpl(CurrencyService currencyService,
                               AccountService accountService,
                               TransferValidation transferValidation) {
        this.currencyService = currencyService;
        this.accountService = accountService;
        this.transferValidation = transferValidation;
    }

    @Override
    public void transferMoneyBetweenAccounts(long userIDFrom, long userIDTo, double amount) {
        AccountModel accountModelFrom = accountService.getAccount(userIDFrom);
        AccountModel accountModelTo = accountService.getAccount(userIDTo);
        String currencySender = accountModelFrom.getCurrency();
        String currencyReceiver = accountModelTo.getCurrency();

        if (checkIfAccountsHaveSameCurrencies(accountModelFrom.getCurrency(), accountModelTo.getCurrency())) {
            changeBalances(amount, accountModelFrom, amount, accountModelTo);
        } else {
            Map<String, Double> exchangeRates = currencyService
                    .getCurrencyExchangeRates(currencySender)
                    .orElseThrow(() -> new TransferException(HttpStatus.NOT_FOUND, "Cannot retrieve currency exchange rates for currency: " + currencySender));

            double exchangeRate = Optional
                    .of(exchangeRates.get(currencyReceiver))
                    .orElseThrow(() -> new TransferException(HttpStatus.NOT_FOUND, "Cannot retrieve currency exchange rates for currency: " + currencyReceiver));

            double amountToSend = calculateExchangeRate(exchangeRate, amount);

            changeBalances(amount, accountModelFrom, amountToSend, accountModelTo);
        }
    }

    private void changeBalances(double amountToSubtract, AccountModel sender, double amountToAdd, AccountModel receiver) {
        transferValidation.validateUserHasCorrectBalance(amountToSubtract, sender.getUserID());
        subtractFunds(amountToSubtract, sender);
        addFunds(amountToAdd, receiver);
    }

    private void addFunds(double amount, AccountModel accountModel) {
        double newBalance = accountModel.getBalance() + amount;
        accountModel.setBalance(newBalance);
        accountService.saveAccount(accountModel);
    }

    private void subtractFunds(double amount, AccountModel accountModel) {
        double newBalance = accountModel.getBalance() - amount;
        accountModel.setBalance(newBalance);
        accountService.saveAccount(accountModel);
    }

    private double calculateExchangeRate(double exchangeRate, double amount) {
        BigDecimal bigDecimal = new BigDecimal(amount * exchangeRate).setScale(2, RoundingMode.DOWN);
        return bigDecimal.doubleValue();
    }

    private boolean checkIfAccountsHaveSameCurrencies(String currencyFrom, String currencyTo) {
        return currencyFrom.equals(currencyTo);
    }
}
