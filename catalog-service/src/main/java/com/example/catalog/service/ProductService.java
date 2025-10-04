package com.example.catalog.service;

import com.example.catalog.dto.ProductDto;
import com.example.catalog.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Cacheable(value = "catalog:list", key = "#page + ':' + #size + ':' + #sort + ':' + #q + ':' + #categoryId + ':' + #minPrice + ':' + #maxPrice")
  public List<ProductDto> list(int page, int size, String sort, String q, Long categoryId, Double minPrice, Double maxPrice) {
    int offset = Math.max(0, (page - 1) * Math.min(100, size));
    int limit = Math.min(100, size);

    List<String> conditions = new ArrayList<>();
    List<Object> params = new ArrayList<>();
    if (q != null && !q.isBlank()) {
      conditions.add("(LOWER(name) LIKE ? OR LOWER(description) LIKE ?)");
      String like = "%" + q.toLowerCase() + "%";
      params.add(like);
      params.add(like);
    }
    if (categoryId != null) {
      conditions.add("category_id = ?");
      params.add(categoryId);
    }
    if (minPrice != null) {
      conditions.add("price >= ?");
      params.add(minPrice);
    }
    if (maxPrice != null) {
      conditions.add("price <= ?");
      params.add(maxPrice);
    }
    String where = conditions.isEmpty() ? null : (" WHERE " + String.join(" AND ", conditions));

    String order = null;
    if (sort != null && !sort.isBlank()) {
      String s = sort.toLowerCase();
      if (s.equals("price") || s.equals("name") || s.equals("created_at")) {
        order = " ORDER BY " + s + " ASC ";
      } else if (s.equals("-price")) {
        order = " ORDER BY price DESC ";
      }
    }

    return productRepository.list(offset, limit, order, where, params.toArray());
  }

  @Cacheable(value = "catalog:detail", key = "#id")
  public Optional<ProductDto> get(long id) {
    return productRepository.get(id);
  }
}
