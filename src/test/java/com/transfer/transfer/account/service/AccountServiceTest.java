package com.transfer.transfer.account.service;

import com.transfer.transfer.account.model.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void assertDatabaseEntriesExist() {
        Assertions.assertEquals(accountService.getAccounts().size(), 3);
    }

    @Test
    public void shouldReturnNullIfAccountIsNotFound() {
        Assertions.assertNull(accountService.getAccount(10000L));
        Assertions.assertNull(accountService.getAccount(10004L));
        Assertions.assertNull(accountService.getAccount(10005L));
    }

    @Test
    public void shouldReturnCorrectModelIfAccountIsFound() {
        AccountModel firstAccountModel = accountService.getAccount(10001L);
        AccountModel secondAccountModel = accountService.getAccount(10002L);
        AccountModel thirdAccountModel = accountService.getAccount(10003L);

        Assertions.assertNotNull(firstAccountModel);
        Assertions.assertTrue(checkIfUserModelEqualsValues(firstAccountModel, 10001L, "USD", 150.0));

        Assertions.assertNotNull(secondAccountModel);
        Assertions.assertTrue(checkIfUserModelEqualsValues(secondAccountModel, 10002L, "PLN", 100.0));

        Assertions.assertNotNull(thirdAccountModel);
        Assertions.assertTrue(checkIfUserModelEqualsValues(thirdAccountModel, 10003L, "EUR", 50.0));
    }

    private boolean checkIfUserModelEqualsValues(AccountModel accountModel, long expectedUserID, String expectedCurrency, double expectedBalance) {
        return accountModel.getUserID() == expectedUserID && accountModel.getCurrency().equals(expectedCurrency) && accountModel.getBalance() == expectedBalance;
    }
}
