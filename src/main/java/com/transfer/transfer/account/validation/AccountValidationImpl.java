package com.transfer.transfer.account.validation;

import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.account.validation.exception.AccountException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountValidationImpl implements AccountValidation {

    private static final String ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE = "Account was not found for given user ID: ";

    private final AccountService accountService;

    public AccountValidationImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void validateAccountExists(long userID) {
        accountService.getAccount(userID).orElseThrow(() -> new AccountException(HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE + userID));
    }
}
