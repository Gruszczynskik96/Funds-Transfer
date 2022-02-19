package com.transfer.transfer.user.validation;

public interface UserValidation {
    /**
     * Validates if UserModel object with given User ID exists in database.
     * @param userID UserModel's userID.
     */
    void validateUserExists(long userID);
}
