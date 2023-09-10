package com.mycompany.mockjson.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.mockjson.user.UserRepo;

@Service
public class AuthenticationService {
    private UserRepo userRepo;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

}
