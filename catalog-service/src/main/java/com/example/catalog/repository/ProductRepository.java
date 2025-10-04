package com.example.catalog.repository;

import com.example.catalog.dto.ProductDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
  private final JdbcTemplate jdbcTemplate;

  public ProductRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static class ProductRowMapper implements RowMapper<ProductDto> {
    @Override
    public ProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {
      ProductDto p = new ProductDto();
      p.id = rs.getLong("id");
      p.sku = rs.getString("sku");
      p.name = rs.getString("name");
      p.description = rs.getString("description");
      p.price = rs.getDouble("price");
      p.stock = rs.getInt("stock");
      p.categoryId = rs.getObject("category_id", Long.class);
      return p;
    }
  }

  public List<ProductDto> list(int offset, int limit, String orderClause, String whereClause, Object[] params) {
    String sql = "SELECT id, sku, name, description, price, stock, category_id FROM products " +
        (whereClause != null ? whereClause : "") +
        (orderClause != null ? orderClause : " ORDER BY id DESC ") +
        " OFFSET ? LIMIT ?";
    Object[] finalParams;
    if (params != null) {
      finalParams = new Object[params.length + 2];
      System.arraycopy(params, 0, finalParams, 0, params.length);
      finalParams[params.length] = offset;
      finalParams[params.length + 1] = limit;
    } else {
      finalParams = new Object[] { offset, limit };
    }
    return jdbcTemplate.query(sql, new ProductRowMapper(), finalParams);
  }

  public Optional<ProductDto> get(long id) {
    List<ProductDto> items = jdbcTemplate.query(
        "SELECT id, sku, name, description, price, stock, category_id FROM products WHERE id = ?",
        new ProductRowMapper(), id);
    return items.stream().findFirst();
  }
}
