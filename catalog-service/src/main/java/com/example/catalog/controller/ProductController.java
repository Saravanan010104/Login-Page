package com.example.catalog.controller;

import com.example.catalog.dto.ProductDto;
import com.example.catalog.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/products")
  public ResponseEntity<List<ProductDto>> list(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String q,
      @RequestParam(required = false, name = "category") Long categoryId,
      @RequestParam(required = false, name = "min") Double minPrice,
      @RequestParam(required = false, name = "max") Double maxPrice
  ) {
    return ResponseEntity.ok(productService.list(page, size, sort, q, categoryId, minPrice, maxPrice));
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<ProductDto> get(@PathVariable long id) {
    return ResponseEntity.of(productService.get(id));
  }
}
