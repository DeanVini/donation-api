package com.api.donation_api.controller;

import com.api.donation_api.dto.AuthRequestDTO;
import com.api.donation_api.dto.UserRequestDTO;
import com.api.donation_api.model.User;
import com.api.donation_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {
    private final AuthService authService;

    @Autowired
    private AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDTO authRequestDTO) {
        return authService.authenticate(authRequestDTO);
    }

    @PostMapping("/register")
    public User register(@RequestBody UserRequestDTO novoUserRequestDTO) {
        return authService.register(novoUserRequestDTO);
    }
}
