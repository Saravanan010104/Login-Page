package com.example.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UiServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(UiServerApplication.class, args);
  }
}
