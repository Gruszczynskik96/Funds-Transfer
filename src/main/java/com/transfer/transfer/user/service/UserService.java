package com.transfer.transfer.user.service;

import com.transfer.transfer.user.model.UserModel;

import java.util.List;

public interface UserService {
    /**
        @return returns all user entries from database.
     */
    List<UserModel> findUsers();
}
