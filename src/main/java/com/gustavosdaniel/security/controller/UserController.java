package com.gustavosdaniel.security.controller;

import com.gustavosdaniel.security.model.Users;
import com.gustavosdaniel.security.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody Users user) {

        return userService.verificar(user);
    }
}
