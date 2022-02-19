package com.transfer.transfer.account.validation;

public interface AccountValidation {
    /**
     * Validates if AccountModel object with given User ID exists in database. Throws AccountException if entry is not found.
     * @param userID AccountModel's userID.
     */
    void validateAccountExists(long userID);

    /**
     * Validates if User IDs are different.
     * @param userIDFrom User ID of AccountModel from which money is to be taken from.
     * @param userIDTo User ID of AccountModel from which money is to be transferred to.
     */
    void validateAccountUserIDsAreDifferent(long userIDFrom, long userIDTo);
}
