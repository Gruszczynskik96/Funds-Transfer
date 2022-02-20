package com.transfer.transfer.account.validation;

import com.transfer.transfer.account.validation.exception.AccountException;

public interface AccountValidation {
    /**
     *
     * Validates if AccountModel object with given User ID exists in database. Throws AccountException if entry is not found.
     * @param userID AccountModel's userID.
     * @throws AccountException Throws AccountException if User is not found.
     */
    void validateAccountExists(long userID) throws AccountException;

    /**
     * Validates if AccountModel with give User ID does not exist in database. Throws AccountException if entry is found.
     * @param userID Account Model's user ID
     * @throws AccountException Throws AccountException if User is found.
     */
    void validateAccountDoesNotExist(long userID) throws AccountException;

    /**
     * Validates if User IDs are different.
     * @param userIDSender User ID of AccountModel from which money is to be taken from.
     * @param userIDReceiver User ID of AccountModel to which money is to be transferred to.
     * @throws AccountException Throws AccountException if both values are the same.
     */
    void validateAccountUserIDsAreDifferent(long userIDSender, long userIDReceiver) throws AccountException;
}
