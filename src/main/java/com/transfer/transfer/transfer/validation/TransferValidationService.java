package com.transfer.transfer.transfer.validation;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.service.AccountService;
import com.transfer.transfer.transfer.validation.exception.TransferException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TransferValidationService implements TransferValidation {

    private static final String BALANCE_NOT_SUFFICIENT_ERROR_MESSAGE = "Current balance is not sufficient: ";
    private static final String FUNDS_ARE_LESSER_OR_EQUALS_ZERO_ERROR_MESSAGE = "Funds after calculating exchange rates are lesser or equal zero!";

    private final AccountService accountService;

    public TransferValidationService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void validateUserHasCorrectBalance(double amount, long userID) throws TransferException {
        AccountModel accountModel = accountService.getAccount(userID);
        double currentBalance = accountModel.getBalance();

        if (currentBalance < amount) {
            throw new TransferException(HttpStatus.NOT_ACCEPTABLE, BALANCE_NOT_SUFFICIENT_ERROR_MESSAGE + amount);
        }
    }

    @Override
    public void validateTransferAmountIsGreaterThanZero(double funds) throws TransferException {
        if (funds <= 0) {
            throw new TransferException(HttpStatus.NOT_ACCEPTABLE, FUNDS_ARE_LESSER_OR_EQUALS_ZERO_ERROR_MESSAGE);
        }
    }
}
