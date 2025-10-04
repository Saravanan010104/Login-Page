package com.example.catalog.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
  private final JdbcTemplate jdbcTemplate;

  public AdminController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public record CreateProductRequest(@NotBlank String sku, @NotBlank String name, String description,
                                     @Min(0) double price, @Min(0) int stock, Long categoryId) {}

  @PostMapping("/product")
  public ResponseEntity<?> create(@Valid @RequestBody CreateProductRequest req) {
    Long id = jdbcTemplate.queryForObject(
        "INSERT INTO products (sku, name, description, price, stock, category_id) VALUES (?,?,?,?,?,?) RETURNING id",
        Long.class, req.sku(), req.name(), req.description(), req.price(), req.stock(), req.categoryId());
    return ResponseEntity.ok(Map.of("id", id));
  }
}
