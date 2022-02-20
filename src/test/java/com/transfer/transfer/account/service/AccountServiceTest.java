package com.transfer.transfer.account.service;

import com.transfer.transfer.account.model.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void shouldSaveCorrectAccount() {
        AccountModel firstAccount = createNewAccountModel(10001L, "USD", 0.0);
        accountService.saveAccount(firstAccount);
        Assertions.assertTrue(checkIfUserModelEqualsValues(accountService.getAccount(10001L), 10001L, "USD", 0.0));

        AccountModel secondAccount = createNewAccountModel(10002L, "PLN", 10.0);
        accountService.saveAccount(secondAccount);
        Assertions.assertTrue(checkIfUserModelEqualsValues(accountService.getAccount(10002L), 10002L, "PLN", 10.0));

        AccountModel thirdAccount = createNewAccountModel(10003L, "EUR", 1245.22);
        accountService.saveAccount(thirdAccount);
        Assertions.assertTrue(checkIfUserModelEqualsValues(accountService.getAccount(10003L), 10003L, "EUR", 1245.22));
    }

    @Test
    public void shouldReturnNullIfAccountIsNotFound() {
        Assertions.assertNull(accountService.getAccount(10000L));
        Assertions.assertNull(accountService.getAccount(10004L));
        Assertions.assertNull(accountService.getAccount(10005L));
    }

    @Test
    public void assertDatabaseEntriesExist() {
        accountService.saveAccount(createNewAccountModel(10004L, "USD", 0.0));
        accountService.saveAccount(createNewAccountModel(10005L, "USD", 0.0));
        accountService.saveAccount(createNewAccountModel(10006L, "USD", 0.0));
        Assertions.assertEquals(3, accountService.getAccounts().size());
    }

    @Test
    public void shouldReturnCorrectModelIfAccountIsFound() {
        accountService.saveAccount(createNewAccountModel(10001L, "USD", 150.0));
        accountService.saveAccount(createNewAccountModel(10002L, "PLN", 100.0));
        accountService.saveAccount(createNewAccountModel(10003L, "EUR", 50.0));

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

    private AccountModel createNewAccountModel(long userID, String currency, double balance) {
        AccountModel accountModel = new AccountModel();
        accountModel.setUserID(userID);
        accountModel.setCurrency(currency);
        accountModel.setBalance(balance);
        return accountModel;
    }
}
