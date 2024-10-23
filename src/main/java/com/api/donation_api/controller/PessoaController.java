package com.api.donation_api.controller;

import com.api.donation_api.dto.PessoaDTO;
import com.api.donation_api.model.Pessoa;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PessoaController {
    @PostMapping("/pessoas")
    public Pessoa cadastrarPessoa(PessoaDTO pessoaDTO){
        return new Pessoa();
    };
}
