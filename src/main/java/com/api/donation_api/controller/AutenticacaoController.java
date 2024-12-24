package com.api.donation_api.controller;

import com.api.donation_api.dto.AuthRequestDTO;
import com.api.donation_api.dto.UsuarioRequestDTO;
import com.api.donation_api.model.User;
import com.api.donation_api.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AutenticacaoController {
    private final AuthenticationService authenticationService;

    @Autowired
    private AutenticacaoController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDTO authRequestDTO) {
        return authenticationService.authenticate(authRequestDTO);
    }

    @PostMapping("/registro")
    public User registro(@RequestBody UsuarioRequestDTO novoUsuarioRequestDTO) {
        return authenticationService.register(novoUsuarioRequestDTO);
    }
}
