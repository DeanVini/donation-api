package com.api.donation_api.controller;

import com.api.donation_api.dto.PessoaDTO;
import com.api.donation_api.model.Pessoa;
import com.api.donation_api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }


    @PostMapping("/")
    public Pessoa cadastrarPessoa(@RequestBody PessoaDTO pessoaDTO){
        return pessoaService.cadastrarPessoa(pessoaDTO);
    }

    @GetMapping("/{id}")
    public Optional<Pessoa> getPessoaById(@PathVariable Long id){
        return pessoaService.getPessoaById(id);
    }
}
