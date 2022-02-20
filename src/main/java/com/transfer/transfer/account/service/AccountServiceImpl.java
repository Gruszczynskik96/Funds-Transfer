package com.transfer.transfer.account.service;

import com.transfer.transfer.account.model.AccountModel;
import com.transfer.transfer.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public AccountModel getAccount(long userID) {
        return accountRepository.getByUserID(userID);
    }

    @Override
    @Transactional
    public void saveAccount(AccountModel accountModel) {
        accountRepository.save(accountModel);
    }
}
