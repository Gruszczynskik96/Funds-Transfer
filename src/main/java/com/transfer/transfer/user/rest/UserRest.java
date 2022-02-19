package com.transfer.transfer.user.rest;

import com.transfer.transfer.user.validation.UserValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "/user")
public class UserRest {

    private final UserValidation userValidation;

    public UserRest(UserValidation userValidation) {
        this.userValidation = userValidation;
    }

    @GetMapping("/{userID}")
    public ResponseEntity<String> getUserByUserID(@PathVariable("userID") long userID) {
        userValidation.validateUserExists(userID);
        return ResponseEntity.ok("Correct");
    }
}
