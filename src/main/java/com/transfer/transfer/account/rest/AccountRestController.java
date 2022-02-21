package com.transfer.transfer.account.rest;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.account.validation.AccountValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/account")
public class AccountRestController {
    private static final String ACCOUNT_CREATED_MESSAGE = "Account was created for user ID: ";

    private final AccountValidation accountValidation;
    private final AccountService accountService;

    public AccountRestController(AccountValidation accountValidation,
                                 AccountService accountService) {
        this.accountValidation = accountValidation;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountModel>> getUsers() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@Valid @RequestBody AccountModel accountModel) {
        long userId = accountModel.getUserID();
        accountValidation.validateAccountDoesNotExist(userId);

        accountService.saveAccount(accountModel);
        return ResponseEntity.ok(ACCOUNT_CREATED_MESSAGE + userId);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<AccountModel> getUserByUserID(@PathVariable("userID") long userID) {
        accountValidation.validateAccountExists(userID);
        return ResponseEntity.ok(accountService.getAccount(userID));
    }
}
