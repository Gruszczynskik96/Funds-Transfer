package com.transfer.transfer.account.rest;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.account.validation.AccountValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "/account")
public class AccountRestController {

    private final AccountValidation userValidation;
    private final AccountService accountService;

    public AccountRestController(AccountValidation userValidation, AccountService accountService) {
        this.userValidation = userValidation;
        this.accountService = accountService;
    }

    @GetMapping("/{userID}")
    public ResponseEntity<AccountModel> getUserByUserID(@PathVariable("userID") long userID) {
        userValidation.validateAccountExists(userID);
        return ResponseEntity.ok(accountService.getAccount(userID).get());
    }
}
