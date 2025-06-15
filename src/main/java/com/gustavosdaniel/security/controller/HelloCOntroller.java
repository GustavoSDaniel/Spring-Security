package com.gustavosdaniel.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloCOntroller {

    @GetMapping("/")
    public String greet(HttpServletRequest request) {
        return "Hello " + request.getSession().getId();
    }
}
