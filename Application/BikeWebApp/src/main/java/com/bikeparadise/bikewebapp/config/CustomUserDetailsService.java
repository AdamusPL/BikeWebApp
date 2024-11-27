package com.bikeparadise.bikewebapp.config;

import com.bikeparadise.bikewebapp.model.User;
import com.bikeparadise.bikewebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> foundUsers = userRepository.findUserByUsername(username);
        if(foundUsers.size() != 0){
            User user = foundUsers.get(0);
            SimpleGrantedAuthority simpleGrantedAuthority;
            List<GrantedAuthority> authorities = new ArrayList<>();

            if(user.getUserData().getShopAssistant() != null){
                simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
            }
            else{
                simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
            }
            authorities.add(simpleGrantedAuthority);

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
        return null;
    }

}
