package com.mycompany.mockjson.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // enables @PreAuthorize and @PostAuthorize annotations in controllers,
                                             // secured for @Secured
public class SecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/v1/auth/**").permitAll()
                        // .requestMatchers("/v1/users/**").hasAuthority(PermissionName.GENERAL_USER_READ.getPermission())
                        .anyRequest().authenticated());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(
                        (request, response, authException) -> {
                            String authHeader = request.getHeader("Authorization");
                            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                                response.sendError(401, "Unauthorized");
                            } else {
                                response.sendError(403, "Forbidden");
                            }
                        }));
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.logout(logout -> logout.logoutUrl("/v1/auth/logout") // default logout url is /logout
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> {
                    SecurityContextHolder.clearContext();
                    response.setStatus(200);
                })); // default logout success handler is to redirect to /login?logout

        return http.build();
    }

    // @Bean
    // public AuthenticationEntryPoint unAuthorizedEntryPoint() {
    // return (request, response, authException) -> {
    // String authHeader = request.getHeader("Authorization");
    // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    // response.sendError(401, "Unauthorized");

    // } else {
    // response.sendError(403, "Forbidden");
    // }
    // };
    // }
}
