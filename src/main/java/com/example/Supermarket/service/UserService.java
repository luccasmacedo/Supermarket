package com.example.Supermarket.service;

import com.example.Supermarket.exception.UsernameAlreadyInUseException;
import com.example.Supermarket.model.User;

public interface UserService {
    
    public void createUser(User user) throws UsernameAlreadyInUseException;

    public User findByUsername(String username);
}
