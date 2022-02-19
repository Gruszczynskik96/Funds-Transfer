package com.transfer.transfer.user.service;

import com.transfer.transfer.user.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Returns list of all UserModel objects saved in Database.
     * @return List of UserModel objects.
     */
    List<UserModel> findUsers();
    Optional<UserModel> getUserModel(long userID);
}
