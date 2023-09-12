package com.mycompany.mockjson.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth-test")
@PreAuthorize("hasAuthority('general_user:read')")
public class AuthTestController {
    @GetMapping
    @PreAuthorize("hasAuthority('general_user:read')")
    public ResponseEntity<?> helloWorld() {
        return ResponseEntity.ok("Hello World, from secured endpoint");
    }
}
