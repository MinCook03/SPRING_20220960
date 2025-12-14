package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // localhost:8080 또는 localhost:8080/index 로 들어오면
    // index.html 화면을 보여줘라
    @GetMapping(value = {"/", "/index"})
    public String home() {
        return "index"; 
    }
}