package com.transfer.transfer.transfer.validation;

import com.transfer.transfer.account.validation.exception.AccountException;
import org.springframework.stereotype.Service;

@Service
public interface TransferValidation {
    /**
     * Checks if user has balance same or greater than the amount he wishes to send to the user. Throws TransferException if balance is insufficient.
     * @param amount Amount of funds to be transferred.
     * @param userID ID of the user from which funds are to be taken from.
     * @throws AccountException Throws account exception if balance is insufficient.
     */
    void validateUserHasCorrectBalance(double amount, long userID) throws AccountException;
}
