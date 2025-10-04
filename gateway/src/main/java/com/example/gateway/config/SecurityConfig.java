package com.example.gateway.config;

import com.example.gateway.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, JwtAuthFilter jwtAuthFilter) {
    return http
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers("/auth/**", "/actuator/**").permitAll()
            .anyExchange().authenticated())
        .addFilterAt(jwtAuthFilter, org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }
}
