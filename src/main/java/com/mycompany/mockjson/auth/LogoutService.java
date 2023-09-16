package com.mycompany.mockjson.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.mycompany.mockjson.auth.token.Token;
import com.mycompany.mockjson.auth.token.TokenService;
import com.mycompany.mockjson.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return;

        String jwt = authHeader.substring(7);
        Token dbToken = null;
        try {
            dbToken = tokenService.findByValue(jwt);
        } catch (ResourceNotFoundException e) {
            return;
        }

        if (dbToken != null) {
            dbToken.setRevoked(true);
            dbToken.setExpired(true);
            tokenService.save(dbToken);
        }
    }

}
