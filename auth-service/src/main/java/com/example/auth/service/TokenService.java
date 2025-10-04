package com.example.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class TokenService {
  private final SecretKey key;
  private final long ttlSeconds;

  public TokenService(@Value("${security.jwt.secret:dev-secret-change}") String secret,
                      @Value("${security.jwt.ttl-seconds:3600}") long ttlSeconds) {
    byte[] secretBytes = Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8));
    this.key = Keys.hmacShaKeyFor(secretBytes);
    this.ttlSeconds = ttlSeconds;
  }

  public String generate(String subject, Map<String, Object> claims) {
    Instant now = Instant.now();
    return Jwts.builder()
        .setSubject(subject)
        .addClaims(claims)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plusSeconds(ttlSeconds)))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }
}
