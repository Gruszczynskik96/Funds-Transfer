package com.transfer.transfer.transfer.service;

import javax.transaction.Transactional;

public interface TransferService {
    /**
     * Transfers amount of money from one account to another.
     * @param userIDFrom User ID of the AccountModel from which money is to be transferred from.
     * @param userIDTo User ID of the AccountModel to which money is to be transferred to.
     * @param amount Amount of money to be transferred.
     */
    @Transactional
    void transferMoneyBetweenAccounts(long userIDFrom, long userIDTo, double amount);
}
