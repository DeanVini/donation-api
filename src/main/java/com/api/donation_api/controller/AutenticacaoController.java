package com.api.donation_api.controller;

import com.api.donation_api.dto.AuthRequestDTO;
import com.api.donation_api.dto.UsuarioRequestDTO;
import com.api.donation_api.model.Usuario;
import com.api.donation_api.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AutenticacaoController {
    private final AutenticacaoService autenticacaoService;

    @Autowired
    private AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDTO authRequestDTO) throws Exception {
        return autenticacaoService.autenticar(authRequestDTO);
    }

    @PostMapping("/registro")
    public Usuario registro(@RequestBody UsuarioRequestDTO novoUsuarioRequestDTO) {
        return autenticacaoService.registrar(novoUsuarioRequestDTO);
    }
}
