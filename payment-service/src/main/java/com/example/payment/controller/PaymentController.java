package com.example.payment.controller;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

  public record WebhookRequest(@NotNull String gatewayReference, @NotNull String status) {}

  @PostMapping("/webhook")
  public ResponseEntity<?> webhook(@RequestBody WebhookRequest request) {
    // TODO: validate signature and update payment status (stub)
    return ResponseEntity.ok(Map.of("ack", true));
  }
}
