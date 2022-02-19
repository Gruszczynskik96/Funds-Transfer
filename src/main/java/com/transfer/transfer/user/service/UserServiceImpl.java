package com.transfer.transfer.user.service;

import com.transfer.transfer.user.model.UserModel;
import com.transfer.transfer.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findUsers() {
        return userRepository.findAll();
    }
}
