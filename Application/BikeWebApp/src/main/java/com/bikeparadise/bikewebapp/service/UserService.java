package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.config.JwtTokenGenerator;
import com.bikeparadise.bikewebapp.dto.SecurityFilterDto;
import com.bikeparadise.bikewebapp.dto.user.PhoneNumberDto;
import com.bikeparadise.bikewebapp.dto.user.UserInfoDto;
import com.bikeparadise.bikewebapp.dto.user.UserRegisterDto;
import com.bikeparadise.bikewebapp.dto.user.UserSignInDto;
import com.bikeparadise.bikewebapp.model.roles.Client;
import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
import com.bikeparadise.bikewebapp.model.user.User;
import com.bikeparadise.bikewebapp.model.user.UserData;
import com.bikeparadise.bikewebapp.model.user.UserEmail;
import com.bikeparadise.bikewebapp.model.user.UserPhoneNumber;
import com.bikeparadise.bikewebapp.repository.user.UserEmailRepository;
import com.bikeparadise.bikewebapp.repository.user.UserPhoneNumberRepository;
import com.bikeparadise.bikewebapp.repository.user.UserRepository;
import com.bikeparadise.bikewebapp.service.shared.AuthService;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final UserPhoneNumberRepository userPhoneNumberRepository;
    private final UserEmailRepository userEmailRepository;

    public UserService(UserRepository userRepository, UserPhoneNumberRepository userPhoneNumberRepository, UserEmailRepository userEmailRepository,
                       JwtTokenGenerator jwtTokenGenerator, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.authenticationManager = authenticationManager;
        this.userPhoneNumberRepository = userPhoneNumberRepository;
        this.userEmailRepository = userEmailRepository;
    }

    private ResponseEntity<String> checkConstraints(UserRegisterDto userRegisterDto) {
        if (userRegisterDto.getFirstName().length() < 2) {
            return ResponseEntity.badRequest().body("Error: First name must have at least 2 characters");
        } else if (userRegisterDto.getFirstName().length() > 18) {
            return ResponseEntity.badRequest().body("Error: First name is too long");
        }

        if (userRegisterDto.getLastName().length() < 2) {
            return ResponseEntity.badRequest().body("Error: Last name must have at least 2 characters");
        } else if (userRegisterDto.getLastName().length() > 48) {
            return ResponseEntity.badRequest().body("Error: Last name is too long");
        }

        if (userRegisterDto.getUsername().length() < 6) {
            return ResponseEntity.badRequest().body("Error: Username must have at least 6 characters");
        } else if (userRegisterDto.getLastName().length() > 30) {
            return ResponseEntity.badRequest().body("Error: Username is too long");
        }

        if (userRegisterDto.getEmail().length() < 3) {
            return ResponseEntity.badRequest().body("Error: E-mail must have at least 3 characters");
        } else if (userRegisterDto.getEmail().length() > 64) {
            return ResponseEntity.badRequest().body("Error: E-mail is too long");
        }

        if (userRegisterDto.getPhoneNumber().length() < 9) {
            return ResponseEntity.badRequest().body("Error: Phone number must have at least 9 characters");
        } else if (userRegisterDto.getPhoneNumber().length() > 13) {
            return ResponseEntity.badRequest().body("Error: Phone number is too long");
        }

        if (userRegisterDto.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body("Error: Password must have at least 8 characters");
        }

        if (userRegisterDto.getPassword().length() > 128) {
            return ResponseEntity.badRequest().body("Error: Password is too long");
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmedPassword())) {
            return ResponseEntity.badRequest().body("Error: Passwords don't match");
        }

        if (userRepository.findUserByUsername(userRegisterDto.getUsername()).size() != 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: That username is already taken");
        }

        if(userRepository.findUserByUserData_UserEmail_Email(userRegisterDto.getEmail()).size() != 0){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: That e-mail address is already taken");
        }

        if(userRepository.findUserByUserData_UserPhoneNumber_PhoneNumber(userRegisterDto.getPhoneNumber()).size() != 0){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: That phone number is already taken");
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> registerUser(UserRegisterDto userRegisterDto) {
        ResponseEntity<String> status = checkConstraints(userRegisterDto);
        if (!status.getStatusCode().equals(HttpStatus.OK)) {
            return status;
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

        if (userRegisterDto.isSelectedRole()) {
            ShopAssistant shopAssistant = new ShopAssistant(userData);
            userData.setShopAssistant(shopAssistant);
        } else {
            Client client = new Client(userData);
            userData.setClient(client);
        }

        userRepository.save(user);

        return ResponseEntity.ok().body("User successfully registered");
    }

    public ResponseEntity<Object> loginUser(UserSignInDto userSignInDto) {
        List<User> foundUsers = userRepository.findUserByUsername(userSignInDto.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (foundUsers.size() == 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User with that username wasn't found");
        }

        if(!encoder.matches(userSignInDto.getPassword(), foundUsers.get(0).getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Wrong password");
        }

        Authentication authentication;

        if (foundUsers.get(0).getUserData().getShopAssistant() != null) {
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userSignInDto.getUsername(), userSignInDto.getPassword(), authorities)
            );
        } else {
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
        User user = AuthService.checkAuth(userRepository);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String phoneNumbers = "";
        String emails = "";

        for (UserPhoneNumber userPhoneNumber : user.getUserData().getUserPhoneNumber()) {
            phoneNumbers += userPhoneNumber.getPhoneNumber() + " ";
        }

        for (UserEmail userEmail : user.getUserData().getUserEmail()) {
            emails += userEmail.getEmail() + " ";
        }

        UserInfoDto userInfoDto = new UserInfoDto(user.getUserData().getFirstName() + " " + user.getUserData().getLastName(), phoneNumbers, emails, user.getUsername());
        return ResponseEntity.ok(userInfoDto);
    }

    public ResponseEntity<List<String>> checkRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(roles);
    }

    public ResponseEntity<String> addPhoneNumber(PhoneNumberDto phoneNumberDto) {
        if (phoneNumberDto.getPhoneNumber().length() < 9) {
            return ResponseEntity.badRequest().body("Error: Phone number must have at least 9 characters");
        } else if (phoneNumberDto.getPhoneNumber().length() > 13) {
            return ResponseEntity.badRequest().body("Error: Phone number is too long");
        }

        UserPhoneNumber userPhoneNumberCheck = userPhoneNumberRepository.findUserPhoneNumberByPhoneNumber(phoneNumberDto.getPhoneNumber());

        if(userPhoneNumberCheck != null){
            return ResponseEntity.badRequest().body("Error: This phone number is already taken");
        }

        User user = AuthService.checkAuth(userRepository);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserPhoneNumber userPhoneNumber = new UserPhoneNumber(phoneNumberDto.getPhoneNumber(), user.getUserData());
        userPhoneNumberRepository.save(userPhoneNumber);
        return ResponseEntity.ok().body("Phone number successfully added");
    }

    public ResponseEntity<String> addEmail(String email) {
        if (email.length() < 3) {
            return ResponseEntity.badRequest().body("Error: E-mail must have at least 3 characters");
        } else if (email.length() > 64) {
            return ResponseEntity.badRequest().body("Error: E-mail is too long");
        }

        UserEmail userEmailCheck = userEmailRepository.findUserEmailByEmail(email);
        if(userEmailCheck != null){
            return ResponseEntity.badRequest().body("Error: This e-mail is already taken");
        }

        User user = AuthService.checkAuth(userRepository);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserEmail userEmail = new UserEmail(email, user.getUserData());
        userEmailRepository.save(userEmail);
        return ResponseEntity.ok().body("E-mail address successfully added");
    }
}
