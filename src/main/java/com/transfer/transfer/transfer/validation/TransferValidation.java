package com.transfer.transfer.transfer.validation;

import com.transfer.transfer.transfer.validation.exception.TransferException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TransferValidation {
    /**
     * Checks if user has balance same or greater than the amount he wishes to send to the user. Throws TransferException if balance is insufficient.
     * @param amount Amount of funds to be transferred.
     * @param userID ID of the user from which funds are to be taken from.
     * @throws TransferException Throws account exception if balance is insufficient.
     */
    void validateUserHasCorrectBalance(double amount, long userID) throws TransferException;
}
