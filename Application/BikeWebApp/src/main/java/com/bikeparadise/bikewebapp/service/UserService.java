package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.UserRegisterDto;
import com.bikeparadise.bikewebapp.dto.UserSignInDto;
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

    public ResponseEntity<String> registerUser(UserRegisterDto userRegisterDto){
        if(userRepository.findUserByUsername(userRegisterDto.getUsername()).size() != 0 ||
                userRepository.findUserByUserData_UserEmail_Email(userRegisterDto.getEmail()).size() != 0 ||
                userRepository.findUserByUserData_UserPhoneNumber_PhoneNumber(userRegisterDto.getPhoneNumber()).size() != 0
        ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword());

        UserData userData = new UserData(userRegisterDto.getFirstName(), userRegisterDto.getLastName());
        User user = new User(userRegisterDto.getUsername(), encodedPassword, userData);
        UserEmail userEmail = new UserEmail(userRegisterDto.getEmail(), userData);
        UserPhoneNumber userPhoneNumber = new UserPhoneNumber(userRegisterDto.getPhoneNumber(), userData);

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

    public ResponseEntity<String> loginUser(UserSignInDto userSignInDto){
        List<User> foundUsers = userRepository.findUserByUsername(userSignInDto.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(foundUsers.size() == 0 || !encoder.matches(userSignInDto.getPassword(), foundUsers.get(0).getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }
}