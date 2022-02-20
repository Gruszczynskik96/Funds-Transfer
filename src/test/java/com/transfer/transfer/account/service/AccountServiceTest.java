package com.transfer.transfer.account.service;

import com.transfer.transfer.account.model.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void assertDatabaseEntriesExist() {
        Assertions.assertEquals(accountService.getAccounts().size(), 3);
    }

    @Test
    public void assertUserModelIsEmptyIfNotPresent() {
        Assertions.assertNull(accountService.getAccount(10000L));
        Assertions.assertNull(accountService.getAccount(10004L));
        Assertions.assertNull(accountService.getAccount(10005L));
    }

    @Test
    public void assertUserEntriesAreCorrectlyInserted() {
        Assertions.assertTrue(checkIfUserModelEqualsValues(accountService.getAccount(10001L), 10001L, "USD", 150.0));
        Assertions.assertTrue(checkIfUserModelEqualsValues(accountService.getAccount(10002L), 10002L, "PLN", 100.0));
        Assertions.assertTrue(checkIfUserModelEqualsValues(accountService.getAccount(10003L), 10003L, "EUR", 50.0));
    }

    private boolean checkIfUserModelEqualsValues(AccountModel accountModel, long expectedUserID, String expectedCurrency, double expectedBalance) {
        return accountModel.getUserID() == expectedUserID && accountModel.getCurrency().equals(expectedCurrency) && accountModel.getBalance() == expectedBalance;
    }
}
