package com.transfer.transfer.transfer.service;

public interface TransferService {
    void transferMoneyBetweenAccounts(long userIDFrom, long userIDTo, double amount);
}
