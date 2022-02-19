package com.transfer.transfer.user.service;

import com.transfer.transfer.user.model.UserModel;
import com.transfer.transfer.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> getUserModel(long userID) {
        return Optional.ofNullable(userRepository.getByUserID(userID));
    }
}
