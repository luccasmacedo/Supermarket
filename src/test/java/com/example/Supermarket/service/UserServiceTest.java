package com.example.Supermarket.service;

import com.example.Supermarket.exception.UsernameAlreadyInUseException;
import com.example.Supermarket.model.User;
import com.example.Supermarket.repository.UserRepository;
import com.example.Supermarket.service.impl.UserServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setPassword("1234");
        testUser.setUsername("testUser");
        testUser.setRole("CASHIER");

        userService = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("Should be able to include user")
    public void shoudlBeSaveUser() throws UsernameAlreadyInUseException {
        Mockito.when(userRepository.save(testUser)).thenReturn(testUser);
        userService.createUser(testUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    @DisplayName("Should not create user if username already exists")
    public void shouldNotCreateUserIfUsernameIsInUse() throws UsernameAlreadyInUseException {
        Mockito.when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        Assertions.assertThrows(UsernameAlreadyInUseException.class, () -> userService.createUser(testUser));
    }
}
