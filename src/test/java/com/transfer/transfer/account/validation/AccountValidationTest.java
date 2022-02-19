package com.transfer.transfer.account.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
public class AccountValidationTest {

    private static final String ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE = "Account was not found for given user ID: ";

    @Autowired
    private AccountValidation accountValidation;

    @Test
    public void assertUserValidationThrowsResponseStatusException() {
        Assertions.assertThrows(ResponseStatusException.class, () -> accountValidation.validateAccountExists(10000L));
        Assertions.assertThrows(ResponseStatusException.class, () -> accountValidation.validateAccountExists(10004L));
        Assertions.assertThrows(ResponseStatusException.class, () -> accountValidation.validateAccountExists(10005L));
    }

    @Test
    public void assertResponseStatusExceptionIsCorrectlySet() {
        ResponseStatusException responseStatusExceptionForUserOne = Assertions.assertThrows(ResponseStatusException.class, () -> accountValidation.validateAccountExists(10000L));
        ResponseStatusException responseStatusExceptionForUserTwo = Assertions.assertThrows(ResponseStatusException.class, () -> accountValidation.validateAccountExists(10004L));
        ResponseStatusException responseStatusExceptionForUserThree = Assertions.assertThrows(ResponseStatusException.class, () -> accountValidation.validateAccountExists(10005L));
        assertResponseStatusHasCorrectlySetValues(responseStatusExceptionForUserOne, HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE + 10000L);
        assertResponseStatusHasCorrectlySetValues(responseStatusExceptionForUserTwo, HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE+ 10004L);
        assertResponseStatusHasCorrectlySetValues(responseStatusExceptionForUserThree, HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE+ 10005L);
    }

    private void assertResponseStatusHasCorrectlySetValues(ResponseStatusException responseStatusException, HttpStatus httpStatus, String reason) {
        Assertions.assertEquals(responseStatusException.getStatus(), httpStatus);
        Assertions.assertEquals(responseStatusException.getReason(), reason);
    }
}
