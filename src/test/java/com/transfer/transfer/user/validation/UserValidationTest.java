package com.transfer.transfer.user.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
public class UserValidationTest {

    private static final String USER_NOT_FOUND_BY_USER_ID_MESSAGE = "User was not found for given user ID";

    @Autowired
    private UserValidation userValidation;

    @Test
    public void assertUserValidationThrowsResponseStatusException() {
        Assertions.assertThrows(ResponseStatusException.class, () -> userValidation.validateUserExists(10000L));
        Assertions.assertThrows(ResponseStatusException.class, () -> userValidation.validateUserExists(10004L));
        Assertions.assertThrows(ResponseStatusException.class, () -> userValidation.validateUserExists(10005L));
    }

    @Test
    public void assertResponseStatusExceptionIsCorrectlySet() {
        ResponseStatusException responseStatusExceptionForUserOne = Assertions.assertThrows(ResponseStatusException.class, () -> userValidation.validateUserExists(10000L));
        ResponseStatusException responseStatusExceptionForUserTwo = Assertions.assertThrows(ResponseStatusException.class, () -> userValidation.validateUserExists(10004L));
        ResponseStatusException responseStatusExceptionForUserThree = Assertions.assertThrows(ResponseStatusException.class, () -> userValidation.validateUserExists(10005L));
        assertResponseStatusHasCorrectlySetValues(responseStatusExceptionForUserOne, HttpStatus.NOT_FOUND, USER_NOT_FOUND_BY_USER_ID_MESSAGE);
        assertResponseStatusHasCorrectlySetValues(responseStatusExceptionForUserTwo, HttpStatus.NOT_FOUND, USER_NOT_FOUND_BY_USER_ID_MESSAGE);
        assertResponseStatusHasCorrectlySetValues(responseStatusExceptionForUserThree, HttpStatus.NOT_FOUND, USER_NOT_FOUND_BY_USER_ID_MESSAGE);
    }

    private void assertResponseStatusHasCorrectlySetValues(ResponseStatusException responseStatusException, HttpStatus httpStatus, String reason) {
        Assertions.assertEquals(responseStatusException.getStatus(), httpStatus);
        Assertions.assertEquals(responseStatusException.getReason(), reason);
    }
}
