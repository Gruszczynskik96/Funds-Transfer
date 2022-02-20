package com.transfer.transfer.account.validation;

import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.account.validation.exception.AccountException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountValidationImpl implements AccountValidation {
    private static final String ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE = "Account was not found for given user ID: ";
    private static final String ACCOUNT_FOUND_FOR_USER_ID_MESSAGE = "Account already exists with given user ID: ";
    private static final String ACCOUNTS_HAVE_SAME_IDS_MESSAGE = "Cannot transfer money to same account! User ID: ";

    private final AccountService accountService;

    public AccountValidationImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void validateAccountExists(long userID) throws AccountException {
        Optional.ofNullable(accountService.getAccount(userID))
                .orElseThrow(() -> new AccountException(HttpStatus.NOT_FOUND, ACCOUNT_NOT_FOUND_BY_USER_ID_MESSAGE + userID));
    }

    @Override
    public void validateAccountDoesNotExist(long userID) throws AccountException {
        if (Optional.ofNullable(accountService.getAccount(userID)).isPresent()) {
            throw new AccountException(HttpStatus.CONFLICT, ACCOUNT_FOUND_FOR_USER_ID_MESSAGE + userID);
        }
    }

    @Override
    public void validateAccountUserIDsAreDifferent(long userIDFrom, long userIDTo) throws AccountException {
        if (userIDFrom == userIDTo) {
            throw new AccountException(HttpStatus.CONFLICT, ACCOUNTS_HAVE_SAME_IDS_MESSAGE + userIDFrom);
        }
    }
}
