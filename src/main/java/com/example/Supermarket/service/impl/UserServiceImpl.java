package com.example.Supermarket.service.impl;

import com.example.Supermarket.exception.UsernameAlreadyInUseException;
import com.example.Supermarket.model.User;
import com.example.Supermarket.repository.UserRepository;
import com.example.Supermarket.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public void createUser(User user) throws UsernameAlreadyInUseException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyInUseException("Username Already in use");
        } else {
            userRepository.save(user);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
