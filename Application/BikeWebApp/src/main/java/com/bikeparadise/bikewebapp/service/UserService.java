package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.model.User;
import com.bikeparadise.bikewebapp.model.UserData;
import com.bikeparadise.bikewebapp.model.UserEmail;
import com.bikeparadise.bikewebapp.model.UserPhoneNumber;
import com.bikeparadise.bikewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> registerUser(String firstName, String lastName, String username, String password,
                                               String email, String phoneNumber){
        if(userRepository.findUserByUsername(username).size() != 0 ||
                userRepository.findUserByUserData_UserEmail_Email(email).size() != 0 ||
                userRepository.findUserByUserData_UserPhoneNumber_PhoneNumber(phoneNumber).size() != 0
        ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String encodedPassword = passwordEncoder.encode(password);

        UserData userData = new UserData(firstName, lastName);
        User user = new User(username, encodedPassword, userData);
        UserEmail userEmail = new UserEmail(email, userData);
        UserPhoneNumber userPhoneNumber = new UserPhoneNumber(phoneNumber, userData);

        userData.setUser(user);
        List<UserEmail> userEmailList = new ArrayList<>();
        userEmailList.add(userEmail);
        userData.setUserEmail(userEmailList);

        List<UserPhoneNumber> userPhoneNumberList = new ArrayList<>();
        userPhoneNumberList.add(userPhoneNumber);
        userData.setUserPhoneNumber(userPhoneNumberList);

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> loginUser(String username, String password){
        List<User> foundUsers = userRepository.findUserByUsername(username);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!encoder.matches(password, foundUsers.get(0).getPassword()) || foundUsers.size() == 0){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }
}
