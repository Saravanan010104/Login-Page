package com.example.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
  @GetMapping("/")
  public String home(Model model) {
    // Basic placeholders; real data via AJAX from services
    model.addAttribute("title", "Shop");
    return "index";
  }
}
