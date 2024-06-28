package com.example.api.portfolio.security;

import com.example.api.portfolio.controller.AuthController;
import com.example.api.portfolio.dto.ApiResponse;
import com.example.api.portfolio.dto.MetaResponse;
import com.example.api.portfolio.service.MyUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter  extends OncePerRequestFilter{
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private MyUserDetailService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        logger.info("Authorization Header: {}", authorizationHeader);


        String username = null;
        String jwtToken = null;

//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            username = jwtUtil.extractUsername(jwt);
//        }
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwtToken);
                logger.info("ada data user jwtfilter: {}", username);
            } catch (IllegalArgumentException e) {
                System.err.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired: {}", e.getMessage());
                System.err.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
//            MetaResponse meta = new MetaResponse("JWT Token does not provided", HttpStatus.UNAUTHORIZED.value(), "failed");
//            writeResponse(response, meta);
//            return;
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    private void writeResponse(HttpServletResponse response, MetaResponse metaResponse) throws IOException {
        response.setStatus(metaResponse.getCode());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(metaResponse));
    }
}
