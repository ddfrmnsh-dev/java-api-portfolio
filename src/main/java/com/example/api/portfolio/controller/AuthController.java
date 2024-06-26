package com.example.api.portfolio.controller;

import com.example.api.portfolio.dto.ApiResponse;
import com.example.api.portfolio.dto.MetaResponse;
import com.example.api.portfolio.model.JwtRequest;
import com.example.api.portfolio.model.JwtResponse;
import com.example.api.portfolio.security.JwtTokenUtil;
import com.example.api.portfolio.service.MyUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        try {
            logger.info("AuthenticationRequest: {}", authenticationRequest.getPassword());
            logger.info("ada data getUsername auth: {}", authenticationRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            final String jwt = jwtTokenUtil.generateToken(userDetails);
            MetaResponse meta = new MetaResponse("Successfully authenticated", HttpStatus.OK.value(), "success");
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(meta,jwt));
        } catch (AuthenticationException e) {
            MetaResponse meta = new MetaResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), "failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(meta,null));
        }

    }
}


