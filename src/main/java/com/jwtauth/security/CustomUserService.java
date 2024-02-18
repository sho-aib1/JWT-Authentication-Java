package com.jwtauth.security;

import com.jwtauth.Exception.ResouceNotFoundException;
import com.jwtauth.entities.User;
import com.jwtauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = this.userRepository.findByEmail(username).orElseThrow(()->new ResouceNotFoundException("User",username));
        System.out.println("Email "+user.getEmail());
        return user;
    }
}
