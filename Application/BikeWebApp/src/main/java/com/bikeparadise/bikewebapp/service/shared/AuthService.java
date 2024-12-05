package com.bikeparadise.bikewebapp.service.shared;

import com.bikeparadise.bikewebapp.model.user.User;
import com.bikeparadise.bikewebapp.repository.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class AuthService {
    public static User checkAuth(UserRepository userRepository){
        //retrieve client id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        List<User> foundUsers = userRepository.findUserByUsername(authentication.getName());

        if (foundUsers.size() == 0) {
            return null;
        }

        return foundUsers.get(0);
    }
}
