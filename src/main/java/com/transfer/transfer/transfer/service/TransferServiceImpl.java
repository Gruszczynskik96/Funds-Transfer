package com.transfer.transfer.transfer.service;

import com.transfer.transfer.calculate.service.CalculateService;
import com.transfer.transfer.currency.service.CurrencyService;
import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.currency.validation.CurrencyValidation;
import com.transfer.transfer.transfer.validation.TransferValidation;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransferServiceImpl implements TransferService {

    private final CurrencyService currencyService;
    private final AccountService accountService;
    private final TransferValidation transferValidation;
    private final CurrencyValidation currencyValidation;
    private final CalculateService calculateService;

    public TransferServiceImpl(CurrencyService currencyService,
                               AccountService accountService,
                               TransferValidation transferValidation,
                               CurrencyValidation currencyValidation,
                               CalculateService calculateService) {
        this.currencyService = currencyService;
        this.accountService = accountService;
        this.transferValidation = transferValidation;
        this.currencyValidation = currencyValidation;
        this.calculateService = calculateService;
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
                    .getCurrencyExchangeRates(currencySender);

            double exchangeRate = currencyValidation.validateExchangeRateExists(exchangeRates, currencyReceiver);

            double amountToSend = calculateService.multiply(exchangeRate, amount);

            transferValidation.validateTransferAmountIsGreaterThanZero(amountToSend);
            changeBalances(amount, accountModelFrom, amountToSend, accountModelTo);
        }
    }

    private void changeBalances(double amountToSubtract, AccountModel sender, double amountToAdd, AccountModel receiver) {
        transferValidation.validateUserHasCorrectBalance(amountToSubtract, sender.getUserID());
        subtractFunds(amountToSubtract, sender);
        addFunds(amountToAdd, receiver);
    }

    private void addFunds(double amount, AccountModel accountModel) {
        double newBalance = calculateService.sum(accountModel.getBalance(), amount);
        accountModel.setBalance(newBalance);
        accountService.saveAccount(accountModel);
    }

    private void subtractFunds(double amount, AccountModel accountModel) {
        double newBalance = calculateService.subtract(accountModel.getBalance(), amount);
        accountModel.setBalance(newBalance);
        accountService.saveAccount(accountModel);
    }

    private boolean checkIfAccountsHaveSameCurrencies(String currencyFrom, String currencyTo) {
        return currencyFrom.equals(currencyTo);
    }
}
