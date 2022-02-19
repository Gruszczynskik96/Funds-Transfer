package com.transfer.transfer.account.repository;

import com.transfer.transfer.account.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    AccountModel getByUserID(long userID);
}
