package com.transfer.transfer.transfer.service;

import javax.transaction.Transactional;

public interface TransferService {
    /**
     * Transfers amount of money from one account to another.
     * @param userIDFrom
     * @param userIDTo
     * @param amount
     */
    @Transactional
    void transferMoneyBetweenAccounts(long userIDFrom, long userIDTo, double amount);
}
