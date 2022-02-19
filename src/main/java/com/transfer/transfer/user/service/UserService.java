package com.transfer.transfer.user.service;

import com.transfer.transfer.user.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
        @return returns all user entries from database.
     */
    List<UserModel> findUsers();
    Optional<UserModel> getUserModel(long userID);
}
