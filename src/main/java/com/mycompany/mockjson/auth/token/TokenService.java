package com.mycompany.mockjson.auth.token;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.mockjson.exception.ResourceNotFoundException;

@Service
public class TokenService {
    @Autowired
    private TokenRepo tokenRepo;

    public Token findByValue(String value) throws ResourceNotFoundException {
        return tokenRepo.findByValue(value).orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }

    public List<Token> findAllValidTokensByUser(UUID id) {
        return tokenRepo.findAllValidTokensByUser(id);
    }

    public List<Token> saveAll(List<Token> tokens) {
        return tokenRepo.saveAll(tokens);
    }

    public Token save(Token token) {
        return tokenRepo.save(token);
    }
}
