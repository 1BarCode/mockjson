package com.mycompany.mockjson.auth.token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepo extends JpaRepository<Token, UUID> {
    Optional<Token> findByToken(String token);

    @Query("SELECT t FROM Token t WHERE t.user.id = :id AND t.revoked = false AND t.expired = false")
    List<Token> findAllValidTokensByUser(UUID id);
}
