package com.mycompany.mockjson.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.mockjson.auth.permission.PermissionService;
import com.mycompany.mockjson.auth.token.Token;
import com.mycompany.mockjson.auth.token.TokenRepo;
import com.mycompany.mockjson.auth.token.TokenType;
import com.mycompany.mockjson.user.User;
import com.mycompany.mockjson.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenRepo tokenRepo;

    public User registerGeneralUser(RegistrationRequest request) throws Exception {
        User user = userService.constructUserFromRequest(request);
        permissionService.grantGeneralUserPermissions(user);
        User savedUser = userService.createUser(user);
        return savedUser;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        // will throw exception if not successful
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userService.getUserByUsername(request.getUsername());

        String accessTokenString = jwtService.generateToken(user);
        String refreshTokenString = jwtService.generateRefreshToken(user);

        Token accessToken = new Token();
        accessToken.setTokenType(TokenType.BEARER);
        accessToken.setValue(accessTokenString);

        user.addToken(accessToken);
        userService.save(user);

        return new AuthenticationResponse(accessTokenString, refreshTokenString);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws StreamWriteException, DatabindException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);
        User user = null;
        if (username != null) {
            user = userService.getUserByUsername(username);
        }

        if (jwtService.validateToken(refreshToken, user) && user != null) {
            var accessToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            Token token = new Token();
            token.setValue(accessToken);
            user.addToken(token);
            userService.save(user);

            AuthenticationResponse authResponse = new AuthenticationResponse(accessToken, refreshToken);
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepo.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }
}
