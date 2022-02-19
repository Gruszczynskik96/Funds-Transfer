package com.transfer.transfer.user.service;

import com.transfer.transfer.user.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void assertDatabaseCreatesInitialRecords() {
        List<UserModel> userModelList = userService.findUsers();

        Assert.isTrue(userModelList.size() == 3, "User Table is not set up correctly, expecting 3 records!");
    }
}
