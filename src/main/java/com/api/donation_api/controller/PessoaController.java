package com.api.donation_api.controller;

import com.api.donation_api.dto.NovaPessoaRequestDTO;
import com.api.donation_api.model.Pessoa;
import com.api.donation_api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/")
    public List<Pessoa> getAllPessoas(){
        return pessoaService.getAllPessoas();
    }

    @PostMapping("/")
    public Pessoa cadastrarPessoa(@RequestBody NovaPessoaRequestDTO novaPessoaRequestDTO){
        return pessoaService.cadastrarPessoa(novaPessoaRequestDTO);
    }

    @GetMapping("/{id}")
    public Optional<Pessoa> getPessoaById(@PathVariable Long id){
        return pessoaService.getPessoaById(id);
    }
}
