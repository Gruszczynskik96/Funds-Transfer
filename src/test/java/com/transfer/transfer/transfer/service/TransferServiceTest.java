package com.transfer.transfer.transfer.service;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@SpringBootTest
public class TransferServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    @Test
    public void shouldChangeBalancesOnTwoAccountsWithSameCurrency() {
        AccountModel accountModelFrom = accountService.getAccount(10001L);
        AccountModel accountModelTo = accountService.getAccount(10002L);

        transferService.transferMoneyBetweenAccounts(10001L, 10002L, 12.58);

        AccountModel accountModelFromAfterChanges = accountService.getAccount(10001L);
        AccountModel accountModelToAfterChanges = accountService.getAccount(10002L);

        Assertions.assertNotEquals(accountModelFrom.getBalance(), accountModelFromAfterChanges.getBalance());
        Assertions.assertNotEquals(accountModelTo.getBalance(), accountModelToAfterChanges.getBalance());

        Assertions.assertEquals(accountModelFromAfterChanges.getBalance(), accountModelFrom.getBalance() - 12.58);
        Assertions.assertEquals(accountModelToAfterChanges.getBalance(), accountModelTo.getBalance() + 150.0);
    }
}
