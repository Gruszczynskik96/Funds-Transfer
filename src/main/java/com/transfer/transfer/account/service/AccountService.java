package com.transfer.transfer.account.service;

import com.transfer.transfer.account.model.AccountModel;

import java.util.List;

public interface AccountService {
    /**
     * Returns list of all AccountModel objects saved in Database.
     * @return List of AccountModel objects.
     */
    List<AccountModel> getAccounts();

    /**
     * Returns AccountModel object with give userID saved in database, or null if object is not found.
     * @param userID userID value from AccountModel
     * @return AccountModel, or null
     */
    AccountModel getAccount(long userID);

    /**
     * Saves the AccountModel, or creates a new table entry for given AccountModel
     * @param accountModel AccountModel object to save
     */
    void saveAccount(AccountModel accountModel);
}
