package com.mycompany.mockjson.auth;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mycompany.mockjson.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {
    private static final long AT_EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hour

    // private static final long RT_EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24
    // hour

    @Value("${app.jwt.at.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(User user) {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + AT_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }
}
