package com.api.donation_api.controller;

import com.api.donation_api.dto.NovoEnderecoRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Endereco;
import com.api.donation_api.service.CepService;
import com.api.donation_api.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    private final EnderecoService enderecoService;
    private final CepService cepService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService, CepService cepService) {
        this.enderecoService = enderecoService;
        this.cepService = cepService;
    }

    @GetMapping("/")
    public List<Endereco> getAllEnderecos(){
        return enderecoService.getAllEnderecos();
    }

    @PostMapping("/")
    public Endereco cadastrarEndereco(@RequestBody NovoEnderecoRequestDTO novoEnderecoRequest) throws ResourceNotFoundException {
        return enderecoService.cadastrarEndereco(novoEnderecoRequest);
    }
//
//    @PutMapping("/")
//    public Endereco atualizarEndereco(){
//
//    }

    @GetMapping("/viaCep/{cep}")
    public Endereco getEnderecoViaCep(@PathVariable String cep) throws ResourceNotFoundException {
        return cepService.buscarEnderecoPorCep(cep);
    }

    @GetMapping("/{idEndereco}")
    public Optional<Endereco> getEnderecoById(@PathVariable Long idEndereco){
        return enderecoService.getEnderecoById(idEndereco);
    }
}
