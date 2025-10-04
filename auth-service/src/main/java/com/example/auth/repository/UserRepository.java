package com.example.auth.repository;

import com.example.auth.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public class UserRepository {
  private final JdbcTemplate jdbcTemplate;

  public UserRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User u = new User();
      u.setId(rs.getLong("id"));
      u.setEmail(rs.getString("email"));
      u.setPasswordHash(rs.getString("password_hash"));
      u.setRoles(rs.getString("roles"));
      u.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));
      return u;
    }
  }

  public Optional<User> findByEmail(String email) {
    try {
      User user = jdbcTemplate.queryForObject(
          "SELECT id, email, password_hash, roles, created_at FROM users WHERE email = ?",
          new UserRowMapper(), email);
      return Optional.ofNullable(user);
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }

  public long insert(User user) {
    // Returns generated id
    return jdbcTemplate.queryForObject(
        "INSERT INTO users(email, password_hash, roles, created_at) VALUES (?, ?, ?, now()) RETURNING id",
        Long.class, user.getEmail(), user.getPasswordHash(), user.getRoles());
  }
}
