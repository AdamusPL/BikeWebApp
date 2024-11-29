package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.SecurityFilterDto;
import com.bikeparadise.bikewebapp.dto.user.UserInfoDto;
import com.bikeparadise.bikewebapp.dto.user.UserRegisterDto;
import com.bikeparadise.bikewebapp.dto.user.UserSignInDto;
import com.bikeparadise.bikewebapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDto userRegisterDto){
        return userService.registerUser(userRegisterDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SecurityFilterDto> loginUser(@RequestBody UserSignInDto userSignInDto) {
        return userService.loginUser(userSignInDto);
    }

    @GetMapping("/get-user-data")
    public ResponseEntity<UserInfoDto> getUserData(){
        return userService.getUserData();
    }

    @GetMapping("/check-role")
    public ResponseEntity<List<String>> checkRole(){
        return userService.checkRole();
    }
}
