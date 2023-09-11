package com.mycompany.mockjson.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.mockjson.auth.permission.PermissionService;
import com.mycompany.mockjson.user.User;
import com.mycompany.mockjson.user.UserService;

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
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    public User registerGeneralUser(RegistrationRequest request) throws Exception {
        User user = userService.constructUserFromRequest(request);
        permissionService.grantGeneralUserPermissions(user);
        User savedUser = userService.createUser(user);
        return savedUser;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UsernameNotFoundException {
        // will throw exception if not successful
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userService.getUserByUsername(request.getUsername());

        String accessToken = jwtService.generateToken(user);
        // String refreshToken = jwtService.generateRefreshToken(savedUser);

        return new AuthenticationResponse(accessToken);
    }
}
