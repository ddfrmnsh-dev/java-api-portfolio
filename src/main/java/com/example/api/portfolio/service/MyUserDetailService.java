package com.example.api.portfolio.service;

import com.example.api.portfolio.model.User;
import com.example.api.portfolio.model.UserPrincipal;
import com.example.api.portfolio.repository.UserRepository;
import com.example.api.portfolio.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("ada data username load: {}", username);

        User user = userRepository.findByUsername(username);
        logger.info("ada data username load user: {}", user.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                new ArrayList<>());
        return new UserPrincipal(user);
    }
}
