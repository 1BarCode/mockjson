package com.mycompany.mockjson.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.mockjson.auth.authority.Authority;
import com.mycompany.mockjson.auth.role.Role;
import com.mycompany.mockjson.auth.role.RoleName;
import com.mycompany.mockjson.auth.role.RoleService;
import com.mycompany.mockjson.user.User;
import com.mycompany.mockjson.user.UserService;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        Role userRole = roleService.findByName(RoleName.ROLE_USER);

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // hash password
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setRole(userRole);

        user.addAuthority(authority);

        User savedUser = userService.createUser(user);

        String accessToken = jwtService.generateToken(savedUser);
        // String refreshToken = jwtService.generateRefreshToken(savedUser);

        return new AuthenticationResponse(accessToken);
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
