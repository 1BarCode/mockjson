package com.mycompany.mockjson.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth-test")
public class AuthTestController {
    @GetMapping
    public ResponseEntity<?> helloWorld() {
        return ResponseEntity.ok("Hello World, from secured endpoint");
    }
}
