package com.transfer.transfer.transfer.validation;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.transfer.validation.exception.TransferException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TransferValidationTest {

    @Autowired
    private TransferValidation transferValidation;

    @MockBean
    private AccountService accountService;

    @Test
    public void shouldThrowExceptionWhenInsufficientFunds() {
        AccountModel accountModel = Mockito.mock(AccountModel.class);
        Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(accountModel);

        Mockito.when(accountModel.getBalance()).thenReturn(0.0);
        Assertions.assertThrows(TransferException.class, () -> transferValidation.validateUserHasCorrectBalance(1, Mockito.anyLong()));

        Mockito.when(accountModel.getBalance()).thenReturn(100.0);
        Assertions.assertThrows(TransferException.class, () -> transferValidation.validateUserHasCorrectBalance(100.01, Mockito.anyLong()));

        Mockito.when(accountModel.getBalance()).thenReturn(1250.78);
        Assertions.assertThrows(TransferException.class, () -> transferValidation.validateUserHasCorrectBalance(1250.79, Mockito.anyLong()));
    }

    @Test
    public void shouldNotThrowExceptionWhenSufficientFunds() {
        AccountModel accountModel = Mockito.mock(AccountModel.class);
        Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(accountModel);

        Mockito.when(accountModel.getBalance()).thenReturn(10.0);
        Assertions.assertDoesNotThrow(() -> transferValidation.validateUserHasCorrectBalance(0, Mockito.anyLong()));

        Mockito.when(accountModel.getBalance()).thenReturn(100.0);
        Assertions.assertDoesNotThrow(() -> transferValidation.validateUserHasCorrectBalance(99.99, Mockito.anyLong()));

        Mockito.when(accountModel.getBalance()).thenReturn(1250.78);
        Assertions.assertDoesNotThrow(() -> transferValidation.validateUserHasCorrectBalance(1250.77, Mockito.anyLong()));
    }
}
