package com.transfer.transfer.account.validation;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.account.validation.exception.AccountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class AccountValidationTest {

    private static final String ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE = "Account was not found for given user ID: ";
    private static final String ACCOUNTS_HAVE_SAME_IDS_MESSAGE = "Cannot transfer money to same account! User ID: ";

    @Autowired
    private AccountValidation accountValidation;

    @MockBean
    private AccountService accountService;

    @Test
    public void shouldThrowExceptionWhenAccountIsNotFound() {
        Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(null);
        Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountExists(Mockito.anyLong()));
    }

    @Test
    public void shouldThrowExceptionWithCorrectMessageIfAccountIsNotFound() {
        AccountException responseStatusExceptionForUserOne = Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountExists(10000L));
        AccountException responseStatusExceptionForUserTwo = Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountExists(10004L));
        AccountException responseStatusExceptionForUserThree = Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountExists(10005L));
        assertExceptionHasCorrectlySetValues(responseStatusExceptionForUserOne, HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE + 10000L);
        assertExceptionHasCorrectlySetValues(responseStatusExceptionForUserTwo, HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE + 10004L);
        assertExceptionHasCorrectlySetValues(responseStatusExceptionForUserThree, HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE+ 10005L);
    }

    @Test
    public void shouldNotThrowExceptionWhenAccountIsFound() {
        Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(Mockito.mock(AccountModel.class));
        Assertions.assertDoesNotThrow(() -> accountValidation.validateAccountExists(Mockito.anyLong()));
    }

    @Test
    public void shouldThrowExceptionIfIDsAreSame() {
        Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountUserIDsAreDifferent(1L, 1L));
        Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountUserIDsAreDifferent(10L, 10L));
        Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountUserIDsAreDifferent(101L, 101L));
    }

    @Test
    public void shouldThrowExceptionWithCorrectMessageIfIDsAreSame() {
        AccountException responseStatusExceptionForUserOne = Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountUserIDsAreDifferent(1L, 1L));
        AccountException responseStatusExceptionForUserTwo = Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountUserIDsAreDifferent(10L, 10L));
        AccountException responseStatusExceptionForUserThree = Assertions.assertThrows(AccountException.class, () -> accountValidation.validateAccountUserIDsAreDifferent(100L, 100L));
        assertExceptionHasCorrectlySetValues(responseStatusExceptionForUserOne, HttpStatus.CONFLICT, ACCOUNTS_HAVE_SAME_IDS_MESSAGE + 1L);
        assertExceptionHasCorrectlySetValues(responseStatusExceptionForUserTwo, HttpStatus.CONFLICT, ACCOUNTS_HAVE_SAME_IDS_MESSAGE + 10L);
        assertExceptionHasCorrectlySetValues(responseStatusExceptionForUserThree, HttpStatus.CONFLICT, ACCOUNTS_HAVE_SAME_IDS_MESSAGE + 100L);
    }

    @Test
    public void shouldNotThrowExceptionIfIDsAreDifferent() {
        Assertions.assertDoesNotThrow(() -> accountValidation.validateAccountUserIDsAreDifferent(1L, 2L));
        Assertions.assertDoesNotThrow(() -> accountValidation.validateAccountUserIDsAreDifferent(10L, 11L));
        Assertions.assertDoesNotThrow(() -> accountValidation.validateAccountUserIDsAreDifferent(101L, 102L));
    }

    private void assertExceptionHasCorrectlySetValues(AccountException AccountException, HttpStatus httpStatus, String reason) {
        Assertions.assertEquals(AccountException.getStatus(), httpStatus);
        Assertions.assertEquals(AccountException.getReason(), reason);
    }
}
