package com.example.auth.model;

import java.time.OffsetDateTime;

public class User {
  private Long id;
  private String email;
  private String passwordHash;
  private String roles; // comma-separated
  private OffsetDateTime createdAt;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
  public String getRoles() { return roles; }
  public void setRoles(String roles) { this.roles = roles; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
