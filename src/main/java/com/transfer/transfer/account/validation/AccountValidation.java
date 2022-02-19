package com.transfer.transfer.account.validation;

public interface AccountValidation {
    /**
     * Validates if AccountModel object with given User ID exists in database.
     * @param userID AccountModel's userID.
     */
    void validateAccountExists(long userID);
}
