package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.config.JwtTokenGenerator;
import com.bikeparadise.bikewebapp.dto.SecurityFilterDto;
import com.bikeparadise.bikewebapp.dto.UserInfoDto;
import com.bikeparadise.bikewebapp.dto.UserRegisterDto;
import com.bikeparadise.bikewebapp.dto.UserSignInDto;
import com.bikeparadise.bikewebapp.model.*;
import com.bikeparadise.bikewebapp.repository.UserDataRepository;
import com.bikeparadise.bikewebapp.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;

    public UserService(UserRepository userRepository, UserDataRepository userDataRepository,
                       JwtTokenGenerator jwtTokenGenerator, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.userDataRepository = userDataRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.authenticationManager = authenticationManager;
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

        if(userRegisterDto.isSelectedRole()){
            ShopAssistant shopAssistant = new ShopAssistant(userData);
            userData.setShopAssistant(shopAssistant);
        }
        else{
            Client client = new Client(userData);
            userData.setClient(client);
        }

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<SecurityFilterDto> loginUser(UserSignInDto userSignInDto) {
        List<User> foundUsers = userRepository.findUserByUsername(userSignInDto.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(foundUsers.size() == 0 || !encoder.matches(userSignInDto.getPassword(), foundUsers.get(0).getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Authentication authentication;

        if(foundUsers.get(0).getUserData().getShopAssistant() != null){
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userSignInDto.getUsername(), userSignInDto.getPassword(), authorities)
            );
        }
        else{
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userSignInDto.getUsername(), userSignInDto.getPassword(), authorities)
            );
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenGenerator.generateToken(authentication);

        return new ResponseEntity<>(new SecurityFilterDto(token), HttpStatus.OK);
    }

    public ResponseEntity<UserInfoDto> getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<User> foundUsers =  userRepository.findUserByUsername(authentication.getName());
        if(foundUsers.size() == 0){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = foundUsers.get(0);

        String phoneNumbers = "";
        String emails = "";

        for(UserPhoneNumber userPhoneNumber : user.getUserData().getUserPhoneNumber()){
            phoneNumbers += userPhoneNumber.getPhoneNumber() + " ";
        }

        for(UserEmail userEmail : user.getUserData().getUserEmail()){
            emails += userEmail.getEmail() + " ";
        }

        UserInfoDto userInfoDto = new UserInfoDto(user.getUserData().getFirstName() + " " + user.getUserData().getLastName(), phoneNumbers, emails, user.getUsername());
        return ResponseEntity.ok(userInfoDto);
    }
}
