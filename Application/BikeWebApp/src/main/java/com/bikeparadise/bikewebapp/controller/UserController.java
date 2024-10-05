package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register-user")
    public ResponseEntity<String> registerUser(@RequestParam String firstName, @RequestParam String lastName,
                                               @RequestParam String username, @RequestParam String password,
                                               @RequestParam String email, @RequestParam String phoneNumber){
        return userService.registerUser(firstName, lastName, username, password, email, phoneNumber);
    }

    @PostMapping("/login-user")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password){
        return userService.loginUser(username, password);
    }
}
