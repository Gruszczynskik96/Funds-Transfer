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

        if (checkIfAccountsHaveSameCurrencies(accountModelFrom.getCurrency(), accountModelTo.getCurrency())) {
            double newBalanceFrom = accountModelFrom.getBalance() - amount;
            accountModelFrom.setBalance(newBalanceFrom);
            accountService.saveAccount(accountModelFrom);

            double newBalanceTo = accountModelTo.getBalance() + amount;
            accountModelTo.setBalance(newBalanceTo);
            accountService.saveAccount(accountModelTo);
        }
    }

    private boolean checkIfAccountsHaveSameCurrencies(String currencyFrom, String currencyTo) {
        return currencyFrom.equals(currencyTo);
    }
}
