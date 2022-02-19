package com.transfer.transfer.user.service;

import com.transfer.transfer.user.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void assertDatabaseEntriesExist() {
        Assertions.assertEquals(userService.findUsers().size(), 3);
    }

    @Test
    public void assertUserModelIsEmptyIfNotPresent() {
        Assertions.assertEquals(userService.getUserModel(10000L), Optional.empty());
        Assertions.assertEquals(userService.getUserModel(10004L), Optional.empty());
        Assertions.assertEquals(userService.getUserModel(10005L), Optional.empty());
    }

    @Test
    public void assertUserEntriesAreCorrectlyInserted() {
        Assertions.assertTrue(checkIfUserModelEqualsValues(userService.getUserModel(10001L).get(), 10001L, "USD", 150.0));
        Assertions.assertTrue(checkIfUserModelEqualsValues(userService.getUserModel(10002L).get(), 10002L, "PLN", 100.0));
        Assertions.assertTrue(checkIfUserModelEqualsValues(userService.getUserModel(10003L).get(), 10003L, "EUR", 50.0));
    }

    private boolean checkIfUserModelEqualsValues(UserModel userModel, long expectedUserID, String expectedCurrency, double expectedBalance) {
        return userModel.getUserID() == expectedUserID && userModel.getCurrency().equals(expectedCurrency) && userModel.getBalance() == expectedBalance;
    }
}
