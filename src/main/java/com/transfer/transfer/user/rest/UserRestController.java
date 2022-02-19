package com.transfer.transfer.user.rest;

import com.transfer.transfer.user.model.UserModel;
import com.transfer.transfer.user.service.UserService;
import com.transfer.transfer.user.validation.UserValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "/user")
public class UserRestController {

    private final UserValidation userValidation;
    private final UserService userService;

    public UserRestController(UserValidation userValidation, UserService userService) {
        this.userValidation = userValidation;
        this.userService = userService;
    }

    @GetMapping("/{userID}")
    public ResponseEntity<UserModel> getUserByUserID(@PathVariable("userID") long userID) {
        userValidation.validateUserExists(userID);
        return ResponseEntity.ok(userService.getUserModel(userID).get());
    }
}
