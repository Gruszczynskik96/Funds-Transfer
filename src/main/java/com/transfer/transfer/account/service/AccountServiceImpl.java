package com.transfer.transfer.account.service;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountModel> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<AccountModel> getAccount(long userID) {
        return Optional.ofNullable(accountRepository.getByUserID(userID));
    }
}
