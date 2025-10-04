package com.example.cart.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
  private final HashOperations<String, String, String> hashOps;

  public CartService(StringRedisTemplate stringRedisTemplate) {
    this.hashOps = stringRedisTemplate.opsForHash();
  }

  private String key(long userId) { return "cart:" + userId; }

  public void addItem(long userId, long productId, int qty) {
    String field = String.valueOf(productId);
    String existing = hashOps.get(key(userId), field);
    int newQty = (existing == null ? 0 : Integer.parseInt(existing)) + qty;
    hashOps.put(key(userId), field, Integer.toString(newQty));
  }

  public Map<String, Integer> getCart(long userId) {
    Map<String, String> raw = hashOps.entries(key(userId));
    Map<String, Integer> result = new HashMap<>();
    if (raw != null) {
      for (Map.Entry<String, String> e : raw.entrySet()) {
        result.put(e.getKey(), Integer.parseInt(e.getValue()));
      }
    }
    return result;
  }
}
