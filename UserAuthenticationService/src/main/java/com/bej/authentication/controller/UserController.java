package com.bej.authentication.controller;

import com.bej.authentication.exception.UserAlreadyExistsException;
import com.bej.authentication.exception.InvalidCredentialsException;
import com.bej.authentication.security.JWTSecurityTokenGeneratorImpl;
import com.bej.authentication.security.SecurityTokenGenerator;
import com.bej.authentication.service.IUserService;
import com.bej.authentication.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    // Autowire the IUserService, SecurityTokenGenerator using constructor autowiring

    private IUserService userService;
    private JWTSecurityTokenGeneratorImpl jwtSecurityTokenGenerator;

    @Autowired
    public UserController(IUserService userService, JWTSecurityTokenGeneratorImpl jwtSecurityTokenGenerator) {
        this.userService = userService;
        this.jwtSecurityTokenGenerator = jwtSecurityTokenGenerator;
    }

    @PostMapping("/saveCustomer")
    public ResponseEntity<?> saveCustomer(@RequestBody User user) throws UserAlreadyExistsException {
        // Write the logic to save a user,
        // return 201 status if user is saved else 500 status
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws InvalidCredentialsException {
        // Generate the token on login,
        // return 200 status if user is saved else 500 status
        User retrievedUser = userService.getUserByUserIdAndPassword(user.getUserId(), user.getPassword());
        if (retrievedUser == null) {
            throw new InvalidCredentialsException();
        }
        String token = jwtSecurityTokenGenerator.createToken(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
