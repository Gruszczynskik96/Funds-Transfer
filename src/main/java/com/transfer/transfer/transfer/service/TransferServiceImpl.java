package com.transfer.transfer.transfer.service;

import com.transfer.transfer.currency.service.CurrencyService;
import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import org.springframework.stereotype.Service;


@Service
public class TransferServiceImpl implements TransferService {

    private final CurrencyService currencyService;
    private final AccountService accountService;

    public TransferServiceImpl(CurrencyService currencyService,
                               AccountService accountService) {
        this.currencyService = currencyService;
        this.accountService = accountService;
    }

    @Override
    public void transferMoneyBetweenAccounts(long userIDFrom, long userIDTo, double amount) {
        AccountModel accountModelFrom = accountService.getAccount(userIDFrom).get();
        AccountModel accountModelTo = accountService.getAccount(userIDTo).get();
        String currencySender = accountModelFrom.getCurrency();
        String currencyReceiver = accountModelTo.getCurrency();

        if (checkIfAccountsHaveSameCurrencies(accountModelFrom.getCurrency(), accountModelTo.getCurrency())) {
            subtractFunds(amount, accountModelFrom);
            addFunds(amount, accountModelTo);
        } else {
        }
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

    private boolean checkIfAccountsHaveSameCurrencies(String currencyFrom, String currencyTo) {
        return currencyFrom.equals(currencyTo);
    }
}
