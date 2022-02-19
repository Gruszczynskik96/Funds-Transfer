package com.transfer.transfer.user.validation;

import com.transfer.transfer.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserValidationImpl implements UserValidation {

    private static final String USER_NOT_FOUND_BY_USER_ID_MESSAGE = "User was not found for given user ID";

    private final UserService userService;

    public UserValidationImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void validateUserExists(long userID) {
        userService.getUserModel(userID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_BY_USER_ID_MESSAGE));
    }
}
