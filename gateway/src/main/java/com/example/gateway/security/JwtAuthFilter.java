package com.example.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class JwtAuthFilter implements WebFilter {
  private final byte[] signingKey;

  public JwtAuthFilter(@Value("${security.jwt.secret:dev-secret-change}") String secret) {
    this.signingKey = Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String path = request.getPath().value();
    if (path.startsWith("/auth") || path.startsWith("/actuator")) {
      return chain.filter(exchange);
    }
    String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
    String token = authHeader.substring(7);
    try {
      Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
      // Optionally propagate user id header
      String subject = claims.getSubject();
      Object roles = claims.get("roles");
      ServerHttpRequest mutated = request.mutate()
          .header("X-User-Id", subject != null ? subject : "")
          .header("X-User-Roles", roles != null ? roles.toString() : "")
          .build();
      return chain.filter(exchange.mutate().request(mutated).build());
    } catch (Exception ex) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
  }
}
