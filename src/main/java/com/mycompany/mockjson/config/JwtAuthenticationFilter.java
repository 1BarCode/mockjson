package com.mycompany.mockjson.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mycompany.mockjson.auth.JwtService;
import com.mycompany.mockjson.auth.token.Token;
import com.mycompany.mockjson.auth.token.TokenService;
import com.mycompany.mockjson.exception.ResourceNotFoundException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // skip to next filter in the chain
            return;
        }

        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        // check to see if the authentication in the security context is already there
        // or not
        Boolean isAlreadyAuthenticated = SecurityContextHolder.getContext().getAuthentication() != null;

        if (username != null && !isAlreadyAuthenticated) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // get user details from db if
                                                                                       // not authenticated

            // check if token is valid and not expired or revoked from db
            Token dbToken = null;
            try {
                dbToken = tokenService.findByValue(jwt);
            } catch (ResourceNotFoundException e) {
                filterChain.doFilter(request, response); // skip to next filter in the chain
                return;
            }
            boolean tokenIsNotExpiredOrRevoked = dbToken != null && !dbToken.isExpired() && !dbToken.isRevoked();

            // validate token claim with details
            boolean isValidToken = jwtService.validateToken(jwt, userDetails);

            if (isValidToken && tokenIsNotExpiredOrRevoked) {
                // token is needed to update security context to authenticated state
                // create new authentication object
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // add details of the
                                                                                                  // request token
                SecurityContextHolder.getContext().setAuthentication(authToken); // update security context
            }

        }

        filterChain.doFilter(request, response); // continue to next filter in the chain
    }

}
