package com.kijy.strengthhub.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final SecretKey secretKey = Keys.hmacShaKeyFor("my-very-secret-key-my-very-secret-key".getBytes());
    private final long expiration = 1000 * 60 * 60; // 1시간

    public String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
