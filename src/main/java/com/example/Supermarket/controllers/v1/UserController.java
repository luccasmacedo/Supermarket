package com.example.Supermarket.controllers.v1;

import com.example.Supermarket.exception.UsernameAlreadyInUseException;
import com.example.Supermarket.model.User;
import com.example.Supermarket.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Adds user 
     *
     * @param user the user to add 
     * @return string "User created succesfuly"
     * @throws UsernameAlreadyInUseException
     */
    @PostMapping("/sign-up")
    public String signUp(@RequestBody User user) throws UsernameAlreadyInUseException {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.createUser(user);
            return "User created succesfully";
        } catch (UsernameAlreadyInUseException exception) {
            throw exception;
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}