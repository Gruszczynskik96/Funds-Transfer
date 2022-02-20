package com.transfer.transfer.transfer.service;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.currency.service.CurrencyService;
import com.transfer.transfer.transfer.validation.exception.TransferException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

@SpringBootTest
public class TransferServiceTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private CurrencyService currencyService;

    @Autowired
    private TransferService transferService;

    @Test
    public void shouldChangeBalancesOnTwoAccountsWithSameCurrency() {
        Mockito.when(accountService.getAccount(10001L)).thenReturn(prepareAccountModel(10001L, "USD", 100.0));
        Mockito.when(accountService.getAccount(10002L)).thenReturn(prepareAccountModel(10002L, "USD", 50.0));

        transferService.transferMoneyBetweenAccounts(10001L, 10002L, 10.00);

        Assertions.assertEquals(90.0, accountService.getAccount(10001L).getBalance());
        Assertions.assertEquals(60.0, accountService.getAccount(10002L).getBalance());

        Mockito.when(accountService.getAccount(10003L)).thenReturn(prepareAccountModel(10003L, "USD", 50.0));
        Mockito.when(accountService.getAccount(10004L)).thenReturn(prepareAccountModel(10004L, "USD", 50.0));

        transferService.transferMoneyBetweenAccounts(10004L, 10003L, 50.00);

        Assertions.assertEquals(100.0, accountService.getAccount(10003L).getBalance());
        Assertions.assertEquals(0.0, accountService.getAccount(10004L).getBalance());

        Mockito.when(accountService.getAccount(10005L)).thenReturn(prepareAccountModel(10005L, "USD", 127.58));
        Mockito.when(accountService.getAccount(10006L)).thenReturn(prepareAccountModel(10006L, "USD", 52.34));

        transferService.transferMoneyBetweenAccounts(10005L, 10006L, 127.57);

        Assertions.assertEquals(0.01, accountService.getAccount(10005L).getBalance());
        Assertions.assertEquals(179.91, accountService.getAccount(10006L).getBalance());
    }

    @Test
    public void balanceShouldChangeWhenCurrenciesAreDifferent() {
        Mockito.when(currencyService.getCurrencyExchangeRates(Mockito.anyString())).thenReturn(Map.ofEntries(Map.entry("PLN", 1.0)));

        Mockito.when(accountService.getAccount(10001L)).thenReturn(prepareAccountModel(10001L, "USD", 100.0));
        Mockito.when(accountService.getAccount(10002L)).thenReturn(prepareAccountModel(10001L, "PLN", 50.0));

        transferService.transferMoneyBetweenAccounts(10001L, 10002L, 50.0);

        Assertions.assertEquals(50.0, accountService.getAccount(10001L).getBalance());
        Assertions.assertEquals(100.0, accountService.getAccount(10002L).getBalance());

        Mockito.when(currencyService.getCurrencyExchangeRates(Mockito.anyString())).thenReturn(Map.ofEntries(Map.entry("USD", 1.13)));

        Mockito.when(accountService.getAccount(10003L)).thenReturn(prepareAccountModel(10003L, "EUR", 100.0));
        Mockito.when(accountService.getAccount(10004L)).thenReturn(prepareAccountModel(10004L, "USD", 50.0));

        transferService.transferMoneyBetweenAccounts(10003L, 10004L, 9.0);

        Assertions.assertEquals(91.0, accountService.getAccount(10003L).getBalance());
        Assertions.assertEquals(60.17, accountService.getAccount(10004L).getBalance());
    }

    @Test
    public void balanceShouldNotChangeIfInsufficientAmounts() {
        Mockito.when(accountService.getAccount(10001L)).thenReturn(prepareAccountModel(10001L, "USD", 100.0));
        Mockito.when(accountService.getAccount(10002L)).thenReturn(prepareAccountModel(10001L, "USD", 50.0));

        try {
            transferService.transferMoneyBetweenAccounts(10001L, 10002L, 101.00);
        } catch (TransferException exception) {
            Assertions.assertEquals(100.0, accountService.getAccount(10001L).getBalance());
            Assertions.assertEquals(50.0, accountService.getAccount(10002L).getBalance());
        }

        Mockito.when(currencyService.getCurrencyExchangeRates(Mockito.anyString())).thenReturn(Map.ofEntries(Map.entry("USD", 0.14)));

        Mockito.when(accountService.getAccount(10003L)).thenReturn(prepareAccountModel(10003L, "EUR", 0.01));
        Mockito.when(accountService.getAccount(10004L)).thenReturn(prepareAccountModel(10004L, "USD", 50.0));

        try {
            transferService.transferMoneyBetweenAccounts(10003L, 10004L, 0.01);
        } catch (TransferException exception) {
            Assertions.assertEquals(0.01, accountService.getAccount(10003L).getBalance());
            Assertions.assertEquals(50.0, accountService.getAccount(10004L).getBalance());
        }
    }

    private AccountModel prepareAccountModel(long userID, String currency, double balance) {
        AccountModel accountModel = new AccountModel();
        accountModel.setUserID(userID);
        accountModel.setCurrency(currency);
        accountModel.setBalance(balance);
        return accountModel;
    }
}
